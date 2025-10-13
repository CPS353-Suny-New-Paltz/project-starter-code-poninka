import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import project.processapi.StorageRequest;
import project.processapi.StorageResponse;
import project.processapi.StoreStatus;
import project.processapi.DataStoreInMemory;

public class ComputeEngineIntegrationTest {
    @Test
    public void testInMemoryDataStore() {
        DataStoreInMemory store = new DataStoreInMemory();

        // Simulate user submitting 1,10,25
        StorageResponse inResp = store.insertRequest(new StorageRequest("1,10,25".getBytes()));

        assertEquals(StoreStatus.SUCCESS, inResp.getStatus());
        assertNotNull(inResp.getId());

        String loadedInput = new String(store.loadData(inResp.getId()));
        assertEquals("1,10,25", loadedInput);

        // Simulate engine computing digit sums
        StorageResponse outResp = store.insertResult(new StorageRequest("1,1,7".getBytes()));

        assertEquals(StoreStatus.SUCCESS, outResp.getStatus());
        assertNotNull(outResp.getId());

        String loadedResult = new String(store.loadResult(outResp.getId()));
        assertEquals("1,1,7", loadedResult);
    }
}