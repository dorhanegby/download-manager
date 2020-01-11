package main;

import java.util.ArrayList;

public class DownloadManager {
    String url;
    PartitioningService partitioner;
    ArrayList<DownloaderContext> downloaderContexts;
    public DownloadManager(String url) {
        this(url, 1);
    }
    public DownloadManager(String url, int numberOfThreads) {
        MetadataHandler metadataHandler = MetadataHandler.getInstance();
        String fileName = FilesUtil.extract(url);

        if(!FilesUtil.isFileExists(MetadataHandler.formatMetadataFile(fileName))) {
            this.url = url;
            downloaderContexts = new ArrayList<>();
            long fileSize = FileSizeService.getFileSize(url);
            this.partitioner = new PartitioningService(numberOfThreads, fileSize);
            for (Range range : this.partitioner.getPartitions()) {
                downloaderContexts.add(new DownloaderContext(url, range));
            }
            metadataHandler.init(fileName , downloaderContexts);
            metadataHandler.serialize();
        }
        else {
            metadataHandler.deserialize(fileName);
            this.downloaderContexts = metadataHandler.getDownloaderContexts();
        }
    }

    public void download() {
        for (DownloaderContext downloaderContext : downloaderContexts) {
            Downloader downloader = new Downloader(downloaderContext);
            downloader.run();
        }
    }
}
