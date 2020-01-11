package main;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

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
            System.out.println(httpURLConnection.getContentLengthLong());

            try (BufferedInputStream in = new BufferedInputStream(url.openStream())) {
                File file = new File("./out/test-file.txt");
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                byte dataBuffer[] = new byte[1024];
                int bytesRead;
                while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                    randomAccessFile.write(dataBuffer, 0, bytesRead);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
