import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

class Printer {
    private final BlockingQueue<String> printQueue = new LinkedBlockingQueue<>();

    public void printDocument(String document) {
        System.out.println("Printing document: " + document);
        // Simulate printing process
        System.out.println("Document printed: " + document);
    }

    public void processPrintRequests() {
        while (true) {
            try {
                String document = printQueue.take();
                printDocument(document);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void queuePrintRequest(String document) {
        printQueue.offer(document);
    }
}

public class ActiveObjectPrinterDemo {
    public static void main(String[] args) {
        Printer printer = new Printer();

        // Users printing documents
        new Thread(() -> printer.queuePrintRequest("Document1")).start();
        new Thread(() -> printer.queuePrintRequest("Document2")).start();
        new Thread(() -> printer.queuePrintRequest("Document3")).start();

        // Printer processing print requests
        new Thread(printer::processPrintRequests).start();
    }
}
