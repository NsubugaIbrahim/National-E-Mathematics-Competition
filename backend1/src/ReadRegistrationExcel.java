import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public class ReadRegistrationExcel {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("registrations.xlsx");
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // Assuming data is in the first sheet (index 0)

            // Reading headers
            Row headerRow = sheet.getRow(0);
            int numColumns = headerRow.getLastCellNum();

            // Print headers
            System.out.println("Headers:");
            for (int i = 0; i < numColumns; i++) {
                Cell cell = headerRow.getCell(i);
                if (cell != null) {
                    System.out.print(cell.getStringCellValue() + "\t");
                }
            }
            System.out.println();

            // Reading data rows
            int numRows = sheet.getLastRowNum() + 1;
            for (int r = 1; r < numRows; r++) {
                Row row = sheet.getRow(r);
                if (row != null) {
                    // Assuming fixed columns based on the headers in your example
                    String username = row.getCell(0).getStringCellValue();
                    String firstname = row.getCell(1).getStringCellValue();
                    String lastname = row.getCell(2).getStringCellValue();
                    String email = row.getCell(3).getStringCellValue();
                    String password = row.getCell(4).getStringCellValue();
                    String dateOfBirth = row.getCell(5).getStringCellValue();
                    String schoolRegNo = row.getCell(6).getStringCellValue();

                    // Reading base64 parts and concatenating
                    StringBuilder base64ImageBuilder = new StringBuilder();
                    for (int c = 7; c < numColumns; c++) {
                        Cell imageCell = row.getCell(c);
                        if (imageCell != null) {
                            base64ImageBuilder.append(imageCell.getStringCellValue());
                        }
                    }
                    String base64Image = base64ImageBuilder.toString();

                    // Displaying data
                    System.out.println("-----------------------------");
                    System.out.println("Row " + r + " Data:");
                    System.out.println("Username: " + username);
                    System.out.println("Firstname: " + firstname);
                    System.out.println("Lastname: " + lastname);
                    System.out.println("Email: " + email);
                    System.out.println("Password: " + password);
                    System.out.println("Date of Birth: " + dateOfBirth);
                    System.out.println("School Registration No: " + schoolRegNo);
                    System.out.println("Base64 Image: " + base64Image);
                }
            }

            workbook.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
