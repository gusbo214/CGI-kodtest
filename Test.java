import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.nio.charset.StandardCharsets;

/**
 * The Test class sends HTTP POST requests to a specified API endpoint
 * and prints the responses. It also demonstrates concurrent requests
 * using multiple threads.
 */
public class Test {
    private final static String API_URL = "http://localhost:3000/count";

    /**
     * The main method that starts the test.
     *
     * @param args Command line arguments (not used).
     * @throws Exception If an error occurs during the HTTP requests.
     */
    public static void main(String[] args) throws Exception {
        // Array of test strings to send to the API
        String[] testStrings = {
            "Hello, world!",
            "Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!",
            "Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world! Hello, world!",
        };

        // Loop through each test string and send an HTTP POST request
        for (String testString : testStrings) {
            System.out.println("Test String: " + testString);
            HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(API_URL))
                .header("Content-Type", "text/plain; charset=UTF-8")
                .POST(HttpRequest.BodyPublishers.ofString(testString))
                .build();
            System.out.println("Request Body: " + testString);
            HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

            System.out.println("Response Body: " + response.body());
            System.out.println("-----------------------------");
        }

        // Create and start threads for concurrent requests
        Thread user1 = new Thread(new UserTask("User 1", "hello world hello"));
        Thread user2 = new Thread(new UserTask("User 2", "this is a test this is only a test"));

        user1.start();
        user2.start();

        // Wait for both threads to finish
        try {
            user1.join();
            user2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("All users have finished sending requests.");
    }

    /**
     * The UserTask class implements the Runnable interface and represents
     * a task that sends multiple HTTP POST requests to the API.
     */
    static class UserTask implements Runnable {
        private final String user;
        private final String data;

        /**
         * Constructs a new UserTask.
         *
         * @param user The name of the user.
         * @param data The data to send in the HTTP POST request.
         */
        public UserTask(String user, String data) {
            this.user = user;
            this.data = data;
        }

        /**
         * The run method sends multiple HTTP POST requests to the API
         * and prints the responses.
         */
        @Override
        public void run() {
            HttpClient client = HttpClient.newHttpClient();

            for (int i = 1; i <= 5; i++) {
                try {
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(API_URL))
                            .header("Content-Type", "text/plain; charset=UTF-8")
                            .POST(HttpRequest.BodyPublishers.ofString(data, StandardCharsets.UTF_8))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    System.out.println(user + " - Request " + i + ":");
                    System.out.println("  Response Code: " + response.statusCode());
                    System.out.println("  Response Body: " + response.body());
                } catch (Exception e) {
                    System.err.println(user + " - Request " + i + " failed:");
                    e.printStackTrace();
                }
            }
        }
    }
}

