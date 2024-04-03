import java.util.*;
import java.util.concurrent.*;

class DataRepository {
    private final Set<Integer> customDataStore = new HashSet<>();
    private final Object customDataStoreLock = new Object();
    private final Set<Integer> synchronizedDataStore = Collections.synchronizedSet(new HashSet<>());
    private final Set<Integer> sortedDataStore = new ConcurrentSkipListSet<>();
    private final Set<Integer> copyOnWriteDataStore = new CopyOnWriteArraySet<>();

    public void addToCustomDataStore(int value) {
        synchronized (customDataStoreLock) {
            customDataStore.add(value);
        }
    }

    public void addToSynchronizedDataStore(int value) {
        synchronized (synchronizedDataStore) {
            synchronizedDataStore.add(value);
        }
    }

    public void addToSortedDataStore(int value) {
        sortedDataStore.add(value);
    }

    public void addToCopyOnWriteDataStore(int value) {
        copyOnWriteDataStore.add(value);
    }

    public Set<Integer> getCustomDataStore() {
        synchronized (customDataStoreLock) {
            return new HashSet<>(customDataStore);
        }
    }

    public Set<Integer> getSynchronizedDataStore() {
        return new HashSet<>(synchronizedDataStore);
    }

    public Set<Integer> getSortedDataStore() {
        return new HashSet<>(sortedDataStore);
    }

    public Set<Integer> getCopyOnWriteDataStore() {
        return new HashSet<>(copyOnWriteDataStore);
    }
}

class WriterTask extends Thread {
    private final DataRepository dataRepository;
    private final int value;

    public WriterTask(DataRepository dataRepository, int value) {
        this.dataRepository = dataRepository;
        this.value = value;
    }

    @Override
    public void run() {
        dataRepository.addToCustomDataStore(value);
        dataRepository.addToSynchronizedDataStore(value);
        dataRepository.addToSortedDataStore(value);
        dataRepository.addToCopyOnWriteDataStore(value);
    }
}

class ReaderTask extends Thread {
    private final DataRepository dataRepository;

    public ReaderTask(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public void run() {
        Set<Integer> customDataStore = dataRepository.getCustomDataStore();
        Set<Integer> synchronizedDataStore = dataRepository.getSynchronizedDataStore();
        Set<Integer> sortedDataStore = dataRepository.getSortedDataStore();
        Set<Integer> copyOnWriteDataStore = dataRepository.getCopyOnWriteDataStore();

        // Perform read operations on the sets
        // ...

        System.out.println("Custom DataStore: " + customDataStore);
        System.out.println("Synchronized DataStore: " + synchronizedDataStore);
        System.out.println("Sorted DataStore: " + sortedDataStore);
        System.out.println("CopyOnWrite DataStore: " + copyOnWriteDataStore);
    }
}

public class DataStorageApp {
    public static void main(String[] args) {
        DataRepository dataRepository = new DataRepository();
        int numTasks = 5;

        // Create and start writer tasks
        List<Thread> writerTasks = new ArrayList<>();
        for (int i = 1; i <= numTasks; i++)
            writerTasks.add(new WriterTask(dataRepository, i));
        writerTasks.forEach(Thread::start);

        // Create and start reader task
        Thread readerTask = new ReaderTask(dataRepository);
        readerTask.start();

        // Wait for all tasks to finish
        try {
            writerTasks.forEach(task -> {
                try {
                    task.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            readerTask.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
