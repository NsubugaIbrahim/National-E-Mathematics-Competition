import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExcelFileOperations {

    private static final String[] HEADERS = {
        "username", "firstname", "lastname", "emailAddress", "password", "date_of_birth", "school_reg_no"
    };
    private static final String FILE_NAME = "users.xlsx";

    public static void writeToFile(String[][] data) {
        @SuppressWarnings("resource")
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Users");

        // Create header row
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < HEADERS.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(HEADERS[i]);
        }

        // Fill data
        for (int i = 0; i < data.length; i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < data[i].length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue(data[i][j]);
            }
        }

        // Write the output to a file
        try (FileOutputStream fileOut = new FileOutputStream(FILE_NAME)) {
            workbook.write(fileOut);
            System.out.println("Excel file written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing the Excel file.");
            e.printStackTrace();
        }
    }

    public static void readFromFile() {
        try (FileInputStream fileIn = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(fileIn)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                for (Cell cell : row) {
                    switch (cell.getCellType()) {
                        case STRING:
                            System.out.print(cell.getStringCellValue() + "\t");
                            break;
                        case NUMERIC:
                            System.out.print(cell.getNumericCellValue() + "\t");
                            break;
                        case BOOLEAN:
                            System.out.print(cell.getBooleanCellValue() + "\t");
                            break;
                        default:
                            break;
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the Excel file.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String[][] users = {
            {"john_doe", "John", "Doe", "john.doe@example.com", "password123", "1990-01-01", "12345"},
            {"jane_smith", "Jane", "Smith", "jane.smith@example.com", "password456", "1992-02-02", "67890"}
        };

        writeToFile(users);
        System.out.println("Reading data from Excel file:");
        readFromFile();
    }
}
