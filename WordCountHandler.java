import java.io.IOException;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.google.gson.Gson;

public class WordCountHandler implements HttpHandler {

    /**
     * Handles incoming HTTP requests.
     *
     * @param exchange The HttpExchange object that contains the request and response.
     * @throws IOException If an I/O error occurs.
     */
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
        
            // Read the request body using UTF-8 encoding, which allows for the use of special characters ÅÄÖ etc.
            var requestBody = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8); // Read the request body using UTF-8 encoding
            var wordCounter = new WordCounter();
            var sortedWordCounts = wordCounter.countWords(requestBody);

            // Convert the sortedWordCounts map to a JSON string using Gson
            Gson gson = new Gson();
            var jsonResponse = gson.toJson(sortedWordCounts);

            // Set the response headers and body using UTF-8 encoding
            exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
            exchange.sendResponseHeaders(200, jsonResponse.getBytes(StandardCharsets.UTF_8).length);
            try (var os = exchange.getResponseBody()) {
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
        }
    }
}