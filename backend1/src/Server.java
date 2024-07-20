import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.Scanner;
import java.io.*;
import java.nio.file.*;

import javax.mail.*;
import javax.mail.internet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;



public class server {
    private static final String REGISTER = "Register";
    private static final String VIEW_CHALLENGES = "ViewChallenges";
    private static final String ATTEMPT_CHALLENGE = "AttemptChallenge";
    private static final String VIEW_APPLICANTS = "ViewApplicants"; // New command
    private static final String IMAGE_FOLDER = "saved_images/";
    private static final String CONFIRM_APPLICANT = "ConfirmApplicant";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mathchallenge";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) throws SQLException {
        try (ServerSocket serverSocket = new ServerSocket(6666);
        Connection connection = DriverManager.getConnection(DB_URL, USER, PASS) ) {
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
                            handleConfirmApplicant(out, in, connection);
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
            String date_of_birth = details[6];
            String school_reg_no = details[7];
            String image_path = details[8].replace("\"", ""); // Remove quotes from the file path
    
            // Ensure the folder exists
            File folder = new File(IMAGE_FOLDER);
            if (!folder.exists()) {
                folder.mkdirs();
            }
    
            // Define the new image path
            Path sourcePath = Paths.get(image_path);
            Path destinationPath = Paths.get(IMAGE_FOLDER + sourcePath.getFileName());
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    
            // Create a string with registration details
            String registrationEntry = String.join(",",
                username,
                firstname,
                lastname,
                email,
                password,
                date_of_birth,
                school_reg_no,
                destinationPath.toString()
            );
    
            // Write registration details to text file
            try (FileWriter fileWriter = new FileWriter("registration_details.txt", true);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                 PrintWriter printWriter = new PrintWriter(bufferedWriter)) {
                printWriter.println(registrationEntry);
                System.out.println("Registration details saved to registration_details.txt");
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Registration failed. Please try again.");
                return;
            }
    
            // Send email confirmation
            sendEmail(email, username, firstname, lastname, date_of_birth);
    
            out.println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Registration failed. Please try again.");
        }
    }
    

    private static void handleChallenge(BufferedReader in, PrintWriter out) throws IOException {
        List<Questions> challengeQuestions = getRandomQuestions(10);
        Map<Integer, String> correctAnswers = getCorrectAnswers(challengeQuestions);
        Map<Questions, String> userAnswers = new HashMap<>();
        int totalMarks = 0;
    
        for (Questions question : challengeQuestions) {
            // Send question text to client
            out.println("Question: " + question.getQuestionText());
            out.flush(); // Ensure the question is sent immediately
    
            // Receive answer from client
            String answer = in.readLine().trim();
            userAnswers.put(question, answer);
    
            // Check answer correctness
            if (correctAnswers.containsKey(question.getQuestionId()) &&
                correctAnswers.get(question.getQuestionId()).equalsIgnoreCase(answer)) {
                totalMarks++;
                out.println("Correct!");
            } else {
                out.println("Wrong! Correct answer: " + correctAnswers.get(question.getQuestionId()));
            }
            out.flush(); // Ensure response is sent immediately
        }
    
        out.println("You scored: " + totalMarks + " out of " + challengeQuestions.size());
        out.flush(); // Ensure the final score is sent immediately
    
        // Generate PDF report
        generatePDFReport(userAnswers, correctAnswers, totalMarks, challengeQuestions.size());
    }
    

    public static List<Questions> getRandomQuestions(int numberOfQuestions) {
        List<Questions> questions = new ArrayList<>();
        String query = "SELECT questionid, questionText FROM questions ORDER BY RAND() LIMIT ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, numberOfQuestions);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int questionId = rs.getInt("questionid");
                String questionText = rs.getString("questionText");
                questions.add(new Questions(questionId, questionText));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public static Map<Integer, String> getCorrectAnswers(List<Questions> questions) {
        Map<Integer, String> answers = new HashMap<>();
        String query = "SELECT questionId, answer FROM answers WHERE questionId IN (";
        StringBuilder queryBuilder = new StringBuilder(query);

        for (int i = 0; i < questions.size(); i++) {
            queryBuilder.append("?");
            if (i < questions.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        queryBuilder.append(")");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString())) {

            for (int i = 0; i < questions.size(); i++) {
                stmt.setInt(i + 1, questions.get(i).getQuestionId());
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int questionId = rs.getInt("questionId");
                String correctAnswer = rs.getString("answer");
                answers.put(questionId, correctAnswer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

    public static void generatePDFReport(Map<Questions, String> userAnswers, Map<Integer, String> correctAnswers, int totalMarks, int totalQuestions) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        try {
            PDType0Font customFont = PDType0Font.load(document, new File("C:/xampp/htdocs/National-E-Mathematics-Competition/fonts/OpenSans-Italic-VariableFont_wdth,wght.ttf"));
            System.out.println("Font loaded successfully.");

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(customFont, 16);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 725);
                contentStream.showText("Math Challenge Marking Guide");
                contentStream.newLine();
                contentStream.setFont(customFont, 12);
                contentStream.newLine();

                for (Map.Entry<Questions, String> entry : userAnswers.entrySet()) {
                    Questions question = entry.getKey();
                    String userAnswer = entry.getValue();
                    String correctAnswer = correctAnswers.get(question.getQuestionId());

                    contentStream.showText("Question: " + question.getQuestionText());
                    contentStream.newLine();
                    contentStream.showText("Your Answer: " + userAnswer);
                    contentStream.newLine();
                    contentStream.showText("Correct Answer: " + correctAnswer);
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.showText("Total Marks: " + totalMarks + " out of " + totalQuestions);
                contentStream.endText();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error writing to PDF: " + e.getMessage());
            }

            document.save("Marking_Guide.pdf");
            document.close();
            System.out.println("PDF generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading font or saving PDF: " + e.getMessage());
        }
    }

    private static void handleViewApplicants(PrintWriter out) {
        try {
            File file = new File("registration_details.txt");
    
            if (!file.exists()) {
                out.println("No applicants registered yet.");
                out.flush(); // Ensure the message is sent immediately
                return;
            }
    
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    out.println(line);
                }
                out.flush(); // Ensure all lines are sent immediately
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Failed to read applicants. Please try again.");
                out.flush(); // Ensure the error message is sent immediately
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("An error occurred while retrieving applicants.");
            out.flush(); // Ensure the error message is sent immediately
        }
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

    private static void handleConfirmApplicant(PrintWriter out, BufferedReader in, Connection connection) {
        try {
            out.println("\nPlease enter your choice (confirm yes <username> or confirm no <username>):");
            out.flush(); // Ensure the prompt is sent immediately
    
            // Read the user input
            String command = in.readLine();
            if (command == null || command.isEmpty()) {
                out.println("Invalid input. Please try again.");
                out.flush();
                return;
            }
    
            // Split the command to extract action and username
            String[] parts = command.split(" ");
            if (parts.length != 3 || (!parts[1].equals("yes") && !parts[1].equals("no"))) {
                out.println("Invalid command format. Use: confirm yes <username> or confirm no <username>");
                out.flush();
                return;
            }
    
            String action = parts[1];
            String username = parts[2];
    
            File inputFile = new File("registration_details.txt");
            File tempFile = new File("registration_details_temp.txt");
    
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
    
                String currentLine;
                boolean userFound = false;
                String applicantDetails = null;
    
                // Read each line from the input file
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(username)) {
                        applicantDetails = currentLine;
                        userFound = true;
                        // Skip writing the found line to the temp file (effectively removing it)
                        continue;
                    }
                    // Write all lines except the found applicant's details to the temp file
                    writer.write(currentLine + System.lineSeparator());
                }
    
                if (!userFound) {
                    out.println("Applicant not found.");
                    out.flush();
                    return;
                }
    
                // Insert the applicant details into the database
                if (applicantDetails != null) {
                    if (action.equals("yes")) {
                        insertIntoDatabase(connection, "participants", applicantDetails);
                        out.println("Applicant confirmed and added to participants.");
                    } else if (action.equals("no")) {
                        insertIntoDatabase(connection, "rejected", applicantDetails);
                        out.println("Applicant rejected and added to rejected.");
                    }
                    out.flush();
                }
    
                // Replace the original file with the updated temp file
                if (!inputFile.delete()) {
                    out.println("Failed to delete the original file.");
                    out.flush();
                    return;
                }
                if (!tempFile.renameTo(inputFile)) {
                    out.println("Failed to rename the temp file.");
                    out.flush();
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Failed to process the applicant. Please try again.");
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("An error occurred while confirming the applicant.");
            out.flush();
        }
    }
    
    
    private static void insertIntoDatabase(Connection connection, String tableName, String applicantDetails) {
        String[] details = applicantDetails.split(",");
        
        // Debugging statement to print the length and content of details
        System.out.println("Number of details: " + details.length);
        for (int i = 0; i < details.length; i++) {
            System.out.println("Detail " + i + ": " + details[i]);
        }
        
        // Ensure that there are exactly 8 details (excluding the username)
        if (details.length < 8) {
            System.out.println("Insufficient details provided.");
            return;
        }
    
        String query = "INSERT INTO " + tableName + " (username, firstname, lastname, email, password, date_of_birth, school_reg_no, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, details[0]); // Adjust index based on your format
            pstmt.setString(2, details[1]);
            pstmt.setString(3, details[2]);
            pstmt.setString(4, details[3]);
            pstmt.setString(5, details[4]);
            pstmt.setString(6, details[5]);
            pstmt.setString(7, details[6]);
            pstmt.setString(8, details[7]);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
}

