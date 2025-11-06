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
 // Concurrent = uses thread pool with an upper bound (set to 8 in UserComputeAPIMultiThreaded)
final class SubmissionExecutor {

    private SubmissionExecutor() {}

     // Sequential returns true if all steps complete successfully
    static boolean executeSequential(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine, UserSubmission submission) {

        try {
            // null validation
            if (!isValid(submission)) {
                return false;
            }

            // Extract parameters
            String inputPath = submission.getInput().getSourceName();
            String outputPath = submission.getOutput().getSourceName();
            String delimiter = submission.getDelimiter().getDelimiter();

            // Load list from the file
            List<Integer> intInputs = dataStore.loadInput(inputPath, delimiter);
            if (intInputs == null || intInputs.isEmpty()) {
                return false;
            }

            List<String> results = new ArrayList<>();

            // Compute one by one (n^n digit sum)
            for (int n : intInputs) {
                // request object
                ComputeRequest request = new ComputeRequest(n);
                ComputeResponse response = computeEngine.compute(request);

                // Collect result will look something like: "5,4,27"
                if (response != null && response.getResult() != null) {
                    results.add(response.getResult());
                } else {
                    results.add("ERROR"); // will look something like: "5,ERROR,27"
                }
            }

            String outputString = String.join(",", results);
            // Save output
            StorageResponse saved = dataStore.saveOutput(outputPath, outputString);
            return saved != null && saved.getStatus() == StoreStatus.SUCCESS;

        } catch (Exception e) {
            return false;
        }
    }

     // Concurrent fixed thread pool defined by maxThreads
    static boolean executeConcurrent(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine, UserSubmission submission, int maxThreads) {

        ExecutorService pool = null;

        try {
            if (!isValid(submission)) {
                return false;
            }

            // Extract core parameters
            String inputPath = submission.getInput().getSourceName();
            String outputPath = submission.getOutput().getSourceName();
            String delimiter = submission.getDelimiter().getDelimiter();

            // Load integer
            List<Integer> intInputs = dataStore.loadInput(inputPath, delimiter);
            if (intInputs == null || intInputs.isEmpty()) {
                return false;
            }

            pool = Executors.newFixedThreadPool(Math.max(1, maxThreads)); // at least one thread

            List<Future<String>> futures = new ArrayList<>();

            // Submit each job as a Callable
            for (int n : intInputs) {
                Callable<String> task = new Callable<String>() {
                    @Override
                    public String call() {
                        try {
                            ComputeRequest request = new ComputeRequest(n);
                            ComputeResponse response = computeEngine.compute(request);
                            if (response != null && response.getResult() != null) {
                                return response.getResult();
                            } else {
                                return "ERROR";
                            }
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

            String outputString = String.join(",", results);
            // Save output
            StorageResponse saved = dataStore.saveOutput(outputPath, outputString);
            return saved != null && saved.getStatus() == StoreStatus.SUCCESS;

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
}
