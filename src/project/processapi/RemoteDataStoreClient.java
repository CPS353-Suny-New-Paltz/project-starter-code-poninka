package project.processapi;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.util.ArrayList;
import java.util.List;

// Client for gRPC
public class RemoteDataStoreClient implements DataStoreComputeAPI {

    private final ManagedChannel channel;
    private final DataStoreServiceGrpc.DataStoreServiceBlockingStub blockingStub;

    // Connect to server (host:port)
    public RemoteDataStoreClient(String target) {
        this.channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();
        this.blockingStub = DataStoreServiceGrpc.newBlockingStub(channel);
    }

    @Override
    public List<Integer> loadInput(String inputPath, String delimiter) {
        try {
            LoadInputResponse response = blockingStub.loadInput(
                    LoadInputRequest.newBuilder().setInputPath(inputPath).setDelimiter(delimiter).build()
            );
            return new ArrayList<>(response.getValuesList());
        } catch (StatusRuntimeException e) {
            return List.of();
        }
    }

    @Override
    public StorageResponse saveOutput(String outputPath, String resultContent) {
        try {
            SaveOutputResponse response = blockingStub.saveOutput(
                    SaveOutputRequest.newBuilder().setOutputPath(outputPath)
                            .setContent(resultContent == null ? "" : resultContent).build()
            );

            // Convert response status to enum
            StoreStatus status = parseStatus(response.getStatus());
            return new StorageResponse(response.getId(), status);

        } catch (StatusRuntimeException e) {
            // Return failure status
            return new StorageResponse(outputPath, StoreStatus.FAILURE_WRITE_ERROR);
        }
    }

    // Safely parse
    private StoreStatus parseStatus(String status) {
        try {
            return StoreStatus.valueOf(status);
        } catch (Exception e) {
            return StoreStatus.FAILURE_WRITE_ERROR;
        }
    }
}
