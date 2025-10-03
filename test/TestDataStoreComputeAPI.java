import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import project.processapi.DataStoreInMemory;
import project.processapi.StorageRequest;
import project.processapi.StorageResponse;
import project.processapi.StoreStatus;

public class TestDataStoreComputeAPI {
    @Test
    public void testDataStoreSmokeTest() {
        DataStoreInMemory dataStore = new DataStoreInMemory();
        StorageRequest request = new StorageRequest("test data".getBytes());

        // Test inserting request
        StorageResponse response = dataStore.insertRequest(request);
        assertNotNull(response);
        assertEquals(StoreStatus.SUCCESS, response.getStatus());
        assertNotNull(response.getId());

        // Test loading the data
        byte[] loadedData = dataStore.loadData(response.getId());
        assertNotNull(loadedData);
        assertEquals("test data", new String(loadedData));
    }
}