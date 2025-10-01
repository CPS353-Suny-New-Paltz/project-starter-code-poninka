package project.processapi;

public class StorageRequest {
    private byte[] data;

    public StorageRequest(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }
}