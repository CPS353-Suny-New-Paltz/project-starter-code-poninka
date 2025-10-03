import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class TestDataStoreComputeAPI {
    @Test
    public void testDataStoreSmokeTest() {
        DataStoreInMemory dataStore = new DataStoreInMemory();
        StorageRequest request = new StorageRequest("test data".getBytes());

        // Test inserting a request
        StorageResponse response = dataStore.insertRequest(request);
        assertNotNull(response);
        assertEquals(StoreStatus.SUCCESS, response.getStatus());
        assertNotNull(response.getId());

        // Test loading the data back
        byte[] loadedData = dataStore.loadData(response.getId());
        assertNotNull(loadedData);
        assertEquals("test data", new String(loadedData));
    }
}