import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class Downloader implements Runnable {
    private final DownloaderContext downloaderContext;

    Downloader(DownloaderContext downloaderContext) {
        this.downloaderContext = downloaderContext;
    }

    @Override
    public void run() {
        try {
            long threadId = Thread.currentThread().getId();
            URL url = downloaderContext.getUrl();
            System.out.println("[" + threadId + "]" + " Start downloading range "
                    + downloaderContext.getRangeForLogging() + " from:" + url.getPath());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Range", downloaderContext.getRangeHeader());
            httpURLConnection.connect();
            BlockingQueue queue = BlockingQueue.getInstance();
            try (BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream())) {

                long currentByte = downloaderContext.getStartByte();
                byte[] dataBuffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    queue.enqueue(new Message(dataBuffer, bytesRead, currentByte));
                    currentByte += bytesRead;
                    dataBuffer = new byte[1024];
                }
            }
            System.out.println("[" + threadId + "]" + " Finished downloading");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
