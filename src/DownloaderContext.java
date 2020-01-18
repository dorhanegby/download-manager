import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

class DownloaderContext implements Serializable {
    private URL url;
    private Range range;
    private long bytesDownloaded = 0;

    DownloaderContext(String url, Range range) {
        try {
            this.url = new URL(url);
            this.range = range;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public URL getUrl() {
        return url;
    }

    public String getRangeHeader() {
        return "bytes=" + range.getStartByte() + "-" + range.getEndByte();
    }

    public String getRangeForLogging() {
        return "(" + range.getStartByte() + " - " + range.getEndByte() + ")";
    }

    public long getStartByte() {
        return range.getStartByte();
    }

    public boolean isByteInRange(long byteToCompare) {
        return range.getStartByte() <= byteToCompare && byteToCompare <= range.getEndByte();
    }

    public void setRangeStartByte(long byteToSet) {
        this.range.setStartByte(byteToSet);
    }

    public void addDownloadedBytes(long bytesDownloaded){
        this.bytesDownloaded += bytesDownloaded;
    }

    public long getBytesDownloaded() {
        return bytesDownloaded;
    }
}
