import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class PoisonPillsCancellation {
    public static void main(String[] args) {
        BlockingQueue<String> taskQueue = new LinkedBlockingQueue<>();

        Thread taskThread = new Thread(() -> {
            try {
                while (true) {
                    String task = taskQueue.take();
                    if ("poisonPill".equals(task)) {
                        break;
                    }
                    // Task logic
                    System.out.println("Task is running: " + task);
                }
                System.out.println("Task is cancelled.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        taskThread.start();

        // Simulate tasks
        taskQueue.offer("Task 1");
        taskQueue.offer("Task 2");

        // Simulate cancellation after some time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        taskQueue.offer("poisonPill");

        // Wait for the task thread to finish
        try {
            taskThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
