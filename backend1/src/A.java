import java.io.*;
import java.net.*;

public class A {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 6666);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {

            String serverResponse;

            // Display initial menu
            displayInitialMenu();
            String userCategory = userInput.readLine();
            out.println(userCategory);

            if ("participant".equalsIgnoreCase(userCategory)) {
                handleParticipantSession(in, out, userInput);
            } else if ("schoolrepresentative".equalsIgnoreCase(userCategory)) {
                handleRepresentativeSession(in, out, userInput);
            } else {
                System.out.println("Invalid user category selected.");
            }

        } catch (IOException e) {
            System.out.println("Error connecting to the server: " + e.getMessage());
        }
    }

    private static void handleParticipantSession(BufferedReader in, PrintWriter out, BufferedReader userInput) throws IOException {
        while (true) {
            displayParticipantMenu();
            String clientChoice = userInput.readLine();
            out.println(clientChoice);

            if ("Exit".equalsIgnoreCase(clientChoice)) {
                break; // Exit the session
            }

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.contains("Enter your username:") ||
                    serverResponse.contains("Enter your password:") ||
                    serverResponse.contains("Please enter your details in the format:") ||
                    serverResponse.contains("Enter the command to attempt a challenge: AttemptChallenge [challengeId]")) {
                    String userChoice = userInput.readLine();
                    out.println(userChoice);
                }
                if (serverResponse.contains("Login successful!") || serverResponse.contains("Invalid option selected.")) {
                    break;
                }
            }
        }
    }

    private static void handleRepresentativeSession(BufferedReader in, PrintWriter out, BufferedReader userInput) throws IOException {
        while (true) {
            displayRepresentativeMenu();
            String clientChoice = userInput.readLine();
            out.println(clientChoice);

            if ("Exit".equalsIgnoreCase(clientChoice)) {
                break; // Exit the session
            }

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.contains("Enter your username:") ||
                    serverResponse.contains("Enter your password:") ||
                    serverResponse.contains("Please enter your choice (confirm yes <username> or confirm no <username>):")) {
                    String userChoice = userInput.readLine();
                    out.println(userChoice);
                }
                if (serverResponse.contains("Login successful!") || serverResponse.contains("Invalid option selected.")) {
                    break;
                }
            }
        }
    }

    private static void displayInitialMenu() {
        System.out.println("\nWhich user category are you?\n");
        System.out.println("participant");
        System.out.println("schoolrepresentative\n");
        System.out.println("Please enter your choice:");
    }

    private static void displayParticipantMenu() {
        System.out.println("Participant Menu:\n");
        System.out.println("Register");
        System.out.println("Login");
        System.out.println("Exit");
        System.out.println("Please enter your choice:");
    }

    private static void displayParticipantLoggedInMenu() {
        System.out.println("Participant Menu (Logged In):\n");
        System.out.println("ViewChallenges");
        System.out.println("AttemptChallenge");
        System.out.println("Exit");
        System.out.println("Please enter your choice:");
    }

    private static void displayRepresentativeMenu() {
        System.out.println("School Representative's Menu:\n");
        System.out.println("Login");
        System.out.println("Exit");
        System.out.println("Please enter your choice:");
    }

    private static void displayRepLoggedInMenu() {
        System.out.println("School Representative Menu (Logged In):\n");
        System.out.println("ViewApplicants");
        System.out.println("ConfirmApplicant");
        System.out.println("Exit");
        System.out.println("Please enter your choice:");
    }
}
