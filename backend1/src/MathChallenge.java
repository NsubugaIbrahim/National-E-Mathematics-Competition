import java.io.File;
import java.io.IOException;
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

public class MathChallenge {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mathchallenge";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        List<Questions> challengeQuestions = getRandomQuestions(10);
        Map<Integer, String> correctAnswers = getCorrectAnswers(challengeQuestions);
        Map<Questions, String> userAnswers = new HashMap<>();
        Scanner scanner = new Scanner(System.in);
        int totalMarks = 0;

        for (Questions question : challengeQuestions) {
            System.out.println(question.getQuestionText());
            String answer = scanner.nextLine().trim();
            userAnswers.put(question, answer);

            if (correctAnswers.containsKey(question.getQuestionId()) && 
                correctAnswers.get(question.getQuestionId()).equalsIgnoreCase(answer)) {
                totalMarks++;
                System.out.println("Correct!");
            } else {
                System.out.println("Wrong! Correct answer: " + correctAnswers.get(question.getQuestionId()));
            }
            System.out.println();
        }

        System.out.println("You scored: " + totalMarks + " out of " + challengeQuestions.size());
        scanner.close();
        
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
}

class Question {
    private int questionId;
    private String questionText;

    public Question(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }
}
