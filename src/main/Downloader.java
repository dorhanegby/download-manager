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
            MetadataHandler metadataHandler = MetadataHandler.getInstance();
            long fileSize =  httpURLConnection.getContentLengthLong();
            System.out.println(fileSize);
            try (BufferedInputStream in = new BufferedInputStream(httpURLConnection.getInputStream())) {
                File file = new File("./out/test-file.txt");
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                long currentByte = downloaderContext.getStartByte();
                randomAccessFile.seek(currentByte);
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    randomAccessFile.write(dataBuffer, 0, bytesRead);
                    System.out.println("Wrote bytes: " + currentByte + " - " + (currentByte + bytesRead));
                    currentByte += bytesRead;
                    System.out.println("Progress: " + Math.round(((double) currentByte / fileSize) * 100) + "%");
                    metadataHandler.updateMetadataFile(currentByte);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
