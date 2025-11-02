package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.ComputeRequest;
import project.conceptualapi.ComputeResponse;
import project.conceptualapi.PowerDigitSumController;
import project.processapi.DataStoreComputeAPI;
import project.processapi.DataStoreImplementation;
import project.processapi.StorageResponse;
import project.processapi.StoreStatus;

import java.util.ArrayList;
import java.util.List;

public class UserComputeAPIImplementation implements UserComputeAPI, ComputeControllerAPI {

    private final DataStoreComputeAPI dataStore;
    private final ComputeControllerAPI computeEngine;
    private final boolean noOpMode;

    public UserComputeAPIImplementation() {
        // Default for use in testing
        this.dataStore = new DataStoreImplementation();
        this.computeEngine = new PowerDigitSumController();
        this.noOpMode = true;
    }

    public UserComputeAPIImplementation(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine) {
        this.dataStore = dataStore;
        this.computeEngine = computeEngine;
        this.noOpMode = false;
    }

    // boolean helper and ignore outcome
    public void runComputation(UserSubmission submission) {
        if (!noOpMode) {
            execute(submission);
        }
    }

    @Override
    public UserSubResponse submit(UserSubmission submission) {
        try {
            if (submission == null || submission.getInput() == null || submission.getOutput() == null || submission.getDelimiter() == null) {
                return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
            }
            if (noOpMode) {
                //report success without requiring files
                return new UserSubResponse("sub-1", SubmissionStatus.SUCCESS);
            }
            boolean ok = execute(submission);
            return new UserSubResponse(ok ? "sub-1" : null, ok ? SubmissionStatus.SUCCESS : SubmissionStatus.FAILURE_SYSTEM_ERROR);
        } catch (Exception e) {
            return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
        }
    }

    public SubmissionStatus getStatus(String id) {
        // Any id value is accepted for now
        return (id != null) ? SubmissionStatus.SUCCESS : SubmissionStatus.FAILURE_SYSTEM_ERROR;
    }

    @Override
    public ComputeResponse compute(ComputeRequest request) {
        return computeEngine.compute(request);
    }

    // Executes the full submission, returns true on success false otherwise
    private boolean execute(UserSubmission submission) {
        try {
            if (submission == null || submission.getInput() == null || submission.getOutput() == null || submission.getDelimiter() == null) {
                return false;
            }
            String inputPath = submission.getInput().getSourceName();
            String outputPath = submission.getOutput().getSourceName();
            String delimiter = submission.getDelimiter().getDelimiter();

            // Load integers from data storage
            List<Integer> intInputs = dataStore.loadInput(inputPath, delimiter);
            if (intInputs.isEmpty()) {
                return false;
            }

            // Compute results put as a single line
            List<String> results = new ArrayList<>(intInputs.size());
            for (int n : intInputs) {
                ComputeRequest request = new ComputeRequest(n); // n^n digit sum
                ComputeResponse response = computeEngine.compute(request);
                results.add(response.getResult());
            }
            String outputString = String.join(",", results);
            StorageResponse saved = dataStore.saveOutput(outputPath, outputString);
            return saved != null && saved.getStatus() == StoreStatus.SUCCESS;
        } catch (Exception e) {
            return false;
        }
    }
}
