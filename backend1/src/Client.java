import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6666);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Display menu
        System.out.println("Welcome to the Competition!");
        System.out.println("Please select an option:");
        System.out.println("1. Register");
        System.out.println("2. ViewChallenges");
        System.out.println("3. AttemptChallenge");
        System.out.println("\nPlease enter your choice:\n");

        // User selects an option
        String userChoice = userInput.readLine();
        out.println(userChoice);

        // Process the choice
        if (userChoice.equals("Register")) {
            // Registration process
            System.out.println("Please enter your details in the format:");
            System.out.println("Register username firstname lastname emailAddress password date_of_birth school_registration_number \n ");

            String registrationDetails = userInput.readLine();
            out.println(registrationDetails);

            // Read server response
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
            }

        } else if (userChoice.equals("ViewChallenges")) {
            // View challenges
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
            }

        } else if (userChoice.equals("AttemptChallenge")) {
            // Attempt challenge
            System.out.println("Please enter two numbers separated by a space to add:");
            String numbers = userInput.readLine();
            out.println(numbers);

            // Read server response
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
            }

        } else {
            System.out.println("Invalid option selected.");
        }

        socket.close();
    }
}
