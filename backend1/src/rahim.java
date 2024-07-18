import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class rahim {

    public static void main(String[] args) {
        User user1 = new User(
            "jdoe", "John", "Doe", "jdoe@example.com", "password123", 
            "2000-01-01", "123456", "C:/xampp/htdocs/National-E-Mathematics-Competition/backend1/images/profileblue.jpg"
        );

        User user2 = new User(
            "asmith", "Alice", "Smith", "asmith@example.com", "securepass", 
            "1999-05-15", "789012", "C:/xampp/htdocs/National-E-Mathematics-Competition/backend1/images/profileblue.jpg"
        );

        RegistrationHandler registrationHandler = new RegistrationHandler();
        try {
            registrationHandler.registerUser(user1);
            registrationHandler.registerUser(user2);
            System.out.println("Users registered successfully!");

            // Retrieve and decode the image
           /* String decodedImagePath = "retrieved_image.jpg";
            registrationHandler.retrieveAndReconstructImage("UserData.xlsx", decodedImagePath);
            System.out.println("Image retrieved successfully at: " + decodedImagePath);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class User {
    private String username;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String password;
    private String dateOfBirth;
    private String schoolRegNo;
    private String imagePath;

    public User(String username, String firstName, String lastName, String emailAddress, 
                String password, String dateOfBirth, String schoolRegNo, String imagePath) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.schoolRegNo = schoolRegNo;
        this.imagePath = imagePath;
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSchoolRegNo() {
        return schoolRegNo;
    }

    public String getImagePath() {
        return imagePath;
    }
}

class ImageUtils {
    public static String encodeImageToBase64(String imagePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public static void decodeBase64ToImage(String base64String, String outputPath) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        Files.write(Paths.get(outputPath), imageBytes);
    }
}

class RegistrationHandler {
    private static final String[] COLUMN_HEADERS = {
            "Username", "First Name", "Last Name", "Email Address", "Password", 
            "Date of Birth", "School Reg No", "Image Part 1", "Image Part 2", "Image Part 3"
    };
    private static final String FILE_NAME = "UserData.xlsx";
    private static final int PART_SIZE = 32767;

    public void registerUser(User user) throws IOException {
        Workbook workbook;
        Sheet sheet;
        int rowCount;

        // Check if file exists
        if (Files.exists(Paths.get(FILE_NAME))) {
            // Load existing workbook and sheet
            FileInputStream fis = new FileInputStream(FILE_NAME);
            workbook = new XSSFWorkbook(fis);
            sheet = workbook.getSheetAt(0);
            rowCount = sheet.getPhysicalNumberOfRows();
            fis.close();
        } else {
            // Create a new workbook and sheet
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("User Data");

            // Create header row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < COLUMN_HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(COLUMN_HEADERS[i]);
            }
            rowCount = 1;
        }

        // Create a new row for the user
        Row row = sheet.createRow(rowCount);
        row.createCell(0).setCellValue(user.getUsername());
        row.createCell(1).setCellValue(user.getFirstName());
        row.createCell(2).setCellValue(user.getLastName());
        row.createCell(3).setCellValue(user.getEmailAddress());
        row.createCell(4).setCellValue(user.getPassword());
        row.createCell(5).setCellValue(user.getDateOfBirth());
        row.createCell(6).setCellValue(user.getSchoolRegNo());

        // Encode image to Base64
        String encodedImage = ImageUtils.encodeImageToBase64(user.getImagePath());

        // Split Base64 string into parts and store in separate cells
        int partCount = (int) Math.ceil((double) encodedImage.length() / PART_SIZE);
        for (int i = 0; i < partCount; i++) {
            int start = i * PART_SIZE;
            int end = Math.min((i + 1) * PART_SIZE, encodedImage.length());
            row.createCell(7 + i).setCellValue(encodedImage.substring(start, end));
        }

        // Write the workbook to a file
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
            workbook.write(fileOut);
        }

        workbook.close();
    }

    /*public void retrieveAndReconstructImage(String excelFilePath, String outputImagePath) throws IOException {
        Workbook workbook = new XSSFWorkbook(Files.newInputStream(Paths.get(excelFilePath)));
        Sheet sheet = workbook.getSheetAt(0);
        Row row = sheet.getRow(1); // Assuming the image is stored in the second row

        // Retrieve Base64 parts from cells
        StringBuilder encodedImageBuilder = new StringBuilder();
        for (int i = 7; i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null) {
                encodedImageBuilder.append(cell.getStringCellValue());
            }
        }

        // Decode the reconstructed Base64 string to an image
        String encodedImage = encodedImageBuilder.toString();
        ImageUtils.decodeBase64ToImage(encodedImage, outputImagePath);

        workbook.close();
    }*/
}
