package project.networkapi.grpc;

import io.grpc.stub.StreamObserver;
import project.networkapi.Delimiter;
import project.networkapi.InputSource;
import project.networkapi.OutputSource;
import project.networkapi.SubmissionStatus;
import project.networkapi.UserComputeAPI;
import project.networkapi.UserSubResponse;
import project.networkapi.UserSubmission;

public class NetworkSubmissionServiceImpl extends NetworkSubmissionServiceGrpc.NetworkSubmissionServiceImplBase {

    private final UserComputeAPI coordinator;

    public NetworkSubmissionServiceImpl(UserComputeAPI coordinator) {
        this.coordinator = coordinator;
    }

    @Override
    public void submit(SubmitRequest request, StreamObserver<SubmitResponse> responseObserver) {
        SubmitResponse response;
        try {
            UserSubmission submission = new UserSubmission(
                    new InputSource(request.getInputType(), request.getInputValue()),
                    new OutputSource(request.getOutputDestination()),
                    new Delimiter(resolveDelimiter(request.getDelimiter()))
            );
            UserSubResponse result = coordinator.submit(submission);
            response = SubmitResponse.newBuilder()
                    .setSubmissionId(result.getSubId() == null ? "" : result.getSubId())
                    .setStatus(result.getStatus().name()).build();
        } catch (Exception e) {
            response = SubmitResponse.newBuilder().setSubmissionId("")
                    .setStatus(SubmissionStatus.FAILURE_SYSTEM_ERROR.name())
                    .setMessage(e.getMessage() == null ? "system_error" : e.getMessage()).build();
        }
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private char resolveDelimiter(String delimiterValue) {
        if (delimiterValue == null || delimiterValue.isEmpty()) {
            return '\n';
        }
        return delimiterValue.charAt(0);
    }
}
