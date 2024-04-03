import java.util.concurrent.*;

public class FutureCancellation {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<?> future = executorService.submit(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                // Task logic
                System.out.println("Task is running...");
            }
            System.out.println("Task is cancelled.");
        });

        // Simulate cancellation after some time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        future.cancel(true);
        executorService.shutdown();
    }
}
