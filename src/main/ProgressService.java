package main;

import java.io.Serializable;

public class ProgressService implements Serializable {
    private long totalBytesDownloaded;
    private long fileSize;
    private boolean[] prints = new boolean[101];

    public ProgressService(long totalBytesDownloaded, long fileSize) {
        this.totalBytesDownloaded = totalBytesDownloaded;
        this.fileSize = fileSize;
    }

    public void incrementBytesDownloded(long increment) {
        totalBytesDownloaded += increment;
    }

    public void displayProgress() {
        int index = (int) Math.floor(((double) totalBytesDownloaded / fileSize) * 100);
        if (!prints[index]) {
            System.out.println("Progress: " + index + "%");
            prints[index] = true;
        }
    }

    public boolean isDownloadCompleted() {
        return totalBytesDownloaded == fileSize;
    }

    public void setTotalBytesDownloaded(long totalBytesDownloaded) {
        this.totalBytesDownloaded = totalBytesDownloaded;
    }
}
