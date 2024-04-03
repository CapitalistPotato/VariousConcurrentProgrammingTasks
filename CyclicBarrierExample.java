import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Worker implements Runnable {
    private final CyclicBarrier barrier;

    Worker(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            // Simulating some work
            Thread.sleep(1000);
            System.out.println(
                    Thread.currentThread().getName() + " has finished its work and is waiting at the barrier.");
            barrier.await(); // Waits until all parties have invoked await on this barrier
            System.out.println(Thread.currentThread().getName() + " continues after the barrier.");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

public class CyclicBarrierExample {
    public static void main(String[] args) {
        int workersCount = 3;
        CyclicBarrier barrier = new CyclicBarrier(workersCount,
                () -> System.out.println("All workers have reached the barrier. Continue with the main process."));

        for (int i = 0; i < workersCount; i++) {
            new Thread(new Worker(barrier)).start();
        }
    }
}
