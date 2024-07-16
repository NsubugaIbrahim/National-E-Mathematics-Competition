import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.PasswordAuthentication;

public class sava {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             OutputStream output = socket.getOutputStream();
             PrintWriter writer = new PrintWriter(output, true)) {

            String email = reader.readLine();
            String password = reader.readLine();

            // Store user data in the database
            storeUserData(email, password);

            // Send confirmation email
            sendConfirmationEmail(email);

            writer.println("Registration successful! A confirmation email has been sent to " + email);

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void storeUserData(String email, String password) {
        String jdbcURL = "jdbc:mysql://localhost:3306/e-challenge";
        String dbUser = "root";
        String dbPassword = "";

        try {
            // Ensure MySQL driver is loaded
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
                String sql = "INSERT INTO reg_user (emailAddress, password) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, password);
                statement.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Database error: " + ex.getMessage());
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL driver not found: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendConfirmationEmail(String email) {
        final String from = "tgnsystemslimited@gmail.com";
        final String host = "smtp.gmail.com";
        final String username = "tgnsystemslimited@gmail.com";
        final String emailPassword = "aiej sfej ymlm kxsk";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, emailPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Registration Confirmation");
            message.setText("Thank you for registering!");

            Transport.send(message);
            System.out.println("Confirmation email sent to " + email);
        } catch (MessagingException e) {
            System.out.println("Email error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
