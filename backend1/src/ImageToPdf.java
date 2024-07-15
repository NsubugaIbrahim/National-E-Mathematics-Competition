import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.IOException;

public class ImageToPdf {

    public static void main(String[] args) {
        
        String imagePath = "C:\\Users\\K RAHIM\\Desktop\\project\\profile blue.jpg"; // Replace with your image path
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4); // Create a page

            document.addPage(page);

            PDImageXObject pdImage = PDImageXObject.createFromFile(imagePath, document);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            float scale = 0.5f; // Adjust scale as needed
            contentStream.drawImage(pdImage, 50, 400, pdImage.getWidth() * scale, pdImage.getHeight() * scale);
            contentStream.close();

            document.save("output.pdf"); // Save the document
            document.close();
            
            System.out.println("PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
