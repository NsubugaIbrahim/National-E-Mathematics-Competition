import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONObject;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
//import org.apache.logging.log4j.LogManager;
//import org.apache.xmlbeans.XmlException;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class excell {
    private static final String REGISTER = "Register";
    private static final String VIEW_CHALLENGES = "ViewChallenges";
    private static final String ATTEMPT_CHALLENGE = "AttemptChallenge";
    private static final String VIEW_APPLICANTS = "ViewApplicants"; // New command
    private static final String FILE_PATH = "registration_details.xlsx"; // Excel file path
    private static final String CONFIRM_APPLICANT = "ConfirmApplicant";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
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
                            handleConfirmApplicant(new BufferedReader(new InputStreamReader(System.in)), out); // Use System.in for user input
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

    public static void handleRegistration(BufferedReader in, PrintWriter out) {
        out.println("Please enter your details in the format: username firstname lastname email password date_of_birth school_reg_no \"image_path\"");

        try {
            String registrationDetails = in.readLine();
            System.out.println("Registration details received: " + registrationDetails);

            // Use regex to parse the input
            Pattern pattern = Pattern.compile("^\\S+ (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) (\\S+) \"(.+)\"$");
            Matcher matcher = pattern.matcher(registrationDetails);

            if (!matcher.matches()) {
                throw new IllegalArgumentException("Input format is incorrect");
            }

            String username = matcher.group(1);
            String firstname = matcher.group(2);
            String lastname = matcher.group(3);
            String email = matcher.group(4);
            String password = matcher.group(5);
            String dob = matcher.group(6);
            String schoolRegNo = matcher.group(7);
            String imagePath = matcher.group(8);

            // Ensure the image path is correct
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                throw new FileNotFoundException("Image file not found: " + imagePath);
            }

            // Create Excel workbook and sheet
            File file = new File(FILE_PATH);
            XSSFWorkbook workbook;
            XSSFSheet sheet;

            if (file.exists()) {
                workbook = new XSSFWorkbook(new FileInputStream(file));
                sheet = workbook.getSheetAt(0);
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet("Registration Details");
                createHeaderRow(sheet);
            }

            // Write registration details to Excel
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            writeRegistrationDetails(row, username, firstname, lastname, email, password, dob, schoolRegNo, imagePath, workbook);

            // Save Excel file
            try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
                workbook.write(outputStream);
            }
            workbook.close();

            System.out.println("Registration details saved to " + FILE_PATH);

            // Send email confirmation (implement this method according to your requirements)
            sendEmail(email, username, firstname, lastname, dob);

            out.println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("Registration failed. Please try again.");
        }
    }

    private static void createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Username");
        headerRow.createCell(1).setCellValue("First Name");
        headerRow.createCell(2).setCellValue("Last Name");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Password");
        headerRow.createCell(5).setCellValue("Date of Birth");
        headerRow.createCell(6).setCellValue("School Registration Number");
        headerRow.createCell(7).setCellValue("Image Path");
    }

    private static void writeRegistrationDetails(Row row, String username, String firstname, String lastname, String email, String password, String dob, String schoolRegNo, String imagePath, XSSFWorkbook workbook) throws IOException {
        row.createCell(0).setCellValue(username);
        row.createCell(1).setCellValue(firstname);
        row.createCell(2).setCellValue(lastname);
        row.createCell(3).setCellValue(email);
        row.createCell(4).setCellValue(password);
        row.createCell(5).setCellValue(dob);
        row.createCell(6).setCellValue(schoolRegNo);
        row.createCell(7).setCellValue(imagePath);

        // Add image to Excel
        addImageToExcel(workbook, row.getRowNum(), imagePath);
    }

    private static void addImageToExcel(XSSFWorkbook workbook, int rowNum, String imagePath) throws IOException {
        XSSFSheet sheet = workbook.getSheetAt(0);
        InputStream inputStream = new FileInputStream(imagePath);
        byte[] imageBytes = inputStream.readAllBytes();
        inputStream.close();

        int pictureIdx = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG);
        CreationHelper helper = workbook.getCreationHelper();
        Drawing<?> drawing = sheet.createDrawingPatriarch();

        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setCol1(8);
        anchor.setRow1(rowNum);
        anchor.setCol2(9);
        anchor.setRow2(rowNum + 1);

        drawing.createPicture(anchor, pictureIdx);
    }

    private static void sendEmail(String toEmail, String username, String firstname, String lastname, String dob) {
        // Implement email sending logic here
    }

    
    private static void handleChallenge(BufferedReader in, PrintWriter out) {
        out.println("Enter challenge details:");
        // Implement challenge handling logic
    }

    private static void handleViewApplicants(PrintWriter out) {
        // Implement logic to view applicants
    }

    private static void handleConfirmApplicant(BufferedReader in, PrintWriter out) {
        try {
            System.out.println("Enter username to confirm:");
            String username = in.readLine();
            // Implement logic to confirm applicant
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
