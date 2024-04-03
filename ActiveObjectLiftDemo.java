import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Lift {
    private int currentFloor = 0;
    private final BlockingQueue<Integer> requestQueue = new LinkedBlockingQueue<>();

    public void requestFloor(int destinationFloor) {
        System.out.println("User requesting lift to floor " + destinationFloor);
        requestQueue.offer(destinationFloor);
    }

    public void processRequests() {
        while (true) {
            try {
                int destinationFloor = requestQueue.take();
                System.out.println("Lift moving to floor " + destinationFloor);
                this.currentFloor = destinationFloor;
                System.out.println("Lift arrived at floor " + destinationFloor);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class ActiveObjectLiftDemo {
    public static void main(String[] args) {
        Lift lift = new Lift();

        // Users requesting lift to different floors
        new Thread(() -> lift.requestFloor(3)).start();
        new Thread(() -> lift.requestFloor(5)).start();
        new Thread(() -> lift.requestFloor(2)).start();

        // Lift processing requests
        new Thread(lift::processRequests).start();
    }
}
