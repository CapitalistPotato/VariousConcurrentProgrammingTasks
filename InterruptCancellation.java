public class InterruptCancellation {
    public static void main(String[] args) {
        Thread taskThread = new Thread(() -> {
            while (!Thread.interrupted()) {
                // Task logic
                System.out.println("Task is running...");
            }
            System.out.println("Task is cancelled.");
        });

        taskThread.start();

        // Simulate cancellation after some time
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        taskThread.interrupt();
    }
}
