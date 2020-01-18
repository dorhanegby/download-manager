package main;

public class Message {
    private byte dataBuffer[];
    private int bytesRead;
    private long currentByte;

    public Message(byte[] dataBuffer, int bytesRead, long currentByte) {
        this.dataBuffer = dataBuffer;
        this.bytesRead = bytesRead;
        this.currentByte = currentByte;
    }

    public byte[] getDataBuffer() {
        return dataBuffer;
    }

    public int getBytesRead() {
        return bytesRead;
    }

    public long getCurrentByte() {
        return currentByte;
    }
}
