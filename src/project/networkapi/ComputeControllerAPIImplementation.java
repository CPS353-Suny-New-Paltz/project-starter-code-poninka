package project.networkapi;

public class ComputeControllerAPIImplementation implements ComputeControllerAPI {
    @Override
    public ComputeResponse compute(ComputeRequest request) {
        // Default null until implemented
        return new ComputeResponse(null, ComputeStatus.FAIL);
    }
}
