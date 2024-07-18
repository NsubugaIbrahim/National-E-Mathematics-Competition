import java.io.*;
import java.net.*;

public class client1 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);

                // Check if server sends a question
                if (serverResponse.startsWith("Question: ")) {
                    System.out.print("Your answer: "); // Prompt user for an answer
                    String userAnswer = userInput.readLine(); // Read user's answer
                    out.println(userAnswer); // Send user's answer to server
                } else if (serverResponse.startsWith("You scored: ")) {
                    System.out.println(serverResponse); // Display final score
                    break; // Exit loop after displaying final score
                } else {
                    // Handle other server responses here if needed
                }
            }
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
