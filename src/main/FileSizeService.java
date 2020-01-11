package main;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileSizeService {
    public static long getFileSize(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("HEAD");
            return httpConnection.getContentLengthLong();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return -1;
        }
    }
}
