import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockClassExample {
    private static int sharedData = 0;
    private static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        Runnable readerTask = () -> {
            lock.lock();
            try {
                System.out.println("Reader Task: " + sharedData);
            } finally {
                lock.unlock();
            }
        };

        Runnable writerTask = () -> {
            lock.lock();
            try {
                sharedData++;
                System.out.println("Writer Task: Incremented shared data");
            } finally {
                lock.unlock();
            }
        };

        // Create multiple reader threads
        for (int i = 0; i < 2; i++) {
            new Thread(readerTask).start();
        }

        // Create a writer thread
        new Thread(writerTask).start();
    }
}
