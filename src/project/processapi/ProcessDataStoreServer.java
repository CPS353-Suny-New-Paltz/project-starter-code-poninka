package project.processapi;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;

public class ProcessDataStoreServer {

    private final Server server;

    public ProcessDataStoreServer(int port, DataStoreComputeAPI dataStore) {
        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new GrpcDataStoreService(dataStore)).build();
    }

    public void start() throws IOException {
        server.start();
    }
    // Keep running unless shutdown
    public void blockUntilShutdown() throws InterruptedException {
        server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 60051;
        ProcessDataStoreServer server = new ProcessDataStoreServer(port, new DataStoreImplementation());
        server.start();
        server.blockUntilShutdown();
    }
}
