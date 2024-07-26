import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.Executors;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.nio.file.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.activation.DataSource;
import java.util.Base64;

public class Server {
    private static final String REGISTER = "Register"; // Register
    private static final String LOGIN = "Login"; // Login option for participant
    private static final String REPLOGIN = "Login"; // Login option for representative
    private static final String VIEW_CHALLENGES = "ViewChallenges";
    private static final String ATTEMPT_CHALLENGE = "AttemptChallenge";
    private static final String VIEW_APPLICANTS = "ViewApplicants";
    private static final String LOGOUT = "Logout";
    private static final String BACK = "Back";
    private static final String IMAGE_FOLDER = "saved_images/"; //folder for saving the applicants photos
    private static final String CONFIRM_APPLICANT = "ConfirmApplicant";
    private static final String EXIT = "Exit";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mathchallenge";
    private static final String USER = "root";
    private static final String PASS = "";
    private static String loggedInEmail;

    public static void main(String[] args) throws SQLException {
        // start the server
        try (ServerSocket serverSocket = new ServerSocket(6666);
        // connect to the database
             Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            System.out.println("\n\nWelcome to the Competition!\n");

            while (true) {
                // accept client connections
                try (Socket socket = serverSocket.accept();
                    // read input from client
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     // write output to client
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Client connected...");
                    
                    handleClientSession(in, out, connection);

                } catch (IOException e) {
                    System.err.println("Error: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    private static void handleClientSession(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
        while (true) {
            displayInitialMenu(out);
    
            // read input from client
            String userCategory = in.readLine();
            System.out.println("User category selected: " + userCategory);
    
            if ("participant".equalsIgnoreCase(userCategory)) {
                handleParticipantSession(in, out, connection);
            } else if ("schoolrepresentative".equalsIgnoreCase(userCategory)) {
                handleRepresentativeSession(in, out, connection);
            } else if (EXIT.equalsIgnoreCase(userCategory)) {
                System.out.println("Exiting the server...");
                break; // Exit the server
            } else {
                out.println("Invalid user category selected.");
            }
        }
    }

    private static void handleParticipantSession(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
        while (true) {
            displayParticipantMenu(out);
    
            // read input from client
            String clientChoice = in.readLine();
            System.out.println("Client selected option: " + clientChoice);
    
            if (EXIT.equalsIgnoreCase(clientChoice)) {
                break; // Return to the initial menu
            }
    
            switch (clientChoice) {
                case REGISTER:
                    handleRegistration(in, out, connection);
                    break;
                case LOGIN:
                    handleLogin(in, out, connection);
                    break;
                default:
                    out.println("Invalid option selected.");
            }
        }
    }

    private static void handleRepresentativeSession(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
        while (true) {
            displayRepresentativeMenu(out);
    
            String clientChoice = in.readLine();
            System.out.println("Client selected option: " + clientChoice);
    
            if (BACK.equalsIgnoreCase(clientChoice)) {
                break; // Return to the initial menu
            }
    
            switch (clientChoice) {
                case REPLOGIN:
                    handleRepLogin(in, out, connection);
                    break;
                default:
                    out.println("Invalid option selected.");
            }
        }
    }
    private static void displayInitialMenu(PrintWriter out) {
        out.println("\nWhich user category are you?\n");
        out.println("participant");
        out.println("schoolrepresentative");
        out.println(EXIT); // Add EXIT option
        out.println("Please enter your choice:\n");
    }

    private static void displayParticipantMenu(PrintWriter out) {
        out.println("Participant Menu:\n");
        out.println(REGISTER);
        out.println(LOGIN);
        out.println(EXIT); // Add EXIT option
        out.println("Please enter your choice:");
    }

    private static void displayParticipantLoggedInMenu(PrintWriter out) {
        out.println("Participant Menu (Logged In):\n");
        out.println(VIEW_CHALLENGES);
        out.println(ATTEMPT_CHALLENGE);
        out.println(LOGOUT); // Add LOGOUT option
        out.println(EXIT); // Add EXIT option
        out.println("Please enter your choice:");
    }

    private static void displayRepresentativeMenu(PrintWriter out) {
        out.println("School Representative's Menu:\n");
        out.println(REPLOGIN);
        out.println(EXIT); // Add EXIT option
        out.println("Please enter your choice:\n\n");
    }

    private static void displayRepLoggedInMenu(PrintWriter out) {
        out.println("School Representative Menu (Logged In):\n");
        out.println(VIEW_APPLICANTS);
        out.println(CONFIRM_APPLICANT);
        out.println(LOGOUT); // Add BACK option
        out.println(EXIT); // Add EXIT option
        out.println("Please enter your choice:");
    }

    private static void handleLogin(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
        out.println("Enter your username:");
        String username = in.readLine();
        out.println("Enter your password:");
        String password = in.readLine();

        boolean isAuthenticated = authenticate(username, password, connection);

        if (isAuthenticated) {
            loggedInEmail = getEmailByUsername(username, connection); // Store the email of the logged-in participant
            out.println("Login successful!");
            displayParticipantLoggedInMenu(out);

            String clientChoice = in.readLine();
            System.out.println("Client selected option: " + clientChoice);

            int participantId = getParticipantId(username, connection); // Declare and initialize participantId

            switch (clientChoice) {
                case VIEW_CHALLENGES:
                    viewChallenges(out, in, connection);
                    break;
                    case ATTEMPT_CHALLENGE:
                    try {
                        handleChallenge(in, out, connection, participantId, loggedInEmail);
                    } catch (IOException e) {
                        e.printStackTrace();
                        out.println("An error occurred while handling the challenge: " + e.getMessage());
                        out.flush();
                    }
                    break;
                    case LOGOUT:
                        out.println("Logging out...");
                        return; // Return to the participant menu
                    case EXIT:
                        // Do nothing, will return to the initial menu
                        break;
                    default:
                        out.println("Invalid option selected.");
            }
        } else {
            out.println("Login failed. Invalid username or password.");
        }
    }

    public static int getParticipantId(String username, Connection connection) {
        int participantId = -1;
        String query = "SELECT participantId FROM participants WHERE username = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    participantId = rs.getInt("participantId");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return participantId;
    }

    private static boolean authenticate(String username, String password, Connection connection) {
        String encryptedPassword;
        try {
            encryptedPassword = encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    
        String query = "SELECT COUNT(*) FROM participants WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String getEmailByUsername(String username, Connection connection) {
        String query = "SELECT email FROM participants WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("email");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving email: " + e.getMessage());
        }
        return "";
    }

    private static void handleRepLogin(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
        out.println("Enter your username:");
        String username = in.readLine();
        out.println("Enter your password:");
        String password = in.readLine();

        boolean isAuthenticated = repauthenticate(username, password, connection);

        if (isAuthenticated) {
            
            out.println("Login successful!");
            displayRepLoggedInMenu(out);

            String clientChoice = in.readLine();
            System.out.println("Client selected option: " + clientChoice);
    
            switch (clientChoice) {
                case VIEW_APPLICANTS:
                    handleViewApplicants(out);
                    break;
                case CONFIRM_APPLICANT:
                    handleConfirmApplicant(out, in, connection);
                    break;
                case EXIT:
                    //return to the initial menu
                    break;    
                default:
                    out.println("Invalid option selected.");
            }
        } else {
            out.println("Login failed. Invalid username or password.");
        }
    }

    private static boolean repauthenticate(String username, String password, Connection connection) {
        String encryptedPassword;
        try {
            encryptedPassword = encryptPassword(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    
        String query = "SELECT COUNT(*) FROM representatives WHERE representativeName = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, encryptedPassword);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     private static void handleRegistration(BufferedReader in, PrintWriter out, Connection connection) throws IOException {
            out.println("Please enter your details in the format:Register username firstname lastname emailAddress password date_of_birth school_reg_no image_path");

            String registrationDetails = in.readLine();
            System.out.println("Registration details received: " + registrationDetails);

            try {
                // Parse registration details
                String[] details = registrationDetails.split(" ");
                if (details.length < 9) {
                    out.println("Invalid input format. Please provide all required details.");
                    return;
                }

                String username = details[1];
                String firstname = details[2];
                String lastname = details[3];
                String email = details[4];
                String password = details[5];
                String dateOfBirth = details[6];
                String schoolRegno = details[7];
                String imageFilePath = details[8].replace("\"", ""); // Remove quotes from the file path
                
                // Verify school registration number
                if (!isSchoolRegNoValid(schoolRegno, connection)) {
                out.println("Invalid school registration number. Registration failed.");
                out.flush();
                return;
            }

                // Validate image file path
                Path sourcePath = Paths.get(imageFilePath);
                if (!Files.exists(sourcePath)) {
                    out.println("Image file does not exist. Registration failed.");
                    return;
                }

                // Ensure the folder exists
                File folder = new File(IMAGE_FOLDER);
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                // Define the new image path
                Path destinationPath = Paths.get(IMAGE_FOLDER + sourcePath.getFileName());
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                // Encrypt the password
                String encryptedPassword = encryptPassword(password);

                // Create a string with registration details
                String registrationEntry = String.join(",",
                    username,
                    firstname,
                    lastname,
                    email,
                    encryptedPassword,
                    dateOfBirth,
                    schoolRegno,
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
                sendEmail(email, username, firstname, lastname, dateOfBirth);

                out.println("Registration successful!");
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Registration failed. Please try again.");
            }
     }

     //verifying if the school registration number is among the registered schools
     private static boolean isSchoolRegNoValid(String schoolRegno, Connection connection) {
        String query = "SELECT 1 FROM schools WHERE schoolRegNo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, schoolRegno);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next(); // Returns true if the school registration number exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Encrypts the password
    private static String encryptPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
    
    //saves attempts into attempts table
    private static void saveAttempt(int participantId, int challengeId, int questionId, boolean isCorrect, long duration, LocalDateTime attemptedAt) {
        String query = "INSERT INTO attempts (participantId, challengeId, questionId, isCorrect, duration, attemptedAt, created_at, updated_at) " +
                       "VALUES (?, ?, ?, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setInt(1, participantId);
            pstmt.setInt(2, challengeId);
            pstmt.setInt(3, questionId);
            pstmt.setBoolean(4, isCorrect);
            pstmt.setLong(5, duration);
            pstmt.setObject(6, attemptedAt);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //handles the challenge
    public static void handleChallenge(BufferedReader in, PrintWriter out, Connection connection, int participantId, String loggedInEmail) throws IOException {
        out.println("Enter the command to attempt a challenge: AttemptChallenge [challengeId]");
        out.flush();
    
        String command = in.readLine().trim();
        if (!command.startsWith("AttemptChallenge")) {
            out.println("Invalid command. Please use: AttemptChallenge [challengeId]");
            out.flush();
            return;
        }
    
        int challengeId;
        try {
            challengeId = Integer.parseInt(command.split(" ")[1]);
        } catch (NumberFormatException e) {
            out.println("Invalid challenge ID. Please provide a valid number.");
            out.flush();
            return;
        }
    
        // Check the number of attempts
            int attemptCount;
            try {
                attemptCount = getAttemptCount(connection, participantId, challengeId);
            } catch (SQLException e) {
                // Handle the exception here
                out.println("An error occurred while retrieving the attempt count.");
                out.flush();
                return;
            }

        if (attemptCount >= 3) {
            out.println("You have reached the maximum number of attempts for this challenge.");
            out.flush();
            return;
        }
    
        // Get the current date
        LocalDate currentDate = LocalDate.now();
    
        // Fetch challenge details from the database
        String query = "SELECT numberOfQuestions, duration FROM challenges WHERE challengeId = ? AND ? BETWEEN startDate AND endDate";
        int numberOfQuestions = 0;
        long challengeDuration = 0;
    
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, challengeId);
            pstmt.setString(2, currentDate.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    numberOfQuestions = rs.getInt("numberOfQuestions");
                    challengeDuration = TimeUnit.MINUTES.toMillis(rs.getInt("duration"));
                } else {
                    out.println("Challenge not found or not active.");
                    out.flush();
                    return;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while fetching challenge details: " + e.getMessage());
            out.flush();
            return;
        }
    
        // Get random questions based on the challenge constraints
        List<Questions> challengeQuestions = getRandomQuestions(numberOfQuestions);
        Map<Integer, String> correctAnswers = getCorrectAnswers(challengeQuestions);
        Map<Integer, Integer> questionMarks = getQuestionMarks(correctAnswers); // New method to get marks for each question
        Map<Questions, String> userAnswers = new HashMap<>();
        long startTime = System.currentTimeMillis();
    
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable timeoutTask = () -> {
            out.println("Time's up! Sending the marking guide...");
            out.flush();
            sendMarkingGuide(userAnswers, correctAnswers, questionMarks, loggedInEmail); // Pass the email and marks
        };
        executor.schedule(timeoutTask, challengeDuration, TimeUnit.MILLISECONDS);
    
        try {
            for (Questions question : challengeQuestions) {
                long elapsed = System.currentTimeMillis() - startTime;
                long timeRemaining = challengeDuration - elapsed;
    
                if (timeRemaining <= 0) {
                    break;
                }
    
                // Convert time remaining to minutes and seconds
                long minutesRemaining = timeRemaining / (1000 * 60);
                long secondsRemaining = (timeRemaining % (1000 * 60)) / 1000;
    
                out.println("Remaining questions: " + (challengeQuestions.size() - userAnswers.size()));
                out.println("Time remaining: " + minutesRemaining + " minutes " + secondsRemaining + " seconds");
                out.println("Question: " + question.getQuestionText());
                out.flush();
    
                String answer = in.readLine().trim();
                long duration = System.currentTimeMillis() - startTime;
    
                boolean isCorrect = correctAnswers.containsKey(question.getQuestionId()) &&
                                    correctAnswers.get(question.getQuestionId()).equalsIgnoreCase(answer);
                userAnswers.put(question, answer);
    
                saveAttempt(participantId, challengeId, question.getQuestionId(), isCorrect, duration, LocalDateTime.now());
    
                if (isCorrect) {
                    out.println("Correct!");
                } else {
                    out.println("Wrong! Correct answer: " + correctAnswers.get(question.getQuestionId()));
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdownNow();
        }
    
        // Calculate total score
        int score = (int) userAnswers.entrySet().stream()
            .filter(entry -> correctAnswers.containsKey(entry.getKey().getQuestionId()) &&
                            correctAnswers.get(entry.getKey().getQuestionId()).equalsIgnoreCase(entry.getValue()))
            .mapToInt(entry -> questionMarks.get(entry.getKey().getQuestionId()))
            .sum();
    
        // Determine if the challenge is completed
        boolean completed = userAnswers.size() == numberOfQuestions;
    
        // Insert results into the attemptsCounts table
        String insertQuery1 = "INSERT INTO attemptsCounts (participantId, challengeId, score, correctAnswers, totalQuestions, completed, receivedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery1)) {
            pstmt.setInt(1, participantId);
            pstmt.setInt(2, challengeId);
            pstmt.setInt(3, score);
            pstmt.setInt(4, (int) userAnswers.entrySet().stream()
                .filter(entry -> correctAnswers.containsKey(entry.getKey().getQuestionId()) &&
                                correctAnswers.get(entry.getKey().getQuestionId()).equalsIgnoreCase(entry.getValue()))
                .count());
            pstmt.setInt(5, numberOfQuestions);
            pstmt.setBoolean(6, completed);
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while saving the results: " + e.getMessage());
            out.flush();
        }

        // Insert results into the results table
        String insertQuery2 = "INSERT INTO results (participantId, challengeId, score, correctAnswers, totalQuestions, completed, receivedAt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery2)) {
            pstmt.setInt(1, participantId);
            pstmt.setInt(2, challengeId);
            pstmt.setInt(3, score);
            pstmt.setInt(4, (int) userAnswers.entrySet().stream()
                .filter(entry -> correctAnswers.containsKey(entry.getKey().getQuestionId()) &&
                                correctAnswers.get(entry.getKey().getQuestionId()).equalsIgnoreCase(entry.getValue()))
                .count());
            pstmt.setInt(5, numberOfQuestions);
            pstmt.setBoolean(6, completed);
            pstmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while saving the results: " + e.getMessage());
            out.flush();
        }
    
        // Generate and save the marking guide PDF to the set folder path
        String username = getUsernameByParticipantId(connection, participantId);
        generateMarkingGuidePDF(username, userAnswers, correctAnswers, questionMarks, "C:/xampp/htdocs/National-E-Mathematics-Competition/backend1/src/pdf");
    
        //sendMarkingGuide(userAnswers, correctAnswers, questionMarks, loggedInEmail); // Pass the email and marks

        out.println("Your score: " + score);
        out.println("Completed: " + completed+"\n\n\n\n");
        out.flush();
        displayParticipantLoggedInMenu(out);

    }

    // Generate and save the marking guide PDF
    public static void generateMarkingGuidePDF(String username, Map<Questions, String> userAnswers, Map<Integer, String> correctAnswers, Map<Integer, Integer> questionMarks, String folderPath) {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
    
        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            // Load the custom font
            PDType0Font customFont = PDType0Font.load(document, new File("C:/xampp/htdocs/National-E-Mathematics-Competition/fonts/OpenSans-Italic-VariableFont_wdth,wght.ttf"));
            
            // Set font and font size
            contentStream.setFont(customFont, 12);
            
            // Begin text
            contentStream.beginText();
            contentStream.newLineAtOffset(25, 750);
            contentStream.showText("Marking Guide for " + username);
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20); // Move down for the next line
            contentStream.showText("========================================");
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20); // Move down for the next line
            
            int totalMarks = 0;
            int questionIndex = 1;
    
            for (Map.Entry<Questions, String> entry : userAnswers.entrySet()) {
                Questions question = entry.getKey();
                String userAnswer = entry.getValue();
                int questionId = question.getQuestionId();
                String correctAnswer = correctAnswers.get(questionId);
                int marks = questionMarks.getOrDefault(questionId, 0);
                
                contentStream.showText("Question " + questionIndex + ": " + question.getQuestionText());
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -20); // Move down for the next line
                contentStream.showText("Your Answer: " + userAnswer);
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -20); // Move down for the next line
                contentStream.showText("Correct Answer: " + correctAnswer);
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -20); // Move down for the next line
    
                int awardedMarks = 0;
                if (userAnswer.equals("-") || userAnswer.equals("negative")) {
                    // Participant is not sure, award 0 marks
                    awardedMarks = 0;
                } else if (correctAnswer != null && correctAnswer.equalsIgnoreCase(userAnswer)) {
                    // Correct answer, award specific marks for the question
                    awardedMarks = marks;
                } else {
                    // Wrong answer, deduct 3 marks
                    awardedMarks = -3;
                }
                totalMarks += awardedMarks;
    
                contentStream.showText("Marks: " + awardedMarks);
                contentStream.newLine();
                contentStream.newLineAtOffset(0, -20); // Move down for the next line
                questionIndex++;
            }
    
            contentStream.showText("========================================");
            contentStream.newLine();
            contentStream.newLineAtOffset(0, -20); // Move down for the next line
            contentStream.showText("Total Marks: " + totalMarks);
    
            // End text
            contentStream.endText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Save the document with participant name for easy identification
        File file = new File(folderPath, username + ".pdf");
        try {
            document.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Get the number of attempts for a participant on a given challenge
    private static int getAttemptCount(Connection connection, int participantId, int challengeId) throws SQLException {
        String query = "SELECT COUNT(*) AS attemptCount FROM attemptsCounts WHERE participantId = ? AND challengeId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, participantId);
            preparedStatement.setInt(2, challengeId);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("attemptCount");
                } else {
                    return 0; // No attempts found
                }
            }
        }
    }
    
    // Get the username by participantId to help in saving the pdf
    private static String getUsernameByParticipantId(Connection connection, int participantId) {
        String query = "SELECT username FROM participants WHERE participantId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, participantId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }
    
    //for sending the marking guide to the participant upon completion of a challenge
    private static void sendMarkingGuide(Map<Questions, String> userAnswers, Map<Integer, String> correctAnswers, Map<Integer, Integer> questionMarks, String email) {
        int totalMarks = 0;
        for (Map.Entry<Questions, String> entry : userAnswers.entrySet()) {
            Questions question = entry.getKey();
            String userAnswer = entry.getValue();
            int questionId = question.getQuestionId();
            
            if (userAnswer.equals("-")) {
                // Participant is not sure, award 0 marks
                totalMarks += 0;
            } else if (correctAnswers.containsKey(questionId) &&
                correctAnswers.get(questionId).equalsIgnoreCase(userAnswer)) {
                // Correct answer, award specific marks for the question
                totalMarks += questionMarks.get(questionId);
            } else if (userAnswer.equals("negative")) {
                // Participant is not sure, award 0 marks
                totalMarks += 0;
            } else {
                // Wrong answer, deduct 3 marks
                totalMarks -= 3;
            }
        }
    
        // Create PDF marking guide
        try {
            createPdfMarkingGuide(userAnswers, correctAnswers, totalMarks);
            sendEmailWithAttachment(email, "Marking Guide", "Please find your marking guide attached.", "marking_guide.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Get the marks awarded for each question
    private static Map<Integer, Integer> getQuestionMarks(Map<Integer, String> correctAnswers) {
        Map<Integer, Integer> questionMarks = new HashMap<>();
        String query = "SELECT questionId, marks FROM answers WHERE questionId = ? AND answer = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS)) {
            for (Map.Entry<Integer, String> entry : correctAnswers.entrySet()) {
                int questionId = entry.getKey();
                String correctAnswer = entry.getValue();
                try (PreparedStatement pstmt = connection.prepareStatement(query)) {
                    pstmt.setInt(1, questionId);
                    pstmt.setString(2, correctAnswer);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            questionMarks.put(questionId, rs.getInt("marks"));
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionMarks;
    }
    
    // Create PDF marking guide
    private static void createPdfMarkingGuide(Map<Questions, String> userAnswers, Map<Integer, String> correctAnswers, int totalMarks) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                PDType0Font customFont = PDType0Font.load(document, new File("C:/xampp/htdocs/National-E-Mathematics-Competition/fonts/OpenSans-Italic-VariableFont_wdth,wght.ttf"));
                contentStream.beginText();
                contentStream.setFont(customFont, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(25, 700);

                contentStream.showText("Marking Guide");
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Total Marks: " + totalMarks);
                contentStream.newLine();

                int questionNumber = 1;
                for (Map.Entry<Questions, String> entry : userAnswers.entrySet()) {
                    Questions question = entry.getKey();
                    String userAnswer = entry.getValue();
                    String correctAnswer = correctAnswers.get(question.getQuestionId());

                    contentStream.showText("Question        " + questionNumber + ": " + question.getQuestionText());
                    contentStream.newLine();
                    contentStream.showText("Your Answer:    " + userAnswer);
                    contentStream.newLine();
                    contentStream.showText("Correct Answer: " + correctAnswer);
                    contentStream.newLine();
                    contentStream.newLine();

                    questionNumber++;
                }

                contentStream.endText();
            }

            document.save("marking_guide.pdf");
        }
    }
    //for sending the email attached with the marking guide
    private static void sendEmailWithAttachment(String to, String subject, String body, String filePath) throws UnsupportedEncodingException {
        String from = "tgnsystemslimited@gmail.com";
        String host = "smtp.gmail.com";
        String senderName = "National-E-Mathematics-Competition";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587"); 
    

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "mpdl ahwd lrkg xuqr");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from, senderName));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(filePath);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filePath);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Email with attachment sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
        //for getting random questions
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

    //for getting correct answers
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

    //for viewing applicants
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
    
    //for sending email upon registering
    private static void sendEmail(String recipientEmail, String username, String firstname, String lastname, String dob) throws UnsupportedEncodingException{
        // Sender's email ID and name
        String fromEmail = "tgnsystemslimited@gmail.com";
        String senderName = "National-E-Mathematics-Competition";
        final String usernameEmail = "tgnsystemslimited@gmail.com"; // sender's email ID
        final String password = "mpdl ahwd lrkg xuqr"; // sender's password
    
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
    
            // Set From: header field of the header with sender's name
            message.setFrom(new InternetAddress(fromEmail, senderName));
    
            // Set To: header field of the header
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
    
            // Set Subject: header field
            message.setSubject("Registration Details Successfully Sent");
    
            // Now set the actual message
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
    
            // Send message
            Transport.send(message);
    
            System.out.println("Sent message successfully....");
    
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
    
    //for confirming applicants
    public static void handleConfirmApplicant(PrintWriter out, BufferedReader in, Connection connection) {
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

            // Extract action and username
            String action = parts[1];
            String username = parts[2];
    
            File inputFile = new File("registration_details.txt");
            List<String> fileContents = new ArrayList<>();
            String applicantDetails = null;
    
            // Read the file contents into a list
            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
                String currentLine;
                while ((currentLine = reader.readLine()) != null) {
                    if (currentLine.contains(username)) {
                        applicantDetails = currentLine;
                        continue;
                    }
                    fileContents.add(currentLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Failed to read the file. Please try again.");
                out.flush();
                return;
            }
    
            if (applicantDetails == null) {
                out.println("Applicant not found.");
                out.flush();
                return;
            }
    
            // Extract email address from applicantDetails
            String emailAddress = applicantDetails.split(",")[3]; // emailAddress is in the 4th position
    
            // Insert the applicant details into the  respective table in the database
            if (action.equals("yes")) {
                insertIntoDatabase(connection, "participants", applicantDetails);
                out.println("Applicant confirmed and added to participants.");
                sendEmailConfirmation(emailAddress, "Application Status: Accepted", "Congratulations " + username + ",\nYour application has been accepted.");
            } else if (action.equals("no")) {
                insertIntoDatabase(connection, "rejected", applicantDetails);
                out.println("Applicant rejected and added to rejected.");
                sendEmailConfirmation(emailAddress, "Application Status: Rejected", "Hello " + username + ",\nWe regret to inform you that your application has been rejected.");
            }
            out.flush();
    
            // Write the modified list back to the original file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(inputFile))) {
                for (String line : fileContents) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                out.println("Failed to update the file. Please try again.");
                out.flush();
            }
    
        } catch (Exception e) {
            e.printStackTrace();
            out.println("An error occurred while confirming the applicant.");
            out.flush();
        }
    }
    
    //for sending email upon being confimedyes or no
    private static void sendEmailConfirmation(String toAddress, String subject, String body)throws UnsupportedEncodingException {
        final String fromAddress = "tgnsystemslimited@gmail.com";
        String senderName = "National-E-Mathematics-Competition";
        final String username = "tgnsystemslimited@gmail.com";
        final String password = "mpdl ahwd lrkg xuqr";
    
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587"); 
    
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress, senderName ));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toAddress));
            message.setSubject(subject);
            message.setText(body);
    
            Transport.send(message);
            System.out.println("Email sent successfully.");
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Failed to send email.");
        }
    }
    
    private static void insertIntoDatabase(Connection connection, String tableName, String applicantDetails) {
        String[] details = applicantDetails.split(",");
        
        // Debugging statement to print the length and content of details
        System.out.println("Number of details: " + details.length);
        for (int i = 0; i < details.length; i++) {
            System.out.println("Detail " + i + ": " + details[i]);
        }
        
        // Ensure that there are exactly 8 details (excluding the Register)
        if (details.length < 8) {
            System.out.println("Insufficient details provided.");
            return;
        }
    
        String query = "INSERT INTO " + tableName + " (username, firstname, lastname, email, password, dateOfBirth, schoolRegno, imageFilePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, details[0]);
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

    //view challenges
    private static void viewChallenges(PrintWriter out, BufferedReader in, Connection connection) throws IOException {
        try {
            

            // statement to execute the SQL query
            Statement statement = connection.createStatement();

            // Execute the SQL query to fetch the challenges with current date between startDate and endDate
            String query = "SELECT * FROM challenges WHERE CURDATE() BETWEEN startDate AND endDate";
            ResultSet resultSet = statement.executeQuery(query);

            // Create an empty list to store the challenges
            List<Challenge> challenges = new ArrayList<>();

            // Iterate over the result set and populate the list of challenges
            while (resultSet.next()) {
                int challengeId = resultSet.getInt("challengeId");
                int numberOfQuestions = resultSet.getInt("numberOfQuestions");
                String duration = resultSet.getString("duration");
                String startDate = resultSet.getString("startDate");
                String endDate = resultSet.getString("endDate");

                Challenge challenge = new Challenge(challengeId, numberOfQuestions, duration, startDate, endDate);
                challenges.add(challenge);
            }

            // Close the result set and statement
            resultSet.close();
            statement.close();

            // Display the challenges
            out.println("Challenges:");
            for (Challenge challenge : challenges) {
                out.println("Challenge ID: " + challenge.getChallengeId());
                out.println("Number of Questions: " + challenge.getNumberOfQuestions());
                out.println("Duration: " + challenge.getDuration());
                out.println("Start Date: " + challenge.getStartDate());
                out.println("End Date: " + challenge.getEndDate());
                out.println();

                displayParticipantLoggedInMenu(out);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("An error occurred while fetching challenges: " + e.getMessage());
            out.flush();
        }
    }
    

    static class Challenge {
        private int challengeId;
        private int numberOfQuestions;
        private String duration;
        private String startDate;
        private String endDate;
    
        public Challenge(int challengeId, int numberOfQuestions, String duration, String startDate, String endDate) {
            this.challengeId = challengeId;
            this.numberOfQuestions = numberOfQuestions;
            this.duration = duration;
            this.startDate = startDate;
            this.endDate = endDate;
        }
    
        public int getChallengeId() {
            return challengeId;
        }
    
        public int getNumberOfQuestions() {
            return numberOfQuestions;
        }
    
        public String getDuration() {
            return duration;
        }
    
        public String getStartDate() {
            return startDate;
        }
    
        public String getEndDate() {
            return endDate;
        }
    }
}