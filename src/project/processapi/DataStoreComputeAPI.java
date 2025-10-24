package project.processapi;

import project.annotations.ProcessAPI;
import java.util.List;

@ProcessAPI
public interface DataStoreComputeAPI {
    StorageResponse insertRequest(StorageRequest request);
    StorageResponse insertResult(StorageRequest request);

    List<Integer> loadInput();

    StorageResponse saveOutput(List<String> results);
}