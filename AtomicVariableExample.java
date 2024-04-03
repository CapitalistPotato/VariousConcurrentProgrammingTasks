import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariableExample {
    private static AtomicInteger sharedData = new AtomicInteger(0);

    public static void main(String[] args) {
        Runnable readerTask = () -> {
            System.out.println("Reader Task: " + sharedData.get());
        };

        Runnable writerTask = () -> {
            sharedData.incrementAndGet();
            System.out.println("Writer Task: Incremented shared data");
        };

        // Create multiple reader threads
        for (int i = 0; i < 2; i++) {
            new Thread(readerTask).start();
        }

        // Create a writer thread
        new Thread(writerTask).start();
    }
}
