package project.processapi;

public interface DataStoreComputeAPI {
    StorageResponse insertRequest(StorageRequest request);
    byte[] loadData(String id);
    StorageResponse insertResult(StorageRequest request);
    byte[] loadResult(String id);
}