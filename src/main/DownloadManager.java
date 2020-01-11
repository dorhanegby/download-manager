package main;

import java.util.ArrayList;

public class DownloadManager {
    PartitioningService partitioner;
    ArrayList<DownloaderContext> downloaderContexts;
    public DownloadManager(String url) {
        this(url, 1);
    }
    public DownloadManager(String url, int numberOfThreads) {
        String fileName = FilesUtil.extract(url);

        if(!FilesUtil.isFileExists(MetadataHandler.formatMetadataFile(fileName))) {
            startNewDownload(url, numberOfThreads);
        }
        else {
            resumeDownload(url);
        }
    }

    private void resumeDownload(String url) {
        String fileName = FilesUtil.extract(url);
        MetadataHandler metadataHandler = MetadataHandler.getInstance();
        metadataHandler.deserialize(fileName);
        this.downloaderContexts = metadataHandler.getDownloaderContexts();
    }

    private void startNewDownload(String url, int numberOfThreads){
        String fileName = FilesUtil.extract(url);
        MetadataHandler metadataHandler = MetadataHandler.getInstance();
        downloaderContexts = new ArrayList<>();
        long fileSize = FileSizeService.getFileSize(url);
        this.partitioner = new PartitioningService(numberOfThreads, fileSize);
        for (Range range : this.partitioner.getPartitions()) {
            downloaderContexts.add(new DownloaderContext(url, range));
        }
        metadataHandler.init(fileName , downloaderContexts);
        metadataHandler.serialize();
    }


    public void download() {
        for (DownloaderContext downloaderContext : downloaderContexts) {
            Downloader downloader = new Downloader(downloaderContext);
            downloader.run();
        }
    }
}
