package project.processapi;

import project.annotations.ProcessAPI;

import java.util.List;

@ProcessAPI
public interface DataStoreComputeAPI {
    StorageResponse insertRequest(StorageRequest request);
    byte[] loadData(String id);
    StorageResponse insertResult(StorageRequest request);
    byte[] loadResult(String id);

    List<Integer> loadInput();

    void saveOutput(List<String> results);
}