import java.util.LinkedList;

class BlockingQueue {

    private static BlockingQueue instance;
    private final LinkedList<Message> queue = new LinkedList<>();
    private final int LIMIT = 1000;
    private BlockingQueue() {
    }

    static BlockingQueue getInstance() {
        if(instance == null) {
           instance = new BlockingQueue();
        }

        return  instance;
    }


    synchronized void enqueue(Message item) throws InterruptedException {
        while (this.queue.size() == this.LIMIT) {
            wait();
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(item);
    }
    synchronized Message dequeue() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == this.LIMIT) {
            notifyAll();
        }
        return this.queue.remove(0);
    }
}
