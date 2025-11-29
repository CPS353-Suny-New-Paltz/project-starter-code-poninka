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

        System.out.print("Enter input type (file/memory): ");
        String inputType = scanner.nextLine().trim();

        System.out.print("Enter input value: ");
        String inputValue = scanner.nextLine().trim();

        System.out.print("Enter output file destination: ");
        String outputPath = scanner.nextLine().trim();

        System.out.print("Enter delimiter (press enter for default ','): ");
        String delimiterInput = scanner.nextLine().trim();
        String delimiter = delimiterInput.isEmpty() ? "," : delimiterInput;

        // connection to gRPC server
        ManagedChannel channel = Grpc.newChannelBuilder(target, InsecureChannelCredentials.create()).build();

        try {
            // blocking client stub
            NetworkSubmissionServiceGrpc.NetworkSubmissionServiceBlockingStub stub = NetworkSubmissionServiceGrpc.newBlockingStub(channel);

            SubmitRequest request = SubmitRequest.newBuilder()
                    .setInputType(inputType)
                    .setInputValue(inputValue)
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
