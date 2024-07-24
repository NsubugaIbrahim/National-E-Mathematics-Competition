import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.contains("Please enter your choice:") ||
                    serverResponse.contains("Question: ") ||
                    serverResponse.contains("Please enter your choice (confirm yes <username> or confirm no <username>):") ||
                    serverResponse.contains("Enter your username:") ||
                    serverResponse.contains("Enter your password:") ||
                    serverResponse.contains("Please enter your details in the format:") ||
                    serverResponse.contains("Enter the command to attempt a challenge: AttemptChallenge [challengeId]") ||
                    serverResponse.contains("Please enter two numbers separated by a space to add:")) {
                    String userChoice = userInput.readLine();
                    out.println(userChoice);
                }
            }
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
