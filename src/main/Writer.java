package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

public class Writer implements Runnable {

    private BlockingQueue queue = BlockingQueue.getInstance();
    private MetadataHandler metadataHandler = MetadataHandler.getInstance();
    private RandomAccessFile randomAccessFile;
    public boolean isCompleted = false;

    public Writer(File file) {
        try {
            this.randomAccessFile = new RandomAccessFile(file, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(!isCompleted) {
            try {
                consume();
                isCompleted = metadataHandler.isDownloadCompleted();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void consume() throws Exception {
        Message msg = queue.dequeue();
        System.out.println("Writer :: Received Message, Current Byte: " + msg.getCurrentByte());
        System.out.println("Writer :: Received Message, getBytesRead: " + msg.getBytesRead());

        randomAccessFile.seek(msg.getCurrentByte());
        randomAccessFile.write(msg.getDataBuffer(), 0, msg.getBytesRead());
        metadataHandler.updateMetadataFile(msg.getCurrentByte());
    }

}
