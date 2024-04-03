import java.util.concurrent.SynchronousQueue;

class Producer implements Runnable {
    private final SynchronousQueue<String> queue;

    Producer(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String data = "Produced data";
            System.out.println(Thread.currentThread().getName() + " producing: " + data);
            queue.put(data); // Inserts the specified element into this queue, waiting if necessary for another thread to receive it
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Consumer implements Runnable {
    private final SynchronousQueue<String> queue;

    Consumer(SynchronousQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            String data = queue.take(); // Retrieves and removes the element from this queue, waiting if necessary for another thread to insert it
            System.out.println(Thread.currentThread().getName() + " consuming: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SynchronousQueueExample {
    public static void main(String[] args) {
        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }
}
