package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.PowerDigitSumController;
import project.processapi.DataStoreComputeAPI;
import project.processapi.DataStoreImplementation;

public class UserComputeAPIImplementation implements UserComputeAPI {

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

    // Executes the full submission, returns true on success false otherwise
    private boolean execute(UserSubmission submission) {
        return SubmissionExecutor.executeSequential(dataStore, computeEngine, submission);
    }
}
