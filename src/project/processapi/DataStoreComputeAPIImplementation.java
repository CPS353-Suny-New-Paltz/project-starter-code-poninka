package project.processapi;

import project.annotations.ProcessAPI;
import java.util.List;

public interface DataStoreComputeAPIImplementation {
    // Load the list of integers
    List<Integer> loadInput();

    // Method to save the final list
    void saveOutput(List<String> results);
}