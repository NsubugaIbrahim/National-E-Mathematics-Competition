import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import java.io.File;

import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;
import java.util.Properties;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class y {
    private static final String REGISTER = "Register";
    private static final String VIEW_CHALLENGES = "ViewChallenges";
    private static final String ATTEMPT_CHALLENGE = "AttemptChallenge";
    private static final String VIEW_APPLICANTS = "ViewApplicants"; // New command
    private static final String FILE_PATH = "registration_details.pdf"; // PDF file path
    private static final String CONFIRM_APPLICANT = "ConfirmApplicant";

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
                        case VIEW_APPLICANTS:
                            handleViewApplicants(out);
                            break;
                        case CONFIRM_APPLICANT:
                            handleConfirmApplicant(new BufferedReader(new InputStreamReader(System.in)), out); // Use System.in for user input
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
        out.println(VIEW_APPLICANTS + ". ViewApplicants");
        out.println(CONFIRM_APPLICANT + ". ConfirmApplicant"); // New menu option
        out.println("\nPlease enter your choice:");
    }

    private static void handleRegistration(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please enter your details in the format: username firstname lastname emailAddress password date_of_birth school_reg_no image_path");

        String registrationDetails = in.readLine();
        System.out.println("Registration details received: " + registrationDetails);

        try {
            // Parse registration details
            String[] details = registrationDetails.split(" ");
            String username = details[1];
            String firstname = details[2];
            String lastname = details[3];
            String email = details[4];
            String password = details[5];
            String dob = details[6];
            String schoolRegNo = details[7];
            String imagePath = details[8];

            // Create PDF document
            //PDDocument document = PDDocument.load(new File(FILE_PATH));
            File file = new File(FILE_PATH);
            PDDocument document = Loader.loadPDF(file);

            PDPage page = new PDPage();
            document.addPage(page);

            

            // Write registration details to PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
           // contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            //contentStream.setFont(PDType1Font.HELVETICA, 12);
            //contentStream.setFont(PDType1Font.HELVETICA_BOLD, 0);
            

            PDType0Font helveticaBold = PDType0Font.load(document, new File("C:/xampp/htdocs/National-E-Mathematics-Competition/fonts/OpenSans-Italic-VariableFont_wdth,wght.ttf"));
            contentStream.setFont(helveticaBold, 12);
            contentStream.beginText();
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(50, 750);

            contentStream.showText("Username: " + username);
            contentStream.newLine();
            contentStream.showText("First Name: " + firstname);
            contentStream.newLine();
            contentStream.showText("Last Name: " + lastname);
            contentStream.newLine();
            contentStream.showText("Email: " + email);
            contentStream.newLine();
            contentStream.showText("Password: " + password);
            contentStream.newLine();
            contentStream.showText("Date of Birth: " + dob);
            contentStream.newLine();
            contentStream.showText("School Registration Number: " + schoolRegNo);
            contentStream.newLine();

            // Add image to PDF
            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);
            contentStream.drawImage(pdImage, 100, 400, pdImage.getWidth() / 2, pdImage.getHeight() / 2);

            contentStream.endText();
            contentStream.close();

            // Save PDF
            document.save(FILE_PATH);
            document.close();

            System.out.println("Registration details saved to " + FILE_PATH);

            // Send email confirmation
            sendEmail(email, username, firstname, lastname, dob);

            out.println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Registration failed. Please try again.");
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

    private static void handleViewApplicants(PrintWriter out) {
        out.println("Viewing applicants is not supported in the PDF version.");
    }

    private static void sendEmail(String recipientEmail, String username, String firstname, String lastname, String dob) {
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
            message.setSubject("Registration Details successfully Sent ");

            // Now set the actual message
            String emailContent = "Dear " + firstname + " " + lastname + ",\n\n";
            emailContent += "Your registration details have been successfully received and are under review.\n";
            emailContent += "Username: " + username + "\n";
            emailContent += "firstname: " + firstname + "\n ";
            emailContent += "lastname: " + lastname + "\n" ;
            emailContent += "Date of Birth: " + dob + "\n";
            emailContent += "Please keep this email for future reference.\n\n";
            emailContent += "You will be receiving an email once your registration is approved.\n\n";
            emailContent += "Thank you for registering with for the natinal E mathemactics Competition.";

            message.setText(emailContent);

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleConfirmApplicant(BufferedReader in, PrintWriter out) throws IOException {
        out.println("Please confirm applicant by typing: confirm yes [username]");
        out.flush(); // Ensure prompt is displayed immediately

        String confirmationRequest = in.readLine(); // Wait for user input

        // Parse the command
        String[] commandParts = confirmationRequest.split(" ");
        if (commandParts.length == 3 && commandParts[0].equals("confirm") && commandParts[1].equals("yes")) {
            String usernameToConfirm = commandParts[2];

            // Read from PDF file and find the applicant
            File file = new File(FILE_PATH);
            try (PDDocument document = Loader.loadPDF(file)) {
                boolean found = false;
                for (PDPage page : document.getPages()) {
                    // Extract text content from the page
                    String pageText = new PDFTextStripper().getText(document);
                    if (pageText.contains("Username: " + usernameToConfirm)) {
                        found = true;
                        saveToDatabase(extractApplicantDetails(pageText));
                        out.println("Applicant " + usernameToConfirm + " confirmed and details saved to database.");
                        break;
                    }
                }
                if (!found) {
                    out.println("Applicant not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                out.println("An error occurred while reading the PDF.");
            }
        } else {
            out.println("Invalid confirmation command. Please use format: confirm yes [username]");
        }
    }

    private static JSONObject extractApplicantDetails(String pageText) {
        JSONObject applicant = new JSONObject();
        String[] lines = pageText.split("\n");
        for (String line : lines) {
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                switch (parts[0]) {
                    case "Username":
                        applicant.put("username", parts[1]);
                        break;
                    case "First Name":
                        applicant.put("firstname", parts[1]);
                        break;
                    case "Last Name":
                        applicant.put("lastname", parts[1]);
                        break;
                    case "Email":
                        applicant.put("email", parts[1]);
                        break;
                    case "Password":
                        applicant.put("password", parts[1]);
                        break;
                    case "Date of Birth":
                        applicant.put("date_of_birth", parts[1]);
                        break;
                    case "School Registration Number":
                        applicant.put("school_reg_no", parts[1]);
                        break;
                }
            }
        }
        return applicant;
    }

    private static void saveToDatabase(JSONObject applicant) {
        String url = "jdbc:mysql://localhost:3306/mathchallenge"; // Replace with your database URL
        String username = "root"; // Replace with your database username
        String password = ""; // Replace with your database password

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Connect to the database
            conn = DriverManager.getConnection(url, username, password);

            // SQL query to insert applicant details into 'participants' table
            String sql = "INSERT INTO participants (username, firstname, lastname, email, password, date_of_birth, school_reg_no, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Prepare the statement with the SQL query
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, applicant.getString("username"));
            stmt.setString(2, applicant.getString("firstname"));
            stmt.setString(3, applicant.getString("lastname"));
            stmt.setString(4, applicant.getString("email"));
            stmt.setString(5, applicant.getString("password"));
            stmt.setString(6, applicant.getString("date_of_birth"));
            stmt.setString(7, applicant.getString("school_reg_no"));
            stmt.setString(8, applicant.getString("image")); // Assuming image is stored as a Base64 string

            // Execute the insert statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new participant was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in a finally block to ensure they're always closed
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
