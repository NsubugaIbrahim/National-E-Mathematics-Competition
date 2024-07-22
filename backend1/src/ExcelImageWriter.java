import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelImageWriter {

    public static void main(String[] args) {
        String imagePath = "C:\\Users\\K RAHIM\\Desktop\\project\\profile blue.png";
        String excelFilePath = "path/to/save/excel/file.xlsx";

        try {
            // Create a Workbook
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Images");

            // Read the image
            InputStream inputStream = new FileInputStream(imagePath);
            byte[] bytes = IOUtils.toByteArray(inputStream);
            inputStream.close();

            // Add Picture to Workbook, resize image if required
            int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
            CreationHelper helper = workbook.getCreationHelper();
            Drawing<?> drawing = sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            // Create an anchor point
            anchor.setCol1(1);
            anchor.setRow1(1);

            // Place the anchor in the worksheet
            Picture pict = drawing.createPicture(anchor, pictureIdx);

            // Save workbook to file
            FileOutputStream fileOut = new FileOutputStream(excelFilePath);
            workbook.write(fileOut);
            fileOut.close();

            // Close workbook
            workbook.close();

            System.out.println("Image saved to Excel successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
