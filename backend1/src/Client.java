import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 6666;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);

                if (serverResponse.contains("Please select an option:")) {
                    handleMenuSelection(userInput, out, in);
                } else if (serverResponse.contains("Please enter your choice:") ||
                    serverResponse.contains("Question: ") ||
                    serverResponse.contains("Please enter your choice (confirm yes <username> or confirm no <username>):") ||
                    serverResponse.contains("Enter your username:") ||
                    serverResponse.contains("Enter your password:") ||
                    serverResponse.contains("Please enter your details in the format:") ||
                    serverResponse.contains("Enter the command to attempt a challenge: AttemptChallenge [challengeId]")) {
                    String userChoice = userInput.readLine();
                    out.println(userChoice);
                } else if (serverResponse.contains("Challenges:")) {
                    displayChallenges(in);
                } else if (serverResponse.contains("Applicants:")) {
                    displayApplicants(in);
                }
            }
        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }

    private static void handleMenuSelection(BufferedReader userInput, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("1. View Challenges");
        System.out.println("2. Register Participant");
        System.out.println("3. Confirm Applicant");
        System.out.println("4. View Applicants");
        System.out.println("5. Attempt Challenge");
        System.out.println("6. Exit");

        String choice = userInput.readLine();
        out.println(choice);

        switch (choice) {
            case "1":
                // Fetch challenges
                out.println("ViewChallenges");
                break;
            case "2":
                handleRegistration(userInput, out);
                break;
            case "3":
                handleConfirmApplicant(userInput, out);
                break;
            case "4":
                // Fetch applicants
                out.println("ViewApplicants");
                break;
            case "5":
                handleAttemptChallenge(userInput, out);
                break;
            case "6":
                out.println("exit");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void handleRegistration(BufferedReader userInput, PrintWriter out) throws IOException {
        System.out.println("Please enter your details in the format: username,firstname,lastname,email,password,dob,school_reg_no,image_path");
        String details = userInput.readLine();
        out.println(details);
    }

    private static void handleConfirmApplicant(BufferedReader userInput, PrintWriter out) throws IOException {
        System.out.println("Please enter your choice (confirm yes <username> or confirm no <username>):");
        String choice = userInput.readLine();
        out.println(choice);
    }

    private static void handleAttemptChallenge(BufferedReader userInput, PrintWriter out) throws IOException {
        System.out.println("Enter the command to attempt a challenge: AttemptChallenge [challengeId]");
        String command = userInput.readLine();
        out.println(command);
    }

    private static void displayChallenges(BufferedReader in) throws IOException {
        System.out.println("Fetching challenges...");
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            System.out.println(line);
        }
    }

    private static void displayApplicants(BufferedReader in) throws IOException {
        System.out.println("Fetching applicants...");
        String line;
        while ((line = in.readLine()) != null && !line.isEmpty()) {
            System.out.println(line);
        }
    }
}
