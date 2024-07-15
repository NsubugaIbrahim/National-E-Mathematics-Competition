import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class server {
    private static final int PORT = 4999;
    private final Map<String, Participant> participants = new HashMap<>();
//    private final List<String> questions = new ArrayList<>();
//    private static final long TOTAL_CHALLENGE_TIME_MS = TimeUnit.MINUTES.toMillis(5); // 5 minutes
    private static final int MAX_ATTEMPTS = 3;
    private Connection connection;
    private ObjectOutputStream out = null;

    public server() {
        // Initialize dummy questions
       /* questions.add("What is the capital of France?");
        questions.add("What is 2 + 2?");
        questions.add("Who wrote 'To Kill a Mockingbird'?");*/

        // Set up database connection
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Replace "your_database", "username", and "password" with your actual database name, username, and password
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/maths", "root", "");
            System.out.println("Connected to the database successfully...");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerParticipant(Participant participant) {
        if (participants.containsKey(participant.getUsername())) {
            return false;
        }
        participants.put(participant.getUsername(), participant);
        saveParticipantToDatabase(participant);
        return true;
    }

    private void saveParticipantToDatabase(Participant participant) {
        String sql = "INSERT INTO participant (participantId, username, firstName, lastName, email, dateOfBirth, schoolRegNo, imageFile) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, participant.getParticipantId());
            pstmt.setString(2, participant.getUsername());
            pstmt.setString(3, participant.getFirstName());
            pstmt.setString(4, participant.getLastName());
            pstmt.setString(5, participant.getEmail());
            pstmt.setString(6, participant.getDateOfBirth());
            pstmt.setString(7, participant.getSchoolRegNo());
            pstmt.setString(8, participant.getImageFile());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void startChallenge(Participant participant, PrintWriter out) {
        if (participant.getAttempts() >= MAX_ATTEMPTS) {
            out.println("You have reached the maximum number of attempts for this challenge.");
            return;
        }

        /*participant.incrementAttempts();
        Collections.shuffle(questions);

        long startTime = System.currentTimeMillis();
        int remainingQuestions = questions.size();

        for (String question : questions) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = TOTAL_CHALLENGE_TIME_MS - elapsedTime;

            if (remainingTime <= 0) {
                out.println("Time is up!");
                break;
            }

            // Display time left and remaining questions
            out.println("Time left: " + formatTime(remainingTime) + " | Questions left: " + remainingQuestions);
            out.println(question);

            try {
                Thread.sleep(1000); // Simulate time taken to answer
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            remainingQuestions--;
        }*/
        participant.incrementAttempts();

        List<Question> questions = getQuestionsFromDatabase();
        Collections.shuffle(questions);

        long startTime = System.currentTimeMillis();
        int remainingQuestions = questions.size();

        for (Question question : questions) {
            long elapsedTime = System.currentTimeMillis() - startTime;
            long remainingTime = TimeUnit.MINUTES.toMillis(5) - elapsedTime;

            if (remainingTime <= 0) {
                out.println("Time is up!");
                break;
            }

            // Display time left and remaining questions
            out.println("Time left: " + formatTime(remainingTime) + " | Questions left: " + remainingQuestions);
            out.println(question.getQuestionText());

            try {
                Thread.sleep(1000); // Simulate time taken to answer
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
            e.printStackTrace();
        }
        return questions;
    }

    private String formatTime(long milliseconds) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public Participant getParticipant(String username) {
        return participants.get(username);
    }

    public void viewChallenges(ObjectOutputStream out, String participantId) {
        try {
            // Get the participant's username from the database
            String username = getUsernameFromDatabase(participantId);

            if (username == null) {
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

    private String getUsernameFromDatabase(String participantId) {
        // Replace this query with your actual query to retrieve the username
        String query = "SELECT username FROM participant WHERE participantId = ?";
        String username = null;

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Assuming you have the participantId stored somewhere or passed to this method
            pstmt.setString(001, participantId); // Replace participantId with the actual participantId

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }


    public static void main(String[] args) {
        server server = new server();
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
        private final server server;
        private ObjectOutputStream objOut;

        public ClientHandler(Socket socket, server server) {
            this.socket = socket;
            this.server = server;
        }

        public void run() {
            try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
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
                            if (tokens.length == 3) {
                                String challengeId = tokens[1];
                                String username = tokens[2];
                                Participant participant = server.getParticipant(username);

                                if (participant != null) {
                                    // Check if challengeId exists and handle challenge start
                                    server.startChallenge(participant, out);
                                } else {
                                    out.println("Participant not found, please register first.");
                                }
                            } else {
                                out.println("Invalid input format. Please enter challenge ID and username.");
                            }
                            break;
                        case "VIEWCHALLENGES":
                            if (tokens.length == 2) {
                                String participantId = tokens[1]; // Assuming participantId is passed as the second token
                                server.viewChallenges(objOut, participantId);
                            } else {
                                out.println("Invalid input format. Please enter participantId.");
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
