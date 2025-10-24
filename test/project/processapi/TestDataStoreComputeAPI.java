package project.processapi;

import project.annotations.ProcessAPI;

import java.util.List;

interface DataStoreComputeAPIImplementation {
    List<Integer> loadInput();

    StorageResponse saveOutput(List<String> results);
}