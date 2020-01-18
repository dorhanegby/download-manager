import java.io.File;
import java.util.ArrayList;

public class DownloadManagerUrl extends AbstractDownloadManager{

    public DownloadManagerUrl(String url) {
        this(url, 1);
    }
    public DownloadManagerUrl(String url, int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
        String fileName = FilesUtil.extract(url);
        File file = new File(fileName);
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
}
