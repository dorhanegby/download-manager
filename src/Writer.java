import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;

class Writer implements Runnable {

    private final BlockingQueue queue = BlockingQueue.getInstance();
    private final MetadataHandler metadataHandler = MetadataHandler.getInstance();
    private RandomAccessFile randomAccessFile;
    private boolean isCompleted = false;

    Writer(File file) {
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
        randomAccessFile.seek(msg.getCurrentByte());
        randomAccessFile.write(msg.getDataBuffer(), 0, msg.getBytesRead());
        metadataHandler.updateMetadataFile(msg.getCurrentByte(), msg.getBytesRead());
    }

}
