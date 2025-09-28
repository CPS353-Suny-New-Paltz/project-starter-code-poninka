package project.annotations;

import java.util.ArrayList;
import java.util.List;

import project.annotations.DataStoreComputeAPI;
import project.annotations.StorageRequest;
import project.annotations.StorageResponse;
import project.annotations.StoreStatus;

public class DataStoreInMemory implements DataStoreComputeAPI {
    private List<byte[]> userInputs = new ArrayList<>();
    private List<byte[]> results = new ArrayList<>();

    @Override
    public StorageResponse insertRequest(StorageRequest request) {
        if (request == null || request.getData() == null) {
            return new StorageResponse(null, StoreStatus.FAILURE_WRITE_ERROR);
        }
        int index = userInputs.size();
        userInputs.add(request.getData());
        return new StorageResponse("input-" + index, StoreStatus.SUCCESS);
    }

    @Override
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

    @Override
    public byte[] loadResult(String id) {
        try {
            int index = Integer.parseInt(id.replace("result-", ""));
            return results.get(index);
        } catch (Exception e) {
            return null;
        }
    }
}
