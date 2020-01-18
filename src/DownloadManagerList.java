import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

class DownloadManagerList extends AbstractDownloadManager {

    private final ArrayList<String> urls = new ArrayList<>();


    DownloadManagerList(String filePath, int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;

        String line;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("./" + filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int amountOfUrlsToRun = numberOfThreads;

        while (Objects.requireNonNull(scanner).hasNextLine() && amountOfUrlsToRun > 0) {
            line = scanner.nextLine();
            amountOfUrlsToRun--;
            urls.add(line);
        }

        String fileName = FilesUtil.extract(urls.get(0));
        File file = new File(fileName);
        writer = new Writer(file);
        if (!FilesUtil.isFileExists(MetadataHandler.formatMetadataFile(fileName))) {
            startNewDownload();
        } else {
            resumeDownload(fileName);
        }
    }

    private void resumeDownload(String filePath) {
        String fileName = FilesUtil.extract(filePath);
        MetadataHandler metadataHandler = MetadataHandler.getInstance();
        metadataHandler.deserialize(fileName);
        this.downloaderContexts = metadataHandler.getDownloaderContexts();
    }

    private void startNewDownload() {
        long fileSize = FileSizeService.getFileSize(urls.get(0));
        String fileName = FilesUtil.extract(urls.get(0));

        PartitioningService partitioner = new PartitioningService(urls.size(), fileSize);
        ArrayList<Range> partitions = partitioner.getPartitions();
        downloaderContexts = new ArrayList<>();

        for (int i = 0; i < urls.size(); i++) {
            Range range = partitions.get(i);
            String url = urls.get(i);
            downloaderContexts.add(new DownloaderContext(url, range));
        }

        MetadataHandler metadataHandler = MetadataHandler.getInstance();
        metadataHandler.init(fileName, downloaderContexts, fileSize);
        metadataHandler.serialize();
    }
}
