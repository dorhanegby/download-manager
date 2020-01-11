package main;

import java.io.File;

public class FilesUtil {
    private FilesUtil() {}

    public static String extract(String url) {
        String[] splitted = url.split("/");
        return splitted[splitted.length - 1];
    }

    public static boolean isFileExists(String relativePath) {
        return new File(relativePath).exists();
    }
}
