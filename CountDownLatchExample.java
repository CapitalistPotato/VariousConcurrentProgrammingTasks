import java.util.concurrent.CountDownLatch;

class Task implements Runnable {
    private final CountDownLatch latch;

    Task(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            // Simulating some task
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " has completed the task.");
            latch.countDown(); // Decrements the count of the latch
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        int tasksCount = 3;
        CountDownLatch latch = new CountDownLatch(tasksCount);

        for (int i = 0; i < tasksCount; i++) {
            new Thread(new Task(latch)).start();
        }

        latch.await(); // Waits until the latch has counted down to zero
        System.out.println("All tasks have been completed. Continue with the main process.");
    }
}
