package project.networkapi;

public class UserComputeAPIImplementation implements UserComputeAPI {
    @Override
    public UserSubResponse submit(UserSubmission submission) {
        // Default failure until implemented
        return new UserSubResponse(null, SubmissionStatus.FAILURE_SYSTEM_ERROR);
    }
}
