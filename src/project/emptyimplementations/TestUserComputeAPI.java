package project.emptyimplementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TestUserComputeAPI {
    @Test
    public void testSubmissionSuccess() {
        UserComputeAPI mocksub = Mockito.mock(UserComputeAPI.class);
        UserSubmission sub = new UserSubmission(
                new InputSource("file", "input.txt"),
                new OutputSource("stdout"),
                new Delimiter(";", ":")
        );

        Mockito.when(mocksub.submit(sub))
                .thenReturn(new UserSubResponse("sub-1", SubmissionStatus.SUCCESS));

        UserSubResponse resp = mocksub.submit(sub);
        assertEquals(SubmissionStatus.SUCCESS, resp.getStatus());
        assertEquals("sub-1", resp.getSubId());
    }
}
