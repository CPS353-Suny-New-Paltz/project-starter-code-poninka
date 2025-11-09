package project.networkapi.grpc;

import io.grpc.Grpc;
import io.grpc.InsecureChannelCredentials;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class NetworkSubmissionConsoleClient {

    public static void main(String[] args) throws InterruptedException {
        String target = args.length > 0 ? args[0] : "localhost:50051";
        Scanner scanner = new Scanner(System.in);

        // Ask for input file path
        System.out.print("Enter file path to input file: ");
        String inputPath = scanner.nextLine().trim();

        // Ask for output file path
        System.out.print("Enter output file destination: ");
        String outputPath = scanner.nextLine().trim();

        String delimiter = ",";

        // connection to gRPC server
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        try {
            // blocking client stub
            NetworkSubmissionServiceGrpc.NetworkSubmissionServiceBlockingStub stub = NetworkSubmissionServiceGrpc.newBlockingStub(channel);

            // Build for the request
            SubmitRequest request = SubmitRequest.newBuilder()
                    .setInputType("file")
                    .setInputValue(inputPath)
                    .setOutputDestination(outputPath)
                    .setDelimiter(delimiter)
                    .build();

            // request
            SubmitResponse response = stub.submit(request);

            // Check server response
            if ("SUCCESS".equals(response.getStatus())) {
                System.out.println("Task completed with SUCCESS. id: " + response.getSubmissionId());
            } else {
                System.out.println("Task failed. status: " + response.getStatus());
            }

        } catch (StatusRuntimeException e) {
            // network or server error fail
            System.err.println("Request failed: " + e.getStatus());
        } finally {
            // close connection
            channel.shutdownNow().awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    // Validation
    private static void validateFile(String inputPath) {
        Path path = Paths.get(inputPath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new IllegalArgumentException("File does not exist: " + inputPath);
        }
    }
}
