import java.io.Serializable;

public class Range implements Serializable {
    private long startByte;
    private final long endByte;

    public Range(long startByte, long endByte) {
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public long getStartByte() {
        return startByte;
    }

    public long getEndByte() {
        return endByte;
    }

    public void setStartByte(long byteToSet) {
        this.startByte = byteToSet;
    }
}
