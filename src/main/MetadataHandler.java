package main;

import java.io.*;
import java.util.ArrayList;

public class MetadataHandler implements Serializable {
    private ArrayList<DownloaderContext> downloaderContexts;
    private static MetadataHandler instance;
    private ProgressService progressService;
    private String metaFilePath;
    private String tempPath = "./temp.meta";

    private MetadataHandler() {
    }

    public static MetadataHandler getInstance() {
        if (instance == null) {
            instance = new MetadataHandler();
        }

        return instance;
    }

    public void init(String metaFileName, ArrayList<DownloaderContext> downloaderContexts, long fileSize) {
        this.metaFilePath = MetadataHandler.formatMetadataFile(metaFileName);
        this.downloaderContexts = downloaderContexts;
        progressService = new ProgressService(0, fileSize);
    }

    public void updateMetadataFile(long currentByte, int bytesRead) {
        boolean hasChanged = false;
        for (DownloaderContext downloadContext : instance.downloaderContexts) {
            if (downloadContext.isByteInRange(currentByte)) {
                downloadContext.setRangeStartByte(currentByte);
                instance.progressService.incrementBytesDownloded(bytesRead);
                instance.progressService.displayProgress();
                hasChanged = true;
            }
        }

        if(hasChanged) {
            serialize();
        }
    }

    public static String formatMetadataFile(String metaFileName) {
        return "./" + metaFileName + ".meta";
    }


    public void serialize() {
        try {
            FileOutputStream file = new FileOutputStream(tempPath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(MetadataHandler.getInstance());

            out.close();
            file.close();

            renameMetadataFile();

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("IOException is caught");
        }
    }

    private void renameMetadataFile() {
        File realMetadataFile = new File(instance.metaFilePath);
        File tempMetadataFile = new File(instance.tempPath);

        tempMetadataFile.renameTo(realMetadataFile);
    }

    public void deserialize(String fileName) {
        try
        {
            String formattedFileName = MetadataHandler.formatMetadataFile(fileName);
            System.out.println(formattedFileName);
            FileInputStream file = new FileInputStream(formattedFileName);
            ObjectInputStream in = new ObjectInputStream(file);

            instance = (MetadataHandler) in.readObject();

            in.close();
            file.close();

            long fileSizeDownloaded = 0;
            for (DownloaderContext downloaderContext : instance.getDownloaderContexts()){
                fileSizeDownloaded += downloaderContext.getStartByte();
            }

            instance.progressService.setTotalBytesDownloaded(fileSizeDownloaded);

            System.out.println("Object has been deserialized ");
            System.out.println(instance.downloaderContexts);
        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    public ArrayList<DownloaderContext> getDownloaderContexts() {
        return instance.downloaderContexts;
    }

    public boolean isDownloadCompleted() {
        return instance.progressService.isDownloadCompleted();
    }
}
