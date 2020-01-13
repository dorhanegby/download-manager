package main.test;

import main.DownloadManager;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DownloadManagerTest {
    private void deleteFile(String path) {
        File file = new File(path);
        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }
    }

    @Test
    public void shouldDownloadTheRightFile() {
        String url = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
        DownloadManager dm = new DownloadManager(url);
        dm.download();
        String actual = getMD5Hash("./out/iso_8859-1.txt");
        assertEquals("8026e3afd2e13dab1f5c53bf1c353", actual);
        deleteFile("./out/iso_8859-1.txt");
        deleteFile("./iso_8859-1.txt.meta");
    }

    @Test
    public void shouldDownloadAfterPauseAndResume() {
        try {
            Thread t1 = new Thread(() -> {
                try {
                    String url = "http://mirror.filearena.net/pub/speed/SpeedTest_16MB.dat";
                    DownloadManager dm = new DownloadManager(url);
                    dm.download();
                } catch (Exception e) {
                    // handle: log or throw in a wrapped RuntimeException
                    throw new RuntimeException("InterruptedException caught in lambda", e);
                }
            });
            t1.start();
            TimeUnit.SECONDS.sleep(2);
            System.out.println("resumed");
            t1.interrupt();
            t1.run();
            String actual = getMD5Hash("./out/SpeedTest_16MB.dat");
            deleteFile("./out/SpeedTest_16MB.dat");
            deleteFile("./SpeedTest_16MB.dat.meta");
            assertEquals("2c7ab85a893283e98c931e9511add182", actual);
        }
        catch(Exception e) {

        }
    }

    private String getMD5Hash(String path) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(path)));
            byte[] digest = md.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }

            return hexString.toString();
        } catch (Exception e) {

        }

        return "";
    }
}