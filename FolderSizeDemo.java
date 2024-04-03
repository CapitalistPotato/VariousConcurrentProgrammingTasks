import java.io.File;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class FolderSizeTask extends RecursiveTask<Long> {
    private final File folder;

    public FolderSizeTask(File folder) {
        this.folder = folder;
    }

    @Override
    protected Long compute() {
        long size = 0;

        File[] content = folder.listFiles();
        if (content != null) {
            for (File file : content) {
                if (file.isDirectory()) {
                    FolderSizeTask subtask = new FolderSizeTask(file);
                    size += subtask.fork().join();
                } else {
                    size += file.length();
                }
            }
        }

        return size;
    }
}

public class FolderSizeDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        File folder = new File("/path/to/your/folder");
        FolderSizeTask task = new FolderSizeTask(folder);

        long totalSize = forkJoinPool.invoke(task);

        System.out.println("Total space used by files in the folder: " + totalSize + " bytes");
    }
}
