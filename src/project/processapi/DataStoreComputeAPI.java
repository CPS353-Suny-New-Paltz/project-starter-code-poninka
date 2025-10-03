package project.processapi;

import project.annotations.ProcessAPI;

@ProcessAPI
public interface DataStoreComputeAPI {
    StorageResponse insertRequest(StorageRequest request);
    byte[] loadData(String id);
    StorageResponse insertResult(StorageRequest request);
    byte[] loadResult(String id);
}