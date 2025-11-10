package project.networkapi.grpc;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import project.conceptualapi.PowerDigitSumController;
import project.networkapi.UserComputeAPI;
import project.networkapi.UserComputeAPIImplementation;
import project.processapi.RemoteDataStoreClient;

public class NetworkApiGrpcServer {

    // gRPC server
    private final Server server;

    // Builds the gRPC server
    public NetworkApiGrpcServer(int port, UserComputeAPI coordinator) {
        this.server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new NetworkSubmissionServiceImpl(coordinator)).build();
    }

    // Starts server
    public void start() throws IOException {
        server.start();
        System.out.println("Port: " + server.getPort());

        // Create a shutdown hook for server
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("JVM is shutting down");
            try {
                stop();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    // Manual testing
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 50051;
        String dataStoreTarget = args.length > 1 ? args[1] : "localhost:60051";
        RemoteDataStoreClient remoteStore = new RemoteDataStoreClient(dataStoreTarget);
        UserComputeAPI coordinator = new UserComputeAPIImplementation(remoteStore, new PowerDigitSumController());
        NetworkApiGrpcServer grpcServer = new NetworkApiGrpcServer(port, coordinator);
        grpcServer.start();
        grpcServer.blockUntilShutdown();
    }
}
