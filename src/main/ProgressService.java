package main;

import java.io.Serializable;

public class ProgressService implements Serializable {
    private long totalBytesDownloaded;
    private long fileSize;

    public ProgressService(long totalBytesDownloaded, long fileSize) {
        this.totalBytesDownloaded = totalBytesDownloaded;
        this.fileSize = fileSize;
    }

    public void incrementBytesDownloded(long increment) {
        totalBytesDownloaded += increment;
    }

    public void displayProgress() {
//        System.out.println("Progress: " + Math.round(((double) totalBytesDownloaded / fileSize) * 100) + "%");
    }


}
