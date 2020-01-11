package main;

import org.junit.Test;

import static org.junit.Assert.*;

public class FilesUtilTest {
    @Test
    public void shouldExtractFileName() {
        String mockUrl = "http://centos.activecloud.co.il/6.10/isos/x86_64/CentOS-6.10-x86_64-netinstall.iso";
        String actual = FilesUtil.extract(mockUrl);
        assertEquals(actual, "CentOS-6.10-x86_64-netinstall.iso");
    }

    @Test
    public void shouldSayFileExists_relativePath() {
        boolean actual  = FilesUtil.isFileExists("./.gitignore");
        assertTrue(actual);
    }
    @Test
    public void shouldSayFileExists_absolutePath() {
        boolean actual  = FilesUtil.isFileExists(System.getProperty("user.dir") + "/.gitignore");
        assertTrue(actual);
    }

}