import java.io.*;
import java.net.*;
import java.util.*;

public class b {
    private static final int PORT = 5000;
    private static Map<String, String> users = new HashMap<>();
    private static Set<String> challenges = new HashSet<>();
    private static Set<String> applicants = new HashSet<>();
    
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;
        
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }
        
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                
                String userType = getUserType();
                if (userType.equalsIgnoreCase("participant")) {
                    handleParticipant();
                } else if (userType.equalsIgnoreCase("schoolrepresentative")) {
                    handleSchoolRepresentative();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        private String getUserType() throws IOException {
            out.println("Choose user type: participant or schoolrepresentative");
            return in.readLine();
        }
        
        private void handleParticipant() throws IOException {
            out.println("Choose command: register or login");
            String command = in.readLine();
            if (command.equalsIgnoreCase("register")) {
                registerUser();
            } else if (command.equalsIgnoreCase("login")) {
                if (loginUser()) {
                    handleParticipantMenu();
                }
            }
        }
        
        private void handleSchoolRepresentative() throws IOException {
            out.println("Choose command: login");
            if (loginUser()) {
                handleSchoolRepresentativeMenu();
            }
        }
        
        private void handleParticipantMenu() throws IOException {
            out.println("Choose command: viewChallenge or attemptChallenge");
            String command = in.readLine();
            if (command.equalsIgnoreCase("viewChallenge")) {
                out.println("Available challenges: " + challenges);
            } else if (command.equalsIgnoreCase("attemptChallenge")) {
                out.println("Attempting challenge...");
            }
        }
        
        private void handleSchoolRepresentativeMenu() throws IOException {
            out.println("Choose command: viewApplicants or confirmApplicants");
            String command = in.readLine();
            if (command.equalsIgnoreCase("viewApplicants")) {
                out.println("Applicants: " + applicants);
            } else if (command.equalsIgnoreCase("confirmApplicants")) {
                out.println("Confirming applicants...");
            }
        }
        
        private void registerUser() throws IOException {
            out.println("Enter username:");
            String username = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();
            users.put(username, password);
            out.println("User registered successfully!");
        }
        
        private boolean loginUser() throws IOException {
            out.println("Enter username:");
            String username = in.readLine();
            out.println("Enter password:");
            String password = in.readLine();
            if (users.containsKey(username) && users.get(username).equals(password)) {
                out.println("Login successful!");
                return true;
            } else {
                out.println("Invalid username or password.");
                return false;
            }
        }
    }
}
