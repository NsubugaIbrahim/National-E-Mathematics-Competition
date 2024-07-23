import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import javax.mail.*;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

public class Server {
    private static final int PORT = 4999;
    private final Map<String, Participant> participants = new ConcurrentHashMap<>();
    private final List<String> questions = new ArrayList<>();
    private static final long TOTAL_CHALLENGE_TIME_MS = TimeUnit.MINUTES.toMillis(5); // 5 minutes
    private static final int MAX_ATTEMPTS = 3;
    private Connection connection;
    //private ObjectOutputStream out = null;
    private static final String PENDING_PARTICIPANTS_FILE = "src/pending_participants.txt";

    public Server() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maths", "root", "");
            System.out.println("Connected to the database successfully...");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean registerParticipant(Participant participant) {
        if (participants.containsKey(participant.getUsername())) {
            return false;
        }
        if (!isSchoolRegNoValid(participant.getSchoolRegNo())) {
            return false;
        }

        Representative representative = getRepresentativeBySchoolRegNo(participant.getSchoolRegNo());
        if (representative == null) {
            System.err.println("No representative found for school registration number: " + participant.getSchoolRegNo());
            return false;
        }

        participants.put(participant.getUsername(), participant);
        saveParticipantToFile(participant); // Save participant to file
        sendEmailToSchool(participant, representative); // Send email to school representative
        return true;
    }

