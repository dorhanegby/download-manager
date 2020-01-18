import java.io.Serializable;

class ProgressService implements Serializable {
    private long totalBytesDownloaded;
    private final long fileSize;
    private final boolean[] prints = new boolean[101];

    ProgressService(long fileSize) {
        this.totalBytesDownloaded = (long) 0;
        this.fileSize = fileSize;
    }

    void incrementBytesDownloaded(long increment) {
        totalBytesDownloaded += increment;
    }

    void displayProgress() {
        int index = (int) Math.floor(((double) totalBytesDownloaded / fileSize) * 100);
        if (!prints[index]) {
            System.out.println("Downloaded " + index + "%");
            prints[index] = true;
        }
    }

    boolean isDownloadCompleted() {
        return totalBytesDownloaded == fileSize;
    }

    void setTotalBytesDownloaded(long totalBytesDownloaded) {
        this.totalBytesDownloaded = totalBytesDownloaded;
    }
}
