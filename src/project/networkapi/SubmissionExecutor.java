package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.ComputeRequest;
import project.conceptualapi.ComputeResponse;
import project.processapi.DataStoreComputeAPI;
import project.processapi.StorageResponse;
import project.processapi.StoreStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// Sequential = single-thread
// Concurrent = uses a thread pool with an upper bound (set to 8 in UserComputeAPIMultiThreaded)
final class SubmissionExecutor {

    private SubmissionExecutor() {
    }

    // Sequential returns the result string and now null on failure
    static String executeSequential(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine,
            UserSubmission submission) {

        try {
            SubmissionContext context = prepareSubmission(dataStore, submission);
            if (context == null) {
                return null;
            }

            List<String> results = new ArrayList<>();

            // Compute one by one (n^n digit sum)
            for (int n : context.intInputs) {
                results.add(computeResult(computeEngine, n));
            }

            String resultString = String.join(context.delimiter, results);
            boolean saved = saveResults(dataStore, context.outputPath, results, context.delimiter);
            return saved ? resultString : null;

        } catch (Exception e) {
            return null;
        }
    }

    // Concurrent fixed thread pool defined by maxThreads
    static boolean executeConcurrent(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine,
            UserSubmission submission, int maxThreads) {

        ExecutorService pool = null;

        try {
            SubmissionContext context = prepareSubmission(dataStore, submission);
            if (context == null) {
                return false;
            }

            pool = Executors.newFixedThreadPool(Math.max(1, maxThreads)); // at least one thread

            List<Future<String>> futures = new ArrayList<>();

            // Submit each job as a Callable
            for (int n : context.intInputs) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() {
                        try {
                            return computeResult(computeEngine, n);
                        } catch (Exception e) {
                            // Return ERROR if any thread fails
                            return "ERROR";
                        }
                    }
                };
                futures.add(pool.submit(task));
            }

            // "Collect" results
            List<String> results = new ArrayList<>();
            for (Future<String> f : futures) {
                try {
                    results.add(f.get()); // block
                } catch (Exception e) {
                    results.add("ERROR");
                }
            }

            return saveResults(dataStore, context.outputPath, results, context.delimiter);

        } catch (Exception e) {
            return false;
        } finally {
            // shutdown threads
            if (pool != null) {
                pool.shutdown();
            }
        }
    }

    private static boolean isValid(UserSubmission submission) {
        return submission != null
                && submission.getInput() != null
                && submission.getOutput() != null
                && submission.getDelimiter() != null;
    }

    private static SubmissionContext prepareSubmission(DataStoreComputeAPI dataStore, UserSubmission submission) {
        if (!isValid(submission)) {
            return null;
        }

        // Extract parameters
        String inputPath = submission.getInput().getSourceName();
        String inputType = submission.getInput().getType();
        String outputPath = submission.getOutput().getSourceName();
        String delimiter = submission.getDelimiter().getDelimiter();

        List<Integer> intInputs;
        if ("memory".equalsIgnoreCase(inputType)) {
            // Parse directly
            intInputs = parseInput(inputPath);
        } else {
            // Load list using flexible delimiter for input parsing
            intInputs = dataStore.loadInput(inputPath, "[,;\\s]+");
        }
        if (intInputs == null || intInputs.isEmpty()) {
            return null;
        }

        return new SubmissionContext(intInputs, outputPath, delimiter);
    }

    private static List<Integer> parseInput(String content) {
        if (content == null || content.isBlank()) {
            return new ArrayList<>();
        }
        String[] parts = content.split("[,\\s]+");
        List<Integer> values = new ArrayList<>();
        for (String p : parts) {
            String trimmed = p.trim();
            if (!trimmed.isEmpty()) {
                try {
                    values.add(Integer.parseInt(trimmed));
                } catch (NumberFormatException e) {
                    // ignore invalid inputs
                }
            }
        }
        return values;
    }

    private static String computeResult(ComputeControllerAPI computeEngine, int n) {
        // request object
        ComputeRequest request = new ComputeRequest(n);
        ComputeResponse response = computeEngine.compute(request);

        // Collect result will look something like: "5,4,27"
        if (response != null && response.getResult() != null) {
            return response.getResult();
        }
        return "ERROR"; // will look something like: "5,ERROR,27"
    }

    private static boolean saveResults(DataStoreComputeAPI dataStore, String outputPath, List<String> results,
            String delimiter) {
        String outputString = String.join(delimiter, results);
        // Save output
        StorageResponse saved = dataStore.saveOutput(outputPath, outputString);
        return saved != null && saved.getStatus() == StoreStatus.SUCCESS;
    }

    private static final class SubmissionContext {
        private final List<Integer> intInputs;
        private final String outputPath;
        private final String delimiter;

        private SubmissionContext(List<Integer> intInputs, String outputPath, String delimiter) {
            this.intInputs = intInputs;
            this.outputPath = outputPath;
            this.delimiter = delimiter;
        }
    }
}
