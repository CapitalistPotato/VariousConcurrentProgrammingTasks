import java.util.concurrent.Phaser;

class MyTask implements Runnable {
    private final Phaser phaser;

    MyTask(Phaser phaser) {
        this.phaser = phaser;
    }

    @Override
    public void run() {
        // Phase 1
        System.out.println(Thread.currentThread().getName() + " started phase 1");
        phaser.arriveAndAwaitAdvance(); // Waits for all parties to arrive at this barrier

        // Phase 2
        System.out.println(Thread.currentThread().getName() + " started phase 2");
        phaser.arriveAndAwaitAdvance();

        // Phase 3
        System.out.println(Thread.currentThread().getName() + " started phase 3");
        phaser.arriveAndAwaitAdvance();
    }
}

public class PhaserExample {
    public static void main(String[] args) {
        int tasksCount = 3;
        Phaser phaser = new Phaser(tasksCount);

        for (int i = 0; i < tasksCount; i++) {
            new Thread(new MyTask(phaser)).start();
        }
    }
}
