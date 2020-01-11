package main;

public class IdcDm {

    public static void main(String[] args) {
        DownloaderContext downloaderContext = new DownloaderContext("https://www.w3.org/TR/PNG/iso_8859-1.txt", new Range(0, 3000));
        Downloader downloader = new Downloader(downloaderContext);
        downloader.run();
        System.out.println("all good");
    }
}
