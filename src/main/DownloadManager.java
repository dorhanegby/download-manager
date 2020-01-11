package main;

import java.util.ArrayList;

public class DownloadManager {
    String url;
    PartitioningService partitioner;
    ArrayList<Range> ranges;
    public DownloadManager(String url) {
        this(url, 1);
    }
    public DownloadManager(String url, int numberOfThreads) {
        this.url = url;
        long fileSize = FileSizeService.getFileSize(url);
        this.partitioner = new PartitioningService(numberOfThreads, fileSize);
        this.ranges = this.partitioner.getPartitions();
    }

    public void download() {
        for (Range range : ranges) {
            DownloaderContext downloaderContext = new DownloaderContext(url, range);
            Downloader downloader = new Downloader(downloaderContext);
            downloader.run();
        }
    }
}
