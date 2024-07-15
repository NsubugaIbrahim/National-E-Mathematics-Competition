import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class d {
    private static final String REGISTER = "Register";
    private static final String VIEW_CHALLENGES = "ViewChallenges";
    private static final String ATTEMPT_CHALLENGE = "AttemptChallenge";
    private static final String FILE_PATH = "registration_details.txt";
    private static final String IMAGE_DIRECTORY = "images/";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            System.out.println("\n\nWelcome to the Competition!\n\n\n");

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Client connected...");
                    displayMenu(out);

                    // Read client choice
                    String clientChoice = in.readLine();
                    System.out.println("Client selected option: " + clientChoice);

                    switch (clientChoice) {
                        case REGISTER:
                            handleRegistration(in, out);
                            break;
                        case VIEW_CHALLENGES:
                            out.println("Challenges to be viewed here...");
                            break;
                        case ATTEMPT_CHALLENGE:
                            handleChallenge(in, out);
                            break;
                        default:
                            out.println("Invalid option selected.");
                    }
                    // Display menu again after processing choice
                    displayMenu(out);
                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void displayMenu(PrintWriter out) {
        out.println("Welcome to the Competition!");
        out.println("Please select an option:");
        out.println(REGISTER + ". Register");
        out.println(VIEW_CHALLENGES + ". ViewChallenges");
        out.println(ATTEMPT_CHALLENGE + ". AttemptChallenge");
        out.println("\nPlease enter your choice:");
    }

    private static void handleRegistration(BufferedReader in, PrintWriter out) throws IOException {
        StringBuilder registrationPrompt = new StringBuilder();
        registrationPrompt.append("Please enter your details in the format:\n");
        registrationPrompt.append("Register username firstname lastname emailAddress password date_of_birth school_reg_no\n");

        out.println(registrationPrompt.toString());

        String registrationDetails = in.readLine();
        System.out.println("Registration details received: " + registrationDetails);

        try {
            // Save registration details to file
            saveToFile(registrationDetails);

            // Parse registration details
            String[] details = registrationDetails.split(" ");
            String username = details[1];
            String firstname = details[2];
            String lastname = details[3];
            String email = details[4];
            String dob = details[6]; // Assuming date of birth is at index 6

            // Receive and save image
            byte[] imageData = receiveImage(in);
            String imagePath = saveImage(imageData);

            // Send confirmation email with registration details and image path
            sendEmail(email, username, firstname, lastname, dob, imagePath);
            
            out.println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Registration failed. Please try again.");
        }
    }

    private static byte[] receiveImage(BufferedReader in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

    private static String saveImage(byte[] imageData) throws IOException {
        String imagePath = IMAGE_DIRECTORY + "user_image.jpg"; // Modify file name and extension as needed
        File imageFile = new File(imagePath);
        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            fos.write(imageData);
        }
        return imagePath;
    }

    private static void saveToFile(String registrationDetails) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(registrationDetails);
            writer.newLine();
        }
    }

    private static void handleChallenge(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please enter two numbers separated by a space to add:");

        String numbers = in.readLine();
        String[] numArray = numbers.split(" ");
        if (numArray.length == 2) {
            try {
                int num1 = Integer.parseInt(numArray[0]);
                int num2 = Integer.parseInt(numArray[1]);
                int result = num1 + num2;
                out.println("The sum is: " + result);
            } catch (NumberFormatException e) {
                out.println("Invalid input. Please enter valid numbers.");
            }
        } else {
            out.println("Invalid input format. Please enter two numbers separated by a space.");
        }
    }

    private static void sendEmail(String recipientEmail, String username, String firstname, String lastname, String dob, String imagePath) {
        // Sender's email ID needs to be mentioned
        String from = "tgnsystemslimited@gmail.com";
        final String usernameEmail = "tgnsystemslimited@gmail.com"; // sender's email ID
        final String password = "mpdl ahwd lrkg xuqr"; // sender's password

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameEmail, password);
            }
        });

        try {
            // Create a default MimeMessage object
            Message message = new MimeMessage(session);

            // Set From: header field of the header
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));

            // Set Subject: header field
            message.setSubject("Registration Details Successfully Sent");

            // Now set the actual message
            String emailContent = "Dear " + firstname + " " + lastname + ",\n\n";
            emailContent += "Your registration details have successfully been received and are awaiting approval.\n";
            emailContent += "Username: " + username + "\n";
            emailContent += "Date of Birth: " + dob + "\n\n";
            emailContent += "Thank you for registering with us.";

            // Attach image to the email
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            Multipart multipart = new MimeMultipart();
            messageBodyPart.setText(emailContent);
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(imagePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(imagePath);
            multipart.addBodyPart(messageBodyPart);

            // Set the multi-part as email's content
            message.setContent(multipart);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
