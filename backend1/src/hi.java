

import java.io.*;
import java.net.*;

public class hi{
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 6667);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        // Display menu
        System.out.println("\n\nWelcome to the National Mathematics Competition!\n\n");
        System.out.println("Select which type of user you are:");
        System.out.println("-participant");
        System.out.println("-schoolRepresentative");
        System.out.println("\nPlease enter your choice:\n");

        // User selects an option
        String userChoice = userInput.readLine();
        out.println(userChoice);

        // Process the choice
        if (userChoice.equals("participant")) {
            // Registration process

            System.out.println("\n-Register username firstname lastname emailAddress password date_of_birth school_registration_number ");
            System.out.println("-viewChallenges:");
            System.out.println("-login:\n\n\n");


            String registrationDetails = userInput.readLine();
            out.println(registrationDetails);

            // Read server response
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
            }

        } else if (userChoice.equals("schoolRepresentative")) {
            System.out.println("viewApplicants:");
            System.out.println("confirm yes/no username:");
            
            // View challenges
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println("Server: " + serverResponse);
            }

        }  else {
            System.out.println("Invalid option selected.");
        }

        socket.close();
    }
}
