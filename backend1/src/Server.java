import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
import java.io.*;
import java.net.*;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.Properties;

public class Server {

    private static void handleAttemptChallenge(String command, PrintWriter out, BufferedReader in, Connection connection) {
        String[] commandParts = command.split(" ");
        if (commandParts.length != 2) {
            out.println("Invalid command format. Use: AttemptChallenge [challengeId]");
            return;
        }

        int challengeId;
        try {
            challengeId = Integer.parseInt(commandParts[1]);
        } catch (NumberFormatException e) {
            out.println("Invalid challenge ID format. Please enter a valid number.");
            return;
        }

        try {
            // Check if the challenge exists and is currently active
            String checkChallengeQuery = "SELECT * FROM challenges WHERE challengeId = ? AND CURDATE() BETWEEN startDate AND endDate";
            try (PreparedStatement pstmt = connection.prepareStatement(checkChallengeQuery)) {
                pstmt.setInt(1, challengeId);
                ResultSet rs = pstmt.executeQuery();

                if (!rs.next()) {
                    out.println("Challenge not found or not active.");
                    return;
                }

                // Fetch 10 random questions
                String fetchQuestionsQuery = "SELECT * FROM questions WHERE challengeId = ? ORDER BY RAND() LIMIT 10";
                try (PreparedStatement questionsPstmt = connection.prepareStatement(fetchQuestionsQuery)) {
                    questionsPstmt.setInt(1, challengeId);
                    ResultSet questionsRs = questionsPstmt.executeQuery();

                    List<Question> questions = new ArrayList<>();
                    while (questionsRs.next()) {
                        int questionId = questionsRs.getInt("questionId");
                        String questionText = questionsRs.getString("questionText");
                        int marks = questionsRs.getInt("marks");
                        questions.add(new Question(questionId, questionText, marks));
                    }

                    // Send questions to the client
                    for (int i = 0; i < questions.size(); i++) {
                        out.println("Question " + (i + 1) + ": " + questions.get(i).getQuestionText());
                    }

                    // Read user answers
                    out.println("Please enter your answers separated by commas (e.g., answer1,answer2,...):");
                    out.flush();
                    String answers = in.readLine();

                    // Process and validate answers
                    String[] userAnswers = answers.split(",");
                    if (userAnswers.length != questions.size()) {
                        out.println("Incorrect number of answers. Please provide answers for all questions.");
                        return;
                    }

                    // Calculate score (dummy implementation)
                    int totalScore = 0;
                    for (int i = 0; i < userAnswers.length; i++) {
                        totalScore += questions.get(i).getMarks(); // Dummy scoring logic
                    }

                    // Insert attempt results into database
                    String insertResultsQuery = "INSERT INTO attempts (challengeId, username, score) VALUES (?, ?, ?)";
                    try (PreparedStatement resultsPstmt = connection.prepareStatement(insertResultsQuery)) {
                        resultsPstmt.setInt(1, challengeId);
                        resultsPstmt.setString(2, "exampleUser"); // Replace with actual username
                        resultsPstmt.setInt(3, totalScore);
                        resultsPstmt.executeUpdate();
                    }

                    out.println("Your answers have been submitted. Your score is " + totalScore + ".");
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while processing the challenge attempt.");
        }
    }

    private static void handleViewApplicants(PrintWriter out) {
        File file = new File("registration_details.txt");

        if (!file.exists()) {
            out.println("No applicants registered yet.");
            out.flush();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                out.println(line);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            out.println("Failed to read applicants. Please try again.");
            out.flush();
        }
    }

    private static void sendEmail(String recipientEmail, String username, String firstname, String lastname, String dob) throws UnsupportedEncodingException {
        String fromEmail = "tgnsystemslimited@gmail.com";
        String senderName = "National-E-Mathematics-Competition";
        final String usernameEmail = "tgnsystemslimited@gmail.com";
        final String password = "anls iqfv zxwt irfq";
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usernameEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, senderName));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Registration Details Successfully Sent");

            String emailContent = "Dear " + firstname + " " + lastname + ",\n\n";
            emailContent += "Your registration details have been successfully received and are under review.\n";
            emailContent += "Username: " + username + "\n";
            emailContent += "Firstname: " + firstname + "\n";
            emailContent += "Lastname: " + lastname + "\n";
            emailContent += "Date of Birth: " + dob + "\n";
            emailContent += "Please keep this email for future reference.\n\n";
            emailContent += "You will be receiving an email once your registration is approved.\n\n";
            emailContent += "Thank you for registering for the National E-Mathematics Competition.";

            message.setText(emailContent);
            Transport.send(message);

            System.out.println("Sent message successfully....");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleConfirmApplicant(PrintWriter out, BufferedReader in, Connection connection) {
        try {
            out.println("\nPlease enter your choice (confirm yes <username> or confirm no <username>):");
            out.flush();

            String command = in.readLine();
            if (command == null || command.isEmpty()) {
                out.println("Invalid input. Please try again.");
                out.flush();
                return;
            }

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

                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(username)) {
                        applicantDetails = currentLine;
                        userFound = true;
                        continue;
                    }
                    writer.write(currentLine + System.lineSeparator());
                }

                if (!userFound) {
                    out.println("Applicant not found.");
                    out.flush();
                    return;
                }

                String emailAddress = applicantDetails.split(",")[3];
                String[] details = applicantDetails.split(",");
                String firstname = details[1];
                String lastname = details[2];
                String dob = details[5];

                if (applicantDetails != null) {
                    if (action.equals("yes")) {
                        insertIntoDatabase(connection, "participants", applicantDetails);
                        out.println("Applicant confirmed and added to participants.");
                        sendEmail(emailAddress, username, firstname, lastname, dob);
                    } else if (action.equals("no")) {
                        insertIntoDatabase(connection, "rejected", applicantDetails);
                        out.println("Applicant rejected and added to rejected.");
                        sendEmail(emailAddress, username, firstname, lastname, dob);
                    } else {
                        out.println("Invalid action. Use 'yes' or 'no'.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                out.println("An error occurred while processing the applicant.");
                out.flush();
            }

            if (!inputFile.delete()) {
                out.println("Failed to delete the original file.");
                out.flush();
                return;
            }

            if (!tempFile.renameTo(inputFile)) {
                out.println("Failed to rename the temp file.");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            out.println("An error occurred while confirming the applicant.");
            out.flush();
        }
    }

    private static void handleViewChallenges(PrintWriter out, Connection connection) {
        try {
            LocalDate currentDate = LocalDate.now();
            String query = "SELECT * FROM challenges WHERE CURDATE() BETWEEN startDate AND endDate";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                List<Challenge> challenges = new ArrayList<>();
                while (resultSet.next()) {
                    int challengeId = resultSet.getInt("challengeId");
                    int numberOfQuestions = resultSet.getInt("numberOfQuestions");
                    String duration = resultSet.getString("duration");
                    Date startDate = resultSet.getDate("startDate");
                    Date endDate = resultSet.getDate("endDate");
                    challenges.add(new Challenge(challengeId, numberOfQuestions, duration, startDate, endDate));
                }

                if (challenges.isEmpty()) {
                    out.println("No active challenges found.");
                } else {
                    for (Challenge challenge : challenges) {
                        out.println("Challenge ID: " + challenge.getChallengeId());
                        out.println("Challenge Name: " + challenge.getNumberOfQuestions());
                        out.println("Challenge Name: " + challenge.getDuration());
                        out.println("Start Date: " + challenge.getStartDate());
                        out.println("End Date: " + challenge.getEndDate());
                        out.println();
                    }
                }
                out.flush();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while retrieving challenges.");
            out.flush();
        }
    }

    private static void insertIntoDatabase(Connection connection, String tableName, String applicantDetails) {
        String[] details = applicantDetails.split(",");
        String query = "INSERT INTO " + tableName + " (username, firstname, lastname, email, phone, dob) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, details[0]); // username
            pstmt.setString(2, details[1]); // firstname
            pstmt.setString(3, details[2]); // lastname
            pstmt.setString(4, details[3]); // email
            pstmt.setString(5, details[4]); // phone
            pstmt.setString(6, details[5]); // dob
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            try (Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mathchallenge", "root", "")) {
                while (true) {
                    try (Socket clientSocket = serverSocket.accept();
                         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                        System.out.println("Client connected: " + clientSocket.getInetAddress());

                        String command;
                        while ((command = in.readLine()) != null) {
                            if (command.startsWith("AttemptChallenge")) {
                                handleAttemptChallenge(command, out, in, connection);
                            } else if (command.startsWith("ViewApplicants")) {
                                handleViewApplicants(out);
                            } else if (command.startsWith("ConfirmApplicant")) {
                                handleConfirmApplicant(out, in, connection);
                            } else if (command.startsWith("ViewChallenges")) {
                                handleViewChallenges(out, connection);
                            } else {
                                out.println("Unknown command. Please try again.");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
