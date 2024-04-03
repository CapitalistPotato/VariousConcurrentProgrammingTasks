public class SharedVariableExample {
    private static volatile long volatileVariable = 0;
    private static long synchronizedVariable = 0;

    public static void main(String[] args) {
        Thread volatileThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                volatileVariable++;
                System.out.println("Volatile: " + volatileVariable);
            }
        });

        Thread synchronizedThread = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (SharedVariableExample.class) {
                    synchronizedVariable++;
                    System.out.println("Synchronized: " + synchronizedVariable);
                }
            }
        });

        volatileThread.start();
        synchronizedThread.start();
    }
}

