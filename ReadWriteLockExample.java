import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample {
    private static int sharedData = 0;
    private static ReadWriteLock lock = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        Runnable readerTask = () -> {
            lock.readLock().lock();
            try {
                System.out.println("Reader Task: " + sharedData);
            } finally {
                lock.readLock().unlock();
            }
        };

        Runnable writerTask = () -> {
            lock.writeLock().lock();
            try {
                sharedData++;
                System.out.println("Writer Task: Incremented shared data");
            } finally {
                lock.writeLock().unlock();
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
