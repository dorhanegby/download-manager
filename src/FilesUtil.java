import java.io.File;

class FilesUtil {
    private FilesUtil() {}

    static String extract(String url) {
        String[] split = url.split("/");
        return split[split.length - 1];
    }

    static boolean isFileExists(String relativePath) {
        return new File(relativePath).exists();
    }
}
