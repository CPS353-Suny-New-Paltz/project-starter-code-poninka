package project.networkapi;

import org.junit.jupiter.api.Test;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.PowerDigitSumController;
import project.conceptualapi.PowerDigitSumControllerFast;
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
        ComputeControllerAPI slowEngine = new PowerDigitSumController();
        ComputeControllerAPI fastEngine = new PowerDigitSumControllerFast();
        UserComputeAPI sequential = new UserComputeAPIImplementation(dataStore, slowEngine);
        UserComputeAPI sequentialFast = new UserComputeAPIImplementation(dataStore, fastEngine);
        UserComputeAPI multiThreaded = new UserComputeAPIMultiThreaded(dataStore, slowEngine);
        UserComputeAPI multiThreadedFast = new UserComputeAPIMultiThreaded(dataStore, fastEngine);

        sequential.submit(submission);
        sequentialFast.submit(submission);
        multiThreaded.submit(submission);
        multiThreadedFast.submit(submission);

        int runs = 5;
        for (int i = 1; i <= runs; i++) {
            long seqStart = System.nanoTime();
            sequential.submit(submission);
            long seqElapsed = System.nanoTime() - seqStart;

            long seqFastStart = System.nanoTime();
            sequentialFast.submit(submission);
            long seqFastElapsed = System.nanoTime() - seqFastStart;

            long multiStart = System.nanoTime();
            multiThreaded.submit(submission);
            long multiElapsed = System.nanoTime() - multiStart;

            long multiFastStart = System.nanoTime();
            multiThreadedFast.submit(submission);
            long multiFastElapsed = System.nanoTime() - multiFastStart;

            double seqMs = seqElapsed / 1_000_000.0;
            double seqFastMs = seqFastElapsed / 1_000_000.0;
            double multiMs = multiElapsed / 1_000_000.0;
            double multiFastMs = multiFastElapsed / 1_000_000.0;
            System.out.printf("run %d = seq: %.2f ms, seq-fast: %.2f ms, multi: %.2f ms, multi-fast: %.2f ms%n", i, seqMs, seqFastMs, multiMs, multiFastMs);
        }
    }
}
