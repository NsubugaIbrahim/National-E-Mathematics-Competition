import java.io.*;
import java.net.*;
import java.util.Base64;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
             Scanner scanner = new Scanner(System.in)) {

            // Display menu
            System.out.println("Please select your user menu");
            System.out.println("1. Participant menu");
            System.out.println("2. Representative menu");
            System.out.println("\nPlease enter your choice:\n");

            // User selects a menu
            String userMenu = userInput.readLine();
            out.println(userMenu);

            // Process the choice
            if (userMenu.equals("Participant menu")) {
                handleParticipantMenu(in, out, userInput);
            } else if (userMenu.equals("Representative menu")) {
                handleRepresentativeMenu(in, out, scanner, userInput);
            } else {
                System.out.println("Invalid menu selected.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleParticipantMenu(BufferedReader in, PrintWriter out, BufferedReader userInput) throws IOException {
        // Display Participant menu
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        System.out.println(in.readLine());

        // Write Participant Choice
        String participantChoice = userInput.readLine();
        out.println(participantChoice);

        if (participantChoice.equals("Register")) {
            System.out.println(in.readLine()); // Enter registration details...

            String registrationDetails = userInput.readLine();
            out.println(registrationDetails);

            System.out.println("Enter the image file path:");
            String imagePath = userInput.readLine();
            File imageFile = new File(imagePath);
            byte[] imageBytes = new byte[(int) imageFile.length()];
            try (FileInputStream fis = new FileInputStream(imageFile)) {
                fis.read(imageBytes);
            }
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
            out.println(encodedImage);

            String response = in.readLine(); // Registration success or failure message
            System.out.println(response);
        }
    }

    private static void handleRepresentativeMenu(BufferedReader in, PrintWriter out, Scanner scanner, BufferedReader userInput) throws IOException {
        // Display Representative menu
        System.out.println(in.readLine());
        System.out.println(in.readLine());
        System.out.println(in.readLine());

        // Write Representative Choice
        String representativeChoice = userInput.readLine();
        out.println(representativeChoice);

        if (representativeChoice.equals("viewApplicants")) {
            System.out.println(in.readLine());

            // Representative login
            System.out.println("Representative Login:");
            System.out.print("Enter your name: ");
            String repName = scanner.nextLine();
            System.out.print("Enter your email: ");
            String repEmail = scanner.nextLine();

            out.println(repName);
            out.println(repEmail);

            String response = in.readLine();
            System.out.println(response); // Login successful or failed message

            if (response.equals("Login successful")) {
                // Handle representative menu options
                System.out.println("Enter your school registration number:");
                String schoolRegNo = scanner.nextLine();
                out.println(schoolRegNo);

                // Read and display the list of applicants from the server
                String applicants;
                while (true) {
                    String applicantDetails = in.readLine();
                    if (applicantDetails.equals("END")) {
                        break;
                    }
                    System.out.println(applicantDetails);
                    System.out.println(in.readLine()); // Confirm acceptance
                    String decision = userInput.readLine();
                    out.println(decision);
                }
            } else {
                System.out.println("Login failed. Invalid name or email!");
            }

        } else if (representativeChoice.equals("Exit")) {
            System.out.println("Exiting...");
        } else {
            System.out.println("Invalid option selected.");
        }
    }
}
