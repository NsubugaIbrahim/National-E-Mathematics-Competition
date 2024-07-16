import java.io.*;
import java.net.*;

public class a {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String fromServer;
            String fromUser;

            while ((fromServer = in.readLine()) != null) {
                System.out.println(fromServer);
                if (fromServer.equals("User registered successfully!") ||
                    fromServer.equals("Invalid username or password.") ||
                    fromServer.equals("Login successful!") ||
                    fromServer.equals("Available challenges: []") ||
                    fromServer.equals("Attempting challenge...") ||
                    fromServer.equals("Applicants: []") ||
                    fromServer.equals("Confirming applicants...")) {
                    break;
                }

                fromUser = console.readLine();
                if (fromUser != null) {
                    System.out.println(fromUser);
                    out.println(fromUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
