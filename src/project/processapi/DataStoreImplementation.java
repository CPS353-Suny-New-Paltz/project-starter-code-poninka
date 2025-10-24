package project.processapi;

import java.util.List;
import java.util.ArrayList;
public class DataStoreImplementation implements DataStoreComputeAPI {

    @Override
    public StorageResponse insertRequest(StorageRequest request) {
        // TODO: My logic to store the request
        System.out.println("Storing request");
        return new StorageResponse("req-123", StoreStatus.SUCCESS);
    }

    @Override
    public StorageResponse insertResult(StorageRequest request) {
        // TODO: My logic to store the result
        System.out.println("Storing result");
        return new StorageResponse("res-456", StoreStatus.SUCCESS);
    }

    @Override
    public List<Integer> loadInput() {
        // TODO: My logic to load the actual input data
        System.out.println("Loading input");

        // Using dummy data just to make it compile for now
        List<Integer> dummyInput = new ArrayList<>();
        dummyInput.add(1);
        dummyInput.add(2);
        dummyInput.add(3);
        return dummyInput;
    }

    // This is the correct saveOutput method that returns a StorageResponse
    @Override
    public StorageResponse saveOutput(List<String> results) {
        // TODO: My logic to save the final output
        System.out.println("Saving output: " + results.toString());
        return new StorageResponse("out-789", StoreStatus.SUCCESS);
    }
}