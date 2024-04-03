import java.util.concurrent.Exchanger;

class Task implements Runnable {
    private final Exchanger<String> exchanger;

    Task(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
    }

    @Override
    public void run() {
        try {
            // Simulating some work
            Thread.sleep(1000);
            System.out
                    .println(Thread.currentThread().getName() + " has completed its work and wants to exchange data.");

            String data = "Data from " + Thread.currentThread().getName();
            String receivedData = exchanger.exchange(data);

            System.out.println(Thread.currentThread().getName() + " received: " + receivedData);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Task(exchanger)).start();
        new Thread(new Task(exchanger)).start();
    }
}
