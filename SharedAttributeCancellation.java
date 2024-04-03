public class SharedAttributeCancellation {
    private static volatile boolean isCancelled = false;

    public static void main(String[] args) {
        Runnable task = () -> {
            while (!isCancelled) {
                // Task logic
                System.out.println("Task is running...");
            }
            System.out.println("Task is cancelled.");
        };

        Thread thread = new Thread(task);
        thread.start();

        // Simulate cancellation after some time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isCancelled = true;
    }
}
