import java.net.URL;

public class DownloaderContext {
    private URL url;
    private Range range;

    public DownloaderContext(URL url, Range range) {
        this.url = url;
        this.range = range;
    }

    public String getRangeHeader() {
        return "bytes=" + range.getStartByte()+ "-" + range.getEndByte();
    }
}
