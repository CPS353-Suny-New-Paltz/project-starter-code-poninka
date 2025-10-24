package project.processapi;

import java.util.ArrayList;
import java.util.List;

public class DataStoreInMemory implements DataStoreComputeAPI {
    // FIX: Made fields final, as they are initialized once and never reassigned.
    private final List<byte[]> userInputs = new ArrayList<>();
    private final List<byte[]> results = new ArrayList<>();

    @Override
    public StorageResponse insertRequest(StorageRequest request) {
        if (request == null || request.getData() == null) {
            return new StorageResponse(null, StoreStatus.FAILURE_WRITE_ERROR);
        }
        int index = userInputs.size();
        userInputs.add(request.getData());
        return new StorageResponse("input-" + index, StoreStatus.SUCCESS);
    }

    public byte[] loadData(String id) {
        try {
            int index = Integer.parseInt(id.replace("input-", ""));
            return userInputs.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public StorageResponse insertResult(StorageRequest request) {
        if (request == null || request.getData() == null) {
            return new StorageResponse(null, StoreStatus.FAILURE_WRITE_ERROR);
        }
        int index = results.size();
        results.add(request.getData());
        return new StorageResponse("result-" + index, StoreStatus.SUCCESS);
    }

    public byte[] loadResult(String id) {
        try {
            int index = Integer.parseInt(id.replace("result-", ""));
            return results.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> loadInput() {
        return List.of();
    }

    @Override
    public StorageResponse saveOutput(List<String> results) {
        // Returning a SUCCESS response for now
        return new StorageResponse(null, StoreStatus.SUCCESS);
    }
}