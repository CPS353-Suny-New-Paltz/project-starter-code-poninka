package project.checkpointtests;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.PowerDigitSumController;
import project.networkapi.Delimiter;
import project.networkapi.InputSource;
import project.networkapi.OutputSource;
import project.networkapi.UserComputeAPI;
import project.networkapi.UserComputeAPIImplementation;
import project.networkapi.UserSubmission;
import project.processapi.DataStoreComputeAPI;
import project.processapi.DataStoreImplementation;

public class ManualTestingFramework {

    public static final String INPUT = "TestInput.txt";
    public static final String OUTPUT = "TestOutput.txt";

    public static void main(String[] args) {
        DataStoreComputeAPI dataStore = new DataStoreImplementation();
        ComputeControllerAPI computeEngine = new PowerDigitSumController();
        UserComputeAPI networkAPI = new UserComputeAPIImplementation(dataStore, computeEngine);

        // Create and submit for network API
        UserSubmission submission = new UserSubmission(
                new InputSource("file", INPUT),
                new OutputSource(OUTPUT),
                new Delimiter(','));

        networkAPI.submit(submission);
    }
}