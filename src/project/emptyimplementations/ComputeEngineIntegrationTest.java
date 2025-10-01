package project.emptyimplementations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import project.annotations.StorageRequest;
import project.annotations.StorageResponse;
import project.annotations.StoreStatus;
import project.annotations.DataStoreInMemory;

public class ComputeEngineIntegrationTest {
    @Test
    public void testInMemoryDataStore() {
        DataStoreInMemory store = new DataStoreInMemory();

        // Simulate user submitting "2^15"
        StorageResponse inResp = store.insertRequest(new StorageRequest("2 15".getBytes()));

        assertEquals(StoreStatus.SUCCESS, inResp.getStatus());
        assertNotNull(inResp.getId());

        String loadedInput = new String(store.loadData(inResp.getId()));
        assertEquals("2 15", loadedInput);

        // Simulate engine computing digit sum = 26
        StorageResponse outResp = store.insertResult(new StorageRequest("26".getBytes()));

        assertEquals(StoreStatus.SUCCESS, outResp.getStatus());
        assertNotNull(outResp.getId());

        String loadedResult = new String(store.loadResult(outResp.getId()));
        assertEquals("26", loadedResult);
    }
}