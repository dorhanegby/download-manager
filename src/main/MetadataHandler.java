package main;

import java.io.*;
import java.util.ArrayList;

public class MetadataHandler implements Serializable {
    private ArrayList<DownloaderContext> downloaderContexts;
    private static MetadataHandler instance;
    String metaFilePath;

    private MetadataHandler() {
    }

    public static MetadataHandler getInstance() {
        if (instance == null) {
            instance = new MetadataHandler();
        }

        return instance;
    }

    public void init(String metaFileName, ArrayList<DownloaderContext> downloaderContexts) {
        this.metaFilePath = MetadataHandler.formatMetadataFile(metaFileName);
        this.downloaderContexts = downloaderContexts;
    }

    public void updateMetadataFile(long startByte) {
        boolean hasChanged = false;
        for (DownloaderContext downloadContext: downloaderContexts) {
            if(downloadContext.isByteInRange(startByte)) {
                downloadContext.setRangeStartByte(startByte);
                hasChanged = true;
            }
        }

        if(hasChanged) {
            serialize();
        }
    }

    public static String formatMetadataFile(String metaFileName) {
        return "./" + metaFileName + ".meta.txt";
    }


    public void serialize() {
        try {
            FileOutputStream file = new FileOutputStream(metaFilePath);
            ObjectOutputStream out = new ObjectOutputStream(file);

            out.writeObject(MetadataHandler.getInstance());

            out.close();
            file.close();

            System.out.println("Object has been serialized");

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.out.println("IOException is caught");
        }
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
}
