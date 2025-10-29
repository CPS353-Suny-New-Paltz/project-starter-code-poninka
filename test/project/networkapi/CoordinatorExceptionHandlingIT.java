package project.networkapi;

import org.junit.jupiter.api.Test;
import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.PowerDigitSumController;
import project.processapi.DataStoreComputeAPI;
import project.processapi.StorageResponse;
import project.processapi.StoreStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CoordinatorExceptionHandlingIT {

    private static class ThrowingDataStore implements DataStoreComputeAPI {
        @Override
        public List<Integer> loadInput(String inputPath, String delimiter) {
            throw new RuntimeException("boom");
        }

        @Override
        public StorageResponse saveOutput(String outputPath, String resultContent) {
            throw new RuntimeException("boom");
        }
    }

    @Test
    void testSubmitReturnsFailureOnDataStoreException() {
        DataStoreComputeAPI ds = new ThrowingDataStore();
        ComputeControllerAPI engine = new PowerDigitSumController();
        UserComputeAPIImplementation coordinator = new UserComputeAPIImplementation(ds, engine);

        UserSubmission sub = new UserSubmission(
                new InputSource("file", "ignored"),
                new OutputSource("ignored"),
                new Delimiter(',')
        );

        UserSubResponse resp = coordinator.submit(sub);
        assertEquals(SubmissionStatus.FAILURE_SYSTEM_ERROR, resp.getStatus());
    }
}
