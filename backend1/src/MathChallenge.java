import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MathChallenge {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mathchallenge";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        List<String> challengeQuestions = getRandomQuestions(10);
        Scanner scanner = new Scanner(System.in);

        for (String question : challengeQuestions) {
            System.out.println(question);
            String answer = scanner.nextLine().trim();
            // Here you can process the answer as needed
            System.out.println("Your answer: " + answer);
            System.out.println();
        }

        scanner.close();
    }

    public static List<String> getRandomQuestions(int numberOfQuestions) {
        List<String> questions = new ArrayList<>();
        String query = "SELECT questionText FROM questions ORDER BY RAND() LIMIT ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, numberOfQuestions);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                questions.add(rs.getString("questionText"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
}