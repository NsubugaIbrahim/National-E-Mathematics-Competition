
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.sql.*;
import java.util.*;

public class UserConfirmationText {

    private static final String USERS_FILE = "users.txt";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/your_database";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter command (confirm yes/no <username>): ");
        String command = scanner.nextLine();
        String[] commandParts = command.split(" ");

        if (commandParts.length != 3 || !commandParts[0].equals("confirm") || (!commandParts[1].equals("yes") && !commandParts[1].equals("no"))) {
            System.out.println("Invalid command.");
            return;
        }

        String action = commandParts[1];
        String username = commandParts[2];

        try {
            List<String> users = Files.readAllLines(Paths.get(USERS_FILE), StandardCharsets.UTF_8);
            Iterator<String> iterator = users.iterator();
            boolean userFound = false;

            while (iterator.hasNext()) {
                String user = iterator.next();
                String[] userDetails = user.split(",");

                if (userDetails[0].equals(username)) {
                    userFound = true;
                    if (action.equals("yes")) {
                        saveToDatabase(userDetails, "participants");
                    } else {
                        saveToDatabase(userDetails, "rejected");
                    }
                    saveImageToDatabase(userDetails[7], action.equals("yes") ? "participants" : "rejected");
                    iterator.remove();
                    break;
                }
            }

            if (userFound) {
                Files.write(Paths.get(USERS_FILE), users, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
                System.out.println("User " + username + " has been " + (action.equals("yes") ? "confirmed and moved to participants." : "rejected and moved to rejected."));
            } else {
                System.out.println("User " + username + " not found.");
            }
        } catch (IOException e) {
            System.out.println("Error reading/writing users file: " + e.getMessage());
        }
    }

    private static void saveToDatabase(String[] userDetails, String tableName) {
        String query = "INSERT INTO " + tableName + " (username, firstname, lastname, emailAddress, password, date_of_birth, school_reg_no, image_path) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            for (int i = 0; i < userDetails.length; i++) {
                statement.setString(i + 1, userDetails[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving to database: " + e.getMessage());
        }
    }

    private static void saveImageToDatabase(String imagePath, String tableName) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE " + tableName + " SET image = ? WHERE image_path = ?")) {

            File imageFile = new File(imagePath);
            FileInputStream inputStream = new FileInputStream(imageFile);

            statement.setBinaryStream(1, inputStream, (int) imageFile.length());
            statement.setString(2, imagePath);
            statement.executeUpdate();
        } catch (SQLException | IOException e) {
            System.out.println("Error saving image to database: " + e.getMessage());
        }
    }
}
