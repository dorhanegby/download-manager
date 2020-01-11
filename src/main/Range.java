package main;

public class Range {
    private int startByte;
    private int endByte;

    public Range(int startByte, int endByte) {
        this.startByte = startByte;
        this.endByte = endByte;
    }

    public int getStartByte() {
        return startByte;
    }

    public int getEndByte() {
        return endByte;
    }
}
