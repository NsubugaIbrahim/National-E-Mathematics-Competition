//package backend;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;

public class client {
    private static final String HOST = "localhost";
    private static final int PORT = 4999;
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final ObjectInputStream objIn;
    private final Scanner scanner;

    public client() throws IOException {
        this.socket = new Socket(HOST, PORT);
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.objIn = new ObjectInputStream(socket.getInputStream());
        this.scanner = new Scanner(System.in);
    }

    public void registerParticipants() {
        while (true) {
            System.out.println("Enter participant details in the following format:");
            System.out.println("participantId username firstname lastname emailAddress dateOfBirth schoolRegistrationNumber imageFile");
            System.out.println("(or type 'exit' to finish)");

            String inputLine = scanner.nextLine();
            if (inputLine.equalsIgnoreCase("exit")) break;

            String[] details = inputLine.split(" ");
            if (details.length != 8) {
                System.out.println("Invalid input format. Please enter exactly 8 values separated by spaces.");
                continue;
            }

            String message = "REGISTER " + inputLine;
            out.println(message);
            try {
                String response = in.readLine();
                System.out.println(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void viewChallenges() {
        System.out.println("Enter your username to view available challenges:");
        String username = scanner.nextLine();
        String message = "VIEWCHALLENGES " + username;
        out.println(message);

        try {
            Object response = objIn.readObject();
            if (response instanceof List<?>) { // I'm using List<?> to avoid raw type since it brought an error
                @SuppressWarnings("unchecked")
                List<Challenge> challenges = (List<Challenge>) response;
                if (challenges.isEmpty()) {
                    System.out.println("No active challenges available.");
                } else {
                    for (Challenge challenge : challenges) {
                        System.out.println("Challenge ID: " + challenge.getChallengeId());
                        System.out.println("Duration: " + challenge.getDuration() + " minutes");
                        System.out.println("Start Date: " + challenge.getStartDate());
                        System.out.println("End Date: " + challenge.getEndDate());
                        System.out.println("Number of Questions: " + challenge.getNumberOfQuestions());
                        System.out.println("-----------------------------");
                    }
                }
            }else if (response instanceof String) {
                String prompt = (String) response;
                System.out.println(prompt); // Display registration prompt
            } else {
                System.out.println("Unexpected response type.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void attemptChallenge() {
        System.out.println("Enter the challenge ID:");
        String challengeId = scanner.nextLine();
        System.out.println("Enter your username to start the challenge:");
        String username = scanner.nextLine();
        String message = "START " + username;
        out.println(message);

        try {
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println(response);
                if (response.equals("Challenge completed!") || response.equals("Time is up!") || response.equals("Participant not found, please register first.")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            client client = new client();
            Scanner scan = new Scanner(System.in);

            while (true) {
                System.out.println("> Choose an option:");
                System.out.println("> Register to register participant details");
                System.out.println("> ViewChallenges to start the challenge");
                System.out.println("> AttemptChallenge to start the challenge");
                System.out.println("> Exit to exit");
                String choice = scan.nextLine();

                switch (choice) {
                    case "Register":
                        client.registerParticipants();
                        break;
                    case "ViewChallenges":
                        client.viewChallenges();
                        break;
                    case "AttemptChallenge":
                        client.attemptChallenge();
                        break;
                    case "Exit":
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
