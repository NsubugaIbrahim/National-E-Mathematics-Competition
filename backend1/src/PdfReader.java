import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class PdfReader {
    public static void main(String[] args) {
        String filePath = "C:/xampp/htdocs/National-E-Mathematics-Competition/backend1/src/design_specification_template.pdf"; // Replace with your PDF file path

        try {
            File file = new File(filePath);
            /*PDDocument document = PDDocument.load(file);*/
            PDDocument document = Loader.loadPDF(file);

            if (!document.isEncrypted()) {
                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(document);
                System.out.println("Text in PDF:\n" + text);
            }
            document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
