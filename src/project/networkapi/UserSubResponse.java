package project.networkapi;

public class UserSubResponse {

    private final String subId;
    private final SubmissionStatus status;
    private final String result;

    public UserSubResponse(String subId, SubmissionStatus status) {
        this(subId, status, null);
    }

    public UserSubResponse(String subId, SubmissionStatus status, String result) {
        this.subId = subId;
        this.status = status;
        this.result = result;
    }

    public String getSubId() {
        return subId;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }
}
