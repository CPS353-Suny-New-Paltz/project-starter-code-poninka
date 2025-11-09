package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.processapi.DataStoreComputeAPI;

// uses SubmissionExecutor for computations
public class UserComputeAPIMultiThreaded implements UserComputeAPI {

    // Upper limit of threads
    public static final int MAX_THREADS = 8;

    private final DataStoreComputeAPI dataStore;
    private final ComputeControllerAPI computeEngine;


     // Constructor for dataStore and computeEngine
    public UserComputeAPIMultiThreaded(DataStoreComputeAPI dataStore, ComputeControllerAPI computeEngine) {
        this.dataStore = dataStore;
        this.computeEngine = computeEngine;
    }

    // uses SubmissionExecutor for multi-threading and returns status
    @Override
    public UserSubResponse submit(UserSubmission submission) {
        try {
            // Run multi-threaded
            boolean ok = SubmissionExecutor.executeConcurrent(dataStore, computeEngine, submission, MAX_THREADS );
            // if success
            if (ok) {
                return new UserSubResponse("sub-1", SubmissionStatus.SUCCESS);
            } else {
                return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
            }

        } catch (Exception e) {
            return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
        }
    }
    // TODO: real status validation
    // unless NULL is successful
    public SubmissionStatus getStatus(String id) {
        if (id != null) {
            return SubmissionStatus.SUCCESS;
        } else {
            return SubmissionStatus.FAILURE_SYSTEM_ERROR;
        }
    }

}
