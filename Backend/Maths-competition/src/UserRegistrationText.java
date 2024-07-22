import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class UserRegistrationText {

    private static final String USERS_FILE = "users.txt";
    private static final String IMAGE_FOLDER = "images/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Create image folder if it does not exist
            Files.createDirectories(Paths.get(IMAGE_FOLDER));
        } catch (IOException e) {
            System.out.println("Error creating image directory: " + e.getMessage());
            return;
        }

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter firstname: ");
        String firstname = scanner.nextLine();

        System.out.print("Enter lastname: ");
        String lastname = scanner.nextLine();

        System.out.print("Enter emailAddress: ");
        String emailAddress = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter date_of_birth (yyyy-MM-dd): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Enter school_reg_no: ");
        String schoolRegNo = scanner.nextLine();

        System.out.print("Enter image file path: ");
        String imagePath = scanner.nextLine();

        String newImagePath = IMAGE_FOLDER + new File(imagePath).getName();

        try {
            Files.copy(Paths.get(imagePath), Paths.get(newImagePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("Error saving image: " + e.getMessage());
            return;
        }

        String userDetails = String.join(",", username, firstname, lastname, emailAddress, password, dateOfBirth, schoolRegNo, newImagePath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(USERS_FILE), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(userDetails);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }

        System.out.println("User registered successfully!");
    }
}
