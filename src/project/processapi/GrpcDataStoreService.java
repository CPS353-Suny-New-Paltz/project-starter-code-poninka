package project.processapi;

import io.grpc.stub.StreamObserver;
import java.util.List;

// gRPC wrapper
public class GrpcDataStoreService extends DataStoreServiceGrpc.DataStoreServiceImplBase {

    // Delegate to DataStoreImplementation
    private final DataStoreComputeAPI delegate;

    public GrpcDataStoreService(DataStoreComputeAPI delegate) {
        this.delegate = delegate;
    }

    @Override
    public void loadInput(LoadInputRequest request, StreamObserver<LoadInputResponse> responseObserver) {
        List<Integer> values = delegate.loadInput(request.getInputPath(), request.getDelimiter());

        // Build and send gRPC response
        LoadInputResponse response = LoadInputResponse.newBuilder().addAllValues(values).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void saveOutput(SaveOutputRequest request, StreamObserver<SaveOutputResponse> responseObserver) {
        // Call local data store to save output
        StorageResponse storageResponse = delegate.saveOutput(request.getOutputPath(), request.getContent());

        // build and send gRPC response
        SaveOutputResponse response = SaveOutputResponse.newBuilder()
                .setId(storageResponse.getId() == null ? "" : storageResponse.getId())
                .setStatus(storageResponse.getStatus().name()).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
