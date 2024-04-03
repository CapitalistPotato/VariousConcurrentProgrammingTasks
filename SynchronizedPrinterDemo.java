class SynchronizedPrinter {
    public synchronized void printDocument(String document) {
        System.out.println("Printing document: " + document);
        // Simulate printing process
        System.out.println("Document printed: " + document);
    }
}

public class SynchronizedPrinterDemo {
    public static void main(String[] args) {
        SynchronizedPrinter printer = new SynchronizedPrinter();

        // Users printing documents
        new Thread(() -> printer.printDocument("Document1")).start();
        new Thread(() -> printer.printDocument("Document2")).start();
        new Thread(() -> printer.printDocument("Document3")).start();
    }
}
