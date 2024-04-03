import java.util.concurrent.Semaphore;

class Worker implements Runnable {
    private final Semaphore semaphore;

    Worker(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            semaphore.acquire(); // Acquires a permit from this semaphore
            System.out.println(Thread.currentThread().getName() + " is working.");

            // Simulating some work
            Thread.sleep(1000);

            System.out.println(Thread.currentThread().getName() + " has finished its work.");

            semaphore.release(); // Releases a permit, returning it to the semaphore
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class SemaphoreExample {
    public static void main(String[] args) {
        int availablePermits = 2;
        Semaphore semaphore = new Semaphore(availablePermits);

        for (int i = 0; i < 5; i++) {
            new Thread(new Worker(semaphore)).start();
        }
    }
}
