package testharness;

import java.io.File;
import project.networkapi.Delimiter;
import project.networkapi.InputSource;
import project.networkapi.OutputSource;
import project.networkapi.UserComputeAPI;
import project.networkapi.UserSubmission;

public class TestUser {

    // 3: changed the type of this variable to the name used
    // The parameter passed to the constructor
    private final UserComputeAPI coordinator;

    public TestUser(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    public void run(String outputPath) {
        char delimiter = ';';
        String inputPath = "test" + File.separatorChar + "testInputFile.test";

        // 4: Run the job through the coordinator with the input/output/delimiter
        InputSource input = new InputSource("file", inputPath);
        OutputSource output = new OutputSource(outputPath);
        Delimiter delim = new Delimiter(delimiter);
        UserSubmission submission = new UserSubmission(input, output, delim);
        coordinator.submit(submission);
    }

}
