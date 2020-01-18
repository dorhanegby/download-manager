package main;

public class IdcDm {

    public static void main(String[] args) {
        String url = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
        DownloadManager dm = new DownloadManager(url);
        try {
            dm.download();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("all good");
    }
}