    private boolean isSchoolRegNoValid(String schoolRegNo) {
        String sql = "SELECT COUNT(*) FROM schools WHERE schoolRegNo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, schoolRegNo);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error validating school registration number: " + e.getMessage());
        }
        return false;
    }

    private void saveParticipantToFile(Participant participant) {

        try (FileWriter fw = new FileWriter(PENDING_PARTICIPANTS_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(participant.toString()); // Append participant details to file
        } catch (IOException e) {
            System.err.println("Error saving participant to file: " + e.getMessage());
        }
    }

    // Method to load pending participants from file
    private void loadPendingParticipantsFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(PENDING_PARTICIPANTS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    Participant participant = new Participant(
                        parts[0].trim(), parts[1].trim(), parts[2].trim(), parts[3].trim(),
                        parts[4].trim(), parts[5].trim(), parts[6].trim(), parts[7].trim()
                    );
                    participants.put(participant.getUsername(), participant);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading pending participants from file: " + e.getMessage());
        }
    }

    public List<Participant> getPendingParticipants() {
       // loadPendingParticipantsFromFile(); // Load pending participants from file
        return new ArrayList<>(participants.values());
    }

    public void confirmParticipant(String confirmation, String username) {
        Participant participant = participants.get(username);
        if (participant == null) {
            System.err.println("Participant not found with username: " + username);
            return;
        }

        String status;
        if (confirmation.equalsIgnoreCase("yes")) {
            status = "accepted";
        } else if (confirmation.equalsIgnoreCase("no")) {
            status = "rejected";
        } else {
            System.err.println("Invalid confirmation value. Must be 'yes' or 'no'.");
            return;
        }

        try {
            moveParticipantToTable(participant, status);
            participants.remove(username); // Remove from the in-memory list after confirmation
            sendEmail(participant, status.equals("accepted"));
        } catch (SQLException e) {
            System.err.println("Error confirming participant: " + e.getMessage());
        }
    }

    private void moveParticipantToTable(Participant participant, String status) throws SQLException {
        String insertSql;
        if (status.equalsIgnoreCase("accepted")) {
            insertSql = "INSERT INTO participant (participantId, username, firstName, lastName, email, dateOfBirth, schoolRegNo, imageFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        } else {
            insertSql = "INSERT INTO rejected (participantId, username, firstName, lastName, email, dateOfBirth, schoolRegNo, imageFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(insertSql)) {
            pstmt.setString(1, participant.getParticipantId());
            pstmt.setString(2, participant.getUsername());
            pstmt.setString(3, participant.getFirstName());
            pstmt.setString(4, participant.getLastName());
            pstmt.setString(5, participant.getEmail());
            pstmt.setString(6, participant.getDateOfBirth());
            pstmt.setString(7, participant.getSchoolRegNo());
            pstmt.setString(8, participant.getImageFile());
            pstmt.executeUpdate();
            System.out.println("Participant moved to " + (status.equalsIgnoreCase("accepted") ? "participant" : "rejected") + " table.");
        }
    }

    private Representative getRepresentativeBySchoolRegNo(String schoolRegNo) {
        String sql = "SELECT * FROM representatives WHERE schoolRegNo = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, schoolRegNo);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Representative(
                        rs.getString("representativeName"),
                        rs.getString("representativeEmail"),
                        rs.getString("schoolRegNo")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving representative from database: " + e.getMessage());
        }
        return null;
    }

    public static void sendEmail(Participant participant, boolean accepted) {
        final String username = "tgnsystemslimited@gmail.com"; // your email
        final String password = "anls iqfv zxwt irfq"; // your email password
        String toEmail = participant.getEmail(); // Recipient's email address
        String subject = accepted ? "Application Accepted" : "Application Rejected";
        String body = "Dear " + participant.getFirstName() + ",\n\n" +
            "Your application has been " + (accepted ? "accepted." : "rejected.") + "\n\n" +
            "Thank you,\nMath Challenge Team";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.debug", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    private void sendEmailToSchool(Participant participant, Representative representative) {
        final String username = "tgnsystemslimited@gmail.com"; // your email
        final String password = "anls iqfv zxwt irfq"; // your email password
        String toEmail = representative.getRepresentativeEmail(); // School representative's email address
        String subject = "New Participant Registration";
        String body = "Dear " + representative.getRepresentativeName() + ",\n\n" +
            "A new participant has registered with the following details:\n" +
            "Username: " + participant.getUsername() + "\n" +
            "First Name: " + participant.getFirstName() + "\n" +
            "Last Name: " + participant.getLastName() + "\n" +
            "Email: " + participant.getEmail() + "\n" +
            "Date of Birth: " + participant.getDateOfBirth() + "\n" +
            "School Registration Number: " + participant.getSchoolRegNo() + "\n\n" +
            "Please confirm the participant by logging into the system.\n\n" +
            "Thank you,\nMath Challenge Team";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.googlemail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.debug", "true");

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);

            System.out.println("Email sent to school representative successfully!");
        } catch (MessagingException e) {
            System.err.println("Error sending email to school representative: " + e.getMessage());
        }
    }


    public void startChallenge(Participant participant, PrintWriter out, BufferedReader in) throws IOException {
        if (participant == null) {
            out.println("Participant not found, please register first.");
            return;
        }

        if (participant.getAttempts() >= MAX_ATTEMPTS) {
            out.println("You have reached the maximum number of attempts for this challenge.");
            return;
        }

        participant.incrementAttempts();

        List<Question> questions = getQuestionsFromDatabase();
        Collections.shuffle(questions);

        long startTime = System.currentTimeMillis();
        int remainingQuestions = questions.size();

        for (Question question : questions) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = TOTAL_CHALLENGE_TIME_MS - elapsedTime;

            if (remainingTime <= 0) {
                out.println("Time is up!");
                break;
            }

            // Display time left and remaining questions
            out.println("Time left: " + formatTime(remainingTime) + " | Questions left: " + remainingQuestions);
            out.println(question.getQuestionText());

            String answer = in.readLine();
            //if (answer == null) break;

            // Validate the answer
            if (validateAnswer(question.getQuestionId(), answer)) {
                out.println("Correct!");
            } else {
                out.println("Incorrect!");
            }

            try {
                Thread.sleep(2000); // Time taken to answer
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            remainingQuestions--;
        }
        out.println("Challenge completed!");
    }

    private List<Question> getQuestionsFromDatabase() {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Question question = new Question(
                    rs.getString("questionId"),
                    rs.getString("challengeId"),
                    rs.getString("questionText")
                );
                questions.add(question);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving questions from database: " + e.getMessage());
        }
        return questions;
    }

    private boolean validateAnswer(String questionId, String answer) {
        String sql = "SELECT answer FROM answers WHERE questionId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String correctAnswer = rs.getString("correctAnswer");
                return correctAnswer.equalsIgnoreCase(answer);
            }
        } catch (SQLException e) {
            System.err.println("Error validating answer: " + e.getMessage());
        }
        return false;
    }

    private String formatTime(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public Participant getParticipantFromDatabase(String username) {
        String sql = "SELECT * FROM participant WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Participant(
                        rs.getString("participantId"),
                        rs.getString("username"),
                        rs.getString("firstName"),
                        rs.getString("lastName"),
                        rs.getString("email"),
                        rs.getString("dateOfBirth"),
                        rs.getString("schoolRegNo"),
                        rs.getString("imageFile")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving participant from database: " + e.getMessage());
        }
        return null;
    }

    public void viewChallenges(ObjectOutputStream out, String username) {
        try {
            String Username = getUsernameFromDatabase(username);

            if (Username == null) {
                out.writeObject("Participant not found, please register first.");
                out.flush();
                return;
            }
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM challenges WHERE CURDATE() BETWEEN startDate AND endDate");

            List<Challenge> challenges = new ArrayList<>();
            while (rs.next()) {
                Challenge challenge = new Challenge(
                    rs.getString("challengeId"),
                    rs.getInt("duration"),
                    rs.getDate("startDate"),
                    rs.getDate("endDate"),
                    rs.getInt("numberOfQuestions")
                );
                challenges.add(challenge);
            }

            out.writeObject(challenges);
            out.flush();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }
    private String getUsernameFromDatabase(String username) {
        // Replace this query with your actual query to retrieve the username
        String query = "SELECT username FROM participant WHERE participantId = ?";
        String Username = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Username = rs.getString("username");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving username from database: " + e.getMessage());
        }
        return Username;
    }
    public static void main(String[] args) {
        Server server = new Server();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started...");
            while (true) {
                new ClientHandler(serverSocket.accept(), server).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket socket;
        private final Server server;
        ObjectOutputStream objOut;

        public ClientHandler(Socket socket, Server server) {
            this.socket = socket;
            this.server = server;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream())
            ) {
                this.objOut = objOut;
                String input;
                while ((input = in.readLine()) != null) {
                    String[] tokens = input.split(" ");
                    String command = tokens[0];

                    switch (command.toUpperCase()) {
                        case "REGISTER":
                            if (tokens.length == 9) {
                                Participant participant = new Participant(
                                    tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7], tokens[8]
                                );
                                boolean success = server.registerParticipant(participant);
                                out.println(success ? "Participant added successfully!" : "Username already exists. Registration failed.");
                            } else {
                                out.println("Invalid input format. Please enter exactly 8 values separated by spaces.");
                            }
                            break;
                        case "START":
                            if (tokens.length == 2) {
                                String username = tokens[1];
                                Participant participant = server.getParticipantFromDatabase(username);
                                if (username != null) {
                                    out.println("You can start the challenge. ");
                                    server.startChallenge(participant,out,in);
                                } else {
                                    out.println("Participant not found, please register first.");
                                }
                            }else{
                                out.println("Invalid input format. Please enter username.");
                            }
                            break;
                        case "VIEWCHALLENGES":
                            if (tokens.length == 2) {
                                String username = tokens[1]; // Since username is passed as the first token
                                server.viewChallenges(objOut, username);
                            } else {
                                out.println("Invalid input format. Please enter username.");
                            }
                            break;
                        case "VIEWAPPLICANTS":
                            List<Participant> pendingParticipants = server.getPendingParticipants();
                            objOut.writeObject(pendingParticipants);
                            objOut.flush();
                            break;
                        case "CONFIRM":
                            if (tokens.length == 3) {
                                String confirmation = tokens[1];
                                String username = tokens[2];
                                server.confirmParticipant(confirmation, username);
                            } else {
                                out.println("Invalid input format. Use CONFIRM yes/no username.");
                            }
                            break;
                        default:
                            out.println("Invalid command. Try again.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
