import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class email {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/e-challenge";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            String username = "krahim";
            String firstname = "Kayiwa";
            String lastname = "Rahim";
            String emailAddress = "kayiwarahim@gmail.com";
            String password = "Admin@1234";
            String dateOfBirth = "2003-03-26";
            String schoolRegNo = "U23811";

            if (registerUser(username, firstname, lastname, emailAddress, password, dateOfBirth, schoolRegNo)) {
                sendEmail(emailAddress, "Registration Successful", "Dear " + firstname + ", Your registration to the national mathematics competition was successful! \n username: " + username + "\n password: " + password + "\n date of birth: " + dateOfBirth + "\n school registration number: " + schoolRegNo);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean registerUser(String username, String firstname, String lastname, String emailAddress, String password, String dateOfBirth, String schoolRegNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establishing a connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to insert data
            String sql = "INSERT INTO reg_users (username, firstname, lastname, emailAddress, password, date_of_birth, school_reg_no) VALUES (?, ?, ?, ?, ?, ?, ?)";

            // Creating a PreparedStatement object
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, emailAddress);
            pstmt.setString(5, password);
            pstmt.setString(6, dateOfBirth);
            pstmt.setString(7, schoolRegNo);

            // Executing the statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }

    public static void sendEmail(String to, String subject, String body) {
        final String username = "tgnsystemslimited@gmail.com"; // your email
        final String password = "anls iqfv zxwt irfq"; // your email password

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    
}


