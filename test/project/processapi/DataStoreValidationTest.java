package project.processapi;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStoreValidationTest {

    @Test
    void testLoadInputWithInvalidParamsReturnsEmpty() {
        DataStoreComputeAPI ds = new DataStoreImplementation();
        List<Integer> values = ds.loadInput(null, ",");
        assertEquals(List.of(), values);

        values = ds.loadInput(" ", ",");
        assertEquals(List.of(), values);

        values = ds.loadInput("nonexistent.txt", ",");
        assertEquals(List.of(), values);
    }

    @Test
    void testSaveOutputWithInvalidParamsReturnsFailure() {
        DataStoreComputeAPI ds = new DataStoreImplementation();
        StorageResponse resp = ds.saveOutput(null, "x");
        assertEquals(StoreStatus.FAILURE_WRITE_ERROR, resp.getStatus());

        resp = ds.saveOutput(" ", "x");
        assertEquals(StoreStatus.FAILURE_WRITE_ERROR, resp.getStatus());
    }
}
