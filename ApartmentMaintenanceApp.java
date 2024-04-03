import java.util.Random;
import java.util.concurrent.*;

class Tenant implements Runnable {
    private final int tenantId;
    private final BlockingQueue<String> damageReportQueue;
    private final double damageProbability;
    private final int checkInterval;

    public Tenant(int tenantId, BlockingQueue<String> damageReportQueue, double damageProbability, int checkInterval) {
        this.tenantId = tenantId;
        this.damageReportQueue = damageReportQueue;
        this.damageProbability = damageProbability;
        this.checkInterval = checkInterval;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                TimeUnit.SECONDS.sleep(checkInterval);

                if (random.nextDouble() < damageProbability) {
                    String damageDescription = "Damage detected in Apartment " + tenantId;
                    damageReportQueue.put(damageDescription);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class RepairPerson implements Runnable {
    private final BlockingQueue<String> damageReportQueue;
    private final int maxRepairTime;

    public RepairPerson(BlockingQueue<String> damageReportQueue, int maxRepairTime) {
        this.damageReportQueue = damageReportQueue;
        this.maxRepairTime = maxRepairTime;
    }

    @Override
    public void run() {
        Random random = new Random();
        try {
            while (true) {
                String damageDescription = damageReportQueue.take();
                System.out.println("Repair Person is fixing: " + damageDescription);
                TimeUnit.SECONDS.sleep(random.nextInt(maxRepairTime) + 1);
                System.out.println("Issue fixed: " + damageDescription);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

public class ApartmentMaintenanceApp {
    public static void main(String[] args) {
        int numTenants = 5;
        int checkInterval = 10; // seconds
        double damageProbability = 0.1;
        int maxRepairTime = 5; // seconds

        BlockingQueue<String> damageReportQueue = new LinkedBlockingQueue<>();

        ExecutorService executorService = Executors.newCachedThreadPool();

        // Create and start tenant threads
        for (int i = 1; i <= numTenants; i++) {
            executorService.execute(new Tenant(i, damageReportQueue, damageProbability, checkInterval));
        }

        // Create and start repair person thread
        executorService.execute(new RepairPerson(damageReportQueue, maxRepairTime));

        // Shutdown the executor service
        executorService.shutdown();
    }
}
