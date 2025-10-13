package project.processapi;

import java.util.Collections;
import java.util.List;

public class DataStoreImplementation implements DataStoreComputeAPI {

    // TODO: create logic for loading and saving data

    public DataStoreImplementation(String inputSource, String outputSource) {
    }

    @Override
    public StorageResponse insertRequest(StorageRequest request) {
        return null;
    }

    @Override
    public byte[] loadData(String id) {
        return null;
    }

    @Override
    public StorageResponse insertResult(StorageRequest request) {
        return null;
    }

    @Override
    public byte[] loadResult(String id) {
        return null;
    }


    @Override
    public List<Integer> loadInput() {
        return Collections.emptyList();
    }

    @Override
    public void saveOutput(List<String> results) {
    }
}