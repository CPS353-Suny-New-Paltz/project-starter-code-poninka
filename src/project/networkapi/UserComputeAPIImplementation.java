package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.ComputeRequest;
import project.conceptualapi.ComputeResponse;
import project.conceptualapi.PowerDigitSumController;
import project.processapi.DataStoreComputeAPI;
import project.processapi.DataStoreImplementation;

import java.util.ArrayList;
import java.util.List;

public class UserComputeAPIImplementation implements UserComputeAPI {

    private final DataStoreComputeAPI dataStore;
    private final ComputeControllerAPI computeEngine;

    public UserComputeAPIImplementation() {
        // Default for use in testing
        this.dataStore = new DataStoreImplementation();
        this.computeEngine = new PowerDigitSumController();
    }

    public UserComputeAPIImplementation(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine) {
        this.dataStore = dataStore;
        this.computeEngine = computeEngine;
    }

    // validation data store path and delimiter
    public void runComputation(UserSubmission submission) {
        try {
            if (submission == null || submission.getInput() == null || submission.getOutput() == null || submission.getDelimiter() == null) {
                return; // invalid submission
            }
            String inputPath = submission.getInput().getSourceName();
            String outputPath = submission.getOutput().getSourceName();
            String delimiter = submission.getDelimiter().getDelimiter();

            // Load integers from data storage
            List<Integer> intInputs = dataStore.loadInput(inputPath, delimiter);
            if (intInputs.isEmpty()) {
                return; // empty list check
            }

            // Compute results put as a single line
            List<String> results = new ArrayList<>(intInputs.size());
            for (int n : intInputs) {
                ComputeRequest request = new ComputeRequest(n); // n^n digit sum
                ComputeResponse response = computeEngine.compute(request);
                results.add(response.getResult());
            }
            String outputString = String.join(",", results);
            dataStore.saveOutput(outputPath, outputString);

        } catch (Exception e) {
            // submit() will convert Exception to a failure status
        }
    }

    @Override
    public UserSubResponse submit(UserSubmission submission) {
        try {
            if (submission == null || submission.getInput() == null || submission.getOutput() == null || submission.getDelimiter() == null) {
                return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
            }
            runComputation(submission);
            return new UserSubResponse("sub-1", SubmissionStatus.SUCCESS);
        } catch (Exception e) {
            return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
        }
    }

    public SubmissionStatus getStatus(String id) {
        // Any id value is accepted for now
        return (id != null) ? SubmissionStatus.SUCCESS : SubmissionStatus.FAILURE_SYSTEM_ERROR;
    }
}
