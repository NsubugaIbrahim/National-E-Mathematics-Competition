import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.io.input.BoundedInputStream;

import java.io.*;
import java.util.Base64;
import java.util.Scanner;

public class gs {
    private static final String EXCEL_FILE_PATH = "registrations.xlsx";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter firstname: ");
        String firstname = scanner.nextLine();

        System.out.print("Enter lastname: ");
        String lastname = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter date of birth (yyyy-mm-dd): ");
        String dateOfBirth = scanner.nextLine();

        System.out.print("Enter school registration number: ");
        String schoolRegNo = scanner.nextLine();

        System.out.print("Enter image path: ");
        String imagePath = scanner.nextLine();

        saveToExcel(username, firstname, lastname, email, password, dateOfBirth, schoolRegNo, imagePath);

        scanner.close();
    }

    private static void saveToExcel(String username, String firstname, String lastname, String email, String password,
                                    String dateOfBirth, String schoolRegNo, String imagePath) {
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        XSSFWorkbook workbook = null;

        try {
            File file = new File(EXCEL_FILE_PATH);
            if (file.exists()) {
                fileInputStream = new FileInputStream(file);
                workbook = new XSSFWorkbook(fileInputStream); // This line likely triggers the error
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet("Registrations");
            if (sheet == null) {
                sheet = workbook.createSheet("Registrations");
                createHeaderRow(sheet);
            }

            int lastRowNum = sheet.getLastRowNum();
            int nextRowIndex = lastRowNum + 1;
            Row dataRow = sheet.createRow(nextRowIndex);
            setDataRow(dataRow, username, firstname, lastname, email, password, dateOfBirth, schoolRegNo, imagePath);

            fileOutputStream = new FileOutputStream(file);
            workbook.write(fileOutputStream);
            System.out.println("Data saved to Excel file successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error occurred: " + e.getMessage());
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        String[] headers = {"Username", "Firstname", "Lastname", "Email", "Password", "Date of Birth", "School Reg No", "Image (Base64 Part 1)", "Image (Base64 Part 2)"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }
    }

    private static void setDataRow(Row dataRow, String username, String firstname, String lastname, String email, String password,
                                   String dateOfBirth, String schoolRegNo, String imagePath) {
        dataRow.createCell(0).setCellValue(username);
        dataRow.createCell(1).setCellValue(firstname);
        dataRow.createCell(2).setCellValue(lastname);
        dataRow.createCell(3).setCellValue(email);
        dataRow.createCell(4).setCellValue(password);
        dataRow.createCell(5).setCellValue(dateOfBirth);
        dataRow.createCell(6).setCellValue(schoolRegNo);

        // Convert image to base64 string
        String base64Image = encodeImageToBase64(imagePath);

        // Split base64 string into parts (assuming each part is 32,767 characters to avoid Excel limit)
        int partLength = 32767; // Maximum length per cell
        int numParts = (int) Math.ceil((double) base64Image.length() / partLength);
        String[] base64Parts = new String[numParts];
        for (int i = 0; i < numParts; i++) {
            int startIdx = i * partLength;
            int endIdx = Math.min(startIdx + partLength, base64Image.length());
            base64Parts[i] = base64Image.substring(startIdx, endIdx);
        }

        // Store base64 parts in separate cells
        for (int i = 0; i < base64Parts.length; i++) {
            dataRow.createCell(7 + i).setCellValue(base64Parts[i]);
        }
    }

    private static String encodeImageToBase64(String imagePath) {
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                FileInputStream fis = new FileInputStream(imageFile);
                byte[] imageBytes = readImageBytes(fis);
                fis.close();

                return Base64.getEncoder().encodeToString(imageBytes);
            } else {
                System.out.println("Image file not found at: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static byte[] readImageBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        return outputStream.toByteArray();
    }
}
