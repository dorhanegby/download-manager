package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class Downloader implements Runnable {
    private DownloaderContext downloaderContext;

    public Downloader(DownloaderContext downloaderContext) {
        this.downloaderContext = downloaderContext;
    }

    @Override
    public void run() {
        try {
            URL url = downloaderContext.getUrl();
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Range", downloaderContext.getRangeHeader());
            System.out.println("Range: " + downloaderContext.getRangeHeader());
            httpURLConnection.connect();
            BlockingQueue queue = BlockingQueue.getInstance();
            try (BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream())) {

                long currentByte = downloaderContext.getStartByte();
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    queue.enqueue(new Message(dataBuffer, bytesRead, currentByte));
                    currentByte += bytesRead;
                    dataBuffer = new byte[1024];
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
