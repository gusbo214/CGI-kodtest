import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * The main class that sets up the HTTP server and handles incoming requests.
 */
public class HemTest {
    /**
     * The main method that starts the HTTP server.
     *
     * @param args Command line arguments (not used).
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        var server = HttpServer.create(new InetSocketAddress(3000), 0);
        var countHandler = new WordCountHandler();
        server.createContext("/count", countHandler);
        server.setExecutor(null);
        server.start();
        System.out.println("Servern är nu uppsatt på http://localhost:3000/count och är redo för att köra Curl commands :) ");
    }
}