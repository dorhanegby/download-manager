package main;

import java.net.MalformedURLException;
import java.net.URL;

public class DownloaderContext {
    private URL url;
    private Range range;

    public DownloaderContext(String url, Range range) {
        try {
            this.url = new URL(url);
            this.range = range;
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
    }

    public URL getUrl() {
        return url;
    }

    public String getRangeHeader() {
        return "bytes=" + range.getStartByte() + "-" + range.getEndByte();
    }
}
