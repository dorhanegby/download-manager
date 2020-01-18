package main;

import java.io.File;
import java.util.ArrayList;

public class DownloadManager {
    private ArrayList<DownloaderContext> downloaderContexts;
    private Writer writer;
    public DownloadManager(String url) {
        this(url, 1);
    }
    public DownloadManager(String url, int numberOfThreads) {
        String fileName = FilesUtil.extract(url);
        File file = new File("./out/" + fileName);
        writer = new Writer(file);
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
        PartitioningService partitioner = new PartitioningService(numberOfThreads, fileSize);
        for (Range range : partitioner.getPartitions()) {
            downloaderContexts.add(new DownloaderContext(url, range));
        }
        metadataHandler.init(fileName , downloaderContexts, fileSize);
        metadataHandler.serialize();
    }


    public void download() {
        Thread writerThread = new Thread(writer);
        writerThread.start();
        for (DownloaderContext downloaderContext : downloaderContexts) {
            Downloader downloader = new Downloader(downloaderContext);
            new Thread(downloader).start();
        }
    }
}
