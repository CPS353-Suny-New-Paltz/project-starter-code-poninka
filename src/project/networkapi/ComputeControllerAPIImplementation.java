package project.networkapi;

import project.conceptualapi.ComputeControllerAPI;
import project.conceptualapi.ComputeRequest;
import project.conceptualapi.ComputeResponse;
import project.conceptualapi.ComputeStatus;

public class ComputeControllerAPIImplementation implements ComputeControllerAPI {
    @Override
    public ComputeResponse compute(ComputeRequest request) {
        // Default null until implemented
        return new ComputeResponse(null, ComputeStatus.FAIL);
    }
}
