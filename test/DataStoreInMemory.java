package project.processapi;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TestDataStoreComputeAPI {

    @Test
    void testInsertAndLoadRequest() {
        DataStoreInMemory dataStore = new DataStoreInMemory();

        byte[] testData = "simple test".getBytes();
        StorageRequest request = new StorageRequest(testData);

        //Test the insertRequest method
        StorageResponse response = dataStore.insertRequest(request);

        assertEquals(StoreStatus.SUCCESS, response.getStatus());
        assertNotNull(response.getId());

        //Test the loadData method
        byte[] loadedData = dataStore.loadData(response.getId());
        assertArrayEquals(testData, loadedData); // Check if data loaded
    }

    @Test
    void testSaveOutputReturnsSuccess() {
        // Test the saveOutput
        DataStoreComputeAPI dataStore = new DataStoreInMemory();
        StorageResponse response = dataStore.saveOutput(List.of("result"));

        assertEquals(StoreStatus.SUCCESS, response.getStatus());
    }
}