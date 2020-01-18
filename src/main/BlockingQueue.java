package main;

import java.util.LinkedList;

public class BlockingQueue {

    private static BlockingQueue instance;
    private LinkedList<Message> queue = new LinkedList<>();
    private int limit = 1000;
    private BlockingQueue() {
    }

    public static BlockingQueue getInstance() {
        if(instance == null) {
           instance = new BlockingQueue();
        }

        return  instance;
    }


    public synchronized void enqueue(Message item) throws InterruptedException {
        while (this.queue.size() == this.limit) {
            wait();
        }
        if (this.queue.size() == 0) {
            notifyAll();
        }
        this.queue.add(item);
    }
    public synchronized Message dequeue() throws InterruptedException {
        while (this.queue.size() == 0) {
            wait();
        }
        if (this.queue.size() == this.limit) {
            notifyAll();
        }
        Message msg = this.queue.remove(0);
        return msg;
    }
}
