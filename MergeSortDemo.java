import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

class MergeSortTask extends RecursiveAction {
    private final int[] array;
    private final int low;
    private final int high;

    public MergeSortTask(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    @Override
    protected void compute() {
        if (low < high) {
            int mid = (low + high) >>> 1;

            invokeAll(new MergeSortTask(array, low, mid),
                      new MergeSortTask(array, mid + 1, high));

            merge(array, low, mid, high);
        }
    }

    private void merge(int[] array, int low, int mid, int high) {
        int leftLength = mid - low + 1;
        int rightLength = high - mid;

        int[] leftArray = Arrays.copyOfRange(array, low, low + leftLength);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, mid + 1 + rightLength);

        int i = 0, j = 0, k = low;

        while (i < leftLength && j < rightLength) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < leftLength) {
            array[k++] = leftArray[i++];
        }

        while (j < rightLength) {
            array[k++] = rightArray[j++];
        }
    }
}

public class MergeSortDemo {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();

        int[] array = {5, 3, 8, 4, 2, 7, 1, 6};
        MergeSortTask task = new MergeSortTask(array, 0, array.length - 1);

        forkJoinPool.invoke(task);

        System.out.println("Sorted Array: " + Arrays.toString(array));
    }
}
