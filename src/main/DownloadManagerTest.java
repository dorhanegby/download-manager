package main;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

import static org.junit.Assert.*;

public class DownloadManagerTest {
    private void deleteFile(String path) {
        File file = new File(path);
        if(file.delete()) {
            System.out.println("File deleted successfully");
        }else
        {
            System.out.println("Failed to delete the file");
        }
    }
    @Test
    public void shouldDownloadTheRightFile(){
        try {
            String url = "https://www.w3.org/TR/PNG/iso_8859-1.txt";
            DownloadManager dm = new DownloadManager(url);
            dm.download();

            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get("./out/iso_8859-1.txt")));
            byte[] digest = md.digest();
            StringBuffer hexString = new StringBuffer();

            for (int i = 0;i<digest.length;i++) {
                hexString.append(Integer.toHexString(0xFF & digest[i]));
            }
            assertEquals(hexString.toString(), "8026e3afd2e13dab1f5c53bf1c353");
            deleteFile("./out/iso_8859-1.txt");
            deleteFile("./iso_8859-1.txt.meta.txt");
        }catch (Exception e) {

        }

    }
}