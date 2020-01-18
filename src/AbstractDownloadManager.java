import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractDownloadManager {
    ArrayList<DownloaderContext> downloaderContexts;
    Writer writer;
    int numberOfThreads;


    public void download() {
        System.out.println(String.format("Downloading using %d connections...", numberOfThreads));
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

        String filePath = downloaderContexts.get(0).getUrl().getPath();
        deleteFile(FilesUtil.extract(MetadataHandler.formatMetadataFile(filePath)));
    }

    private void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }
}
