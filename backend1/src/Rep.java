import java.io.*;
import java.net.*;

public class Rep {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Display menu
        System.out.println("Welcome Representative!");
        System.out.println("Please select an option:");
        System.out.println("1. Login");
        System.out.println("\nPlease enter your choice:\n");

        // User selects an option
        String userChoice = userInput.readLine();
        out.println(userChoice);

        // Process the choice
        if (userChoice.equals("Login")) {
            out.println("Login");
            // Login process
            System.out.println("Please enter your username:");
            String username = userInput.readLine();
            out.println(username);

            System.out.println("Please enter your password:");
            String password = userInput.readLine();
            out.println(password);

            // Read server response
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
                if (serverResponse.equals("Login successful!") || serverResponse.equals("Login failed. Please try again.")) {
                    break;
                }
            }

        } else {
            System.out.println("Invalid option selected.");
        }

        socket.close();
    }
}
