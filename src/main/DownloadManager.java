package main;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadManager {
    private ArrayList<DownloaderContext> downloaderContexts;
    private Writer writer;
    int numberOfThreads;

    public DownloadManager(String url) {
        this(url, 1);
    }
    public DownloadManager(String url, int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
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
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads + 1);
        executor.execute(writer);

        for (DownloaderContext downloaderContext : downloaderContexts) {
            Downloader downloader = new Downloader(downloaderContext);
            executor.execute(downloader);
        }

        // waiting for executor service to terminate
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        System.out.println("Finished all threads");
    }

}
