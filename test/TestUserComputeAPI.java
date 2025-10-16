import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import project.networkapi.UserComputeAPI;
import project.networkapi.UserComputeAPIImplementation;
import project.networkapi.UserSubmission;
import project.networkapi.InputSource;
import project.networkapi.OutputSource;
import project.networkapi.Delimiter;
import project.networkapi.UserSubResponse;
import project.networkapi.SubmissionStatus;

public class TestUserComputeAPI {
    @Test
    public void testSubmissionSuccess() {
        // Instantiate the implementation
        UserComputeAPI api = new UserComputeAPIImplementation();

        UserSubmission sub = new UserSubmission(
                new InputSource("file", "input.txt"),
                new OutputSource("stdout"),
                new Delimiter(';')
        );

        // Call the method on the object
        UserSubResponse resp = api.submit(sub);

        // Assert the results
        assertEquals(SubmissionStatus.SUCCESS, resp.getStatus());
        assertEquals("sub-1", resp.getSubId());
    }
}