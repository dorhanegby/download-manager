class Message {
    private final byte[] dataBuffer;
    private final int bytesRead;
    private final long currentByte;

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
