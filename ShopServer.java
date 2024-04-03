import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

class ShopServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12346)) {
            System.out.println("Shop Server is running...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     InputStream input = clientSocket.getInputStream();
                     OutputStream output = clientSocket.getOutputStream()) {

                    byte[] buffer = new byte[1024];
                    int bytesRead = input.read(buffer);
                    String request = new String(buffer, 0, bytesRead);

                    // Process the request (e.g., handle product purchases)
                    String response = processShopRequest(request);

                    output.write(response.getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processShopRequest(String request) {
        // Placeholder for processing logic (e.g., handle product purchases)
        return "Processed: " + request;
    }
}


/*Asynch
 * import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ShopServer {
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12346)) {
            System.out.println("Shop Server is running...");

            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    CompletableFuture.runAsync(() -> handleClient(clientSocket), executorService);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (InputStream input = clientSocket.getInputStream();
             OutputStream output = clientSocket.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead = input.read(buffer);
            String request = new String(buffer, 0, bytesRead);

            // Process the request (e.g., handle product purchases)
            String response = processShopRequest(request);

            output.write(response.getBytes());
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String processShopRequest(String request) {
        // Placeholder for processing logic (e.g., handle product purchases)
        return "Processed: " + request;
    }
}

 */