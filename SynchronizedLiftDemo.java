class SynchronizedLift {
    private int currentFloor = 0;

    public synchronized void requestFloor(int destinationFloor) {
        System.out.println("User requesting lift to floor " + destinationFloor);
        // Simulate lift moving to the destination floor
        System.out.println("Lift moving to floor " + destinationFloor);
        this.currentFloor = destinationFloor;
        System.out.println("Lift arrived at floor " + destinationFloor);
    }
}

public class SynchronizedLiftDemo {
    public static void main(String[] args) {
        SynchronizedLift lift = new SynchronizedLift();

        // Users requesting lift to different floors
        new Thread(() -> lift.requestFloor(3)).start();
        new Thread(() -> lift.requestFloor(5)).start();
        new Thread(() -> lift.requestFloor(2)).start();
    }
}
