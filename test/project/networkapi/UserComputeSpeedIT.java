package project.networkapi;

import org.junit.jupiter.api.Test;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.PowerDigitSumController;
import project.networkapi.Delimiter;
import project.networkapi.InputSource;
import project.networkapi.OutputSource;
import project.networkapi.UserComputeAPI;
import project.networkapi.UserComputeAPIImplementation;
import project.networkapi.UserComputeAPIMultiThreaded;
import project.networkapi.UserSubmission;
import project.processapi.DataStoreComputeAPI;
import project.processapi.DataStoreImplementation;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserComputeSpeedIT {

    @Test
    void sequentialVsConcurrentSpeedDump() throws Exception {
        Path input = Files.createTempFile("benchmark_input", ".txt");
        Path output = Files.createTempFile("benchmark_output", ".txt");

        //create payload
        String payload = IntStream.rangeClosed(100, 600).mapToObj(String::valueOf).collect(Collectors.joining(","));
        Files.writeString(input, payload);

        //Set up submission
        UserSubmission submission = new UserSubmission(new InputSource("file", input.toString()), new OutputSource(output.toString()), new Delimiter(','));

        DataStoreComputeAPI dataStore = new DataStoreImplementation();
        ComputeControllerAPI computeEngine = new PowerDigitSumController();
        UserComputeAPI sequential = new UserComputeAPIImplementation(dataStore, computeEngine);
        UserComputeAPI concurrent = new UserComputeAPIParallel(dataStore, computeEngine);

        sequential.submit(submission);
        concurrent.submit(submission);

        int runs = 5;
        long seqAggregate = 0L;
        long concAggregate = 0L;
        for (int i = 1; i <= runs; i++) {
            long seqStart = System.nanoTime();
            sequential.submit(submission);
            long seqElapsed = System.nanoTime() - seqStart;
            seqAggregate += seqElapsed;

            long concStart = System.nanoTime();
            concurrent.submit(submission);
            long concElapsed = System.nanoTime() - concStart;
            concAggregate += concElapsed;

            double seqMs = seqElapsed / 1_000_000.0;
            double concMs = concElapsed / 1_000_000.0;
            System.out.printf("run %d = sequential: %.2f ms, concurrent: %.2f ms%n", i, seqMs, concMs);
        }
    }
}
