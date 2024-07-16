public class LibraryChecker {
    public static void main(String[] args) {
        try {
            Class.forName("org.apache.poi.ss.usermodel.Workbook");
            Class.forName("org.apache.poi.xssf.usermodel.XSSFWorkbook");
            Class.forName("org.apache.xmlbeans.XmlException");
            Class.forName("org.apache.commons.compress.archivers.ArchiveStreamFactory");
            Class.forName("org.apache.commons.collections4.ListValuedMap");
            System.out.println("All required libraries are present.");
        } catch (ClassNotFoundException e) {
            System.err.println("Missing library: " + e.getMessage());
        }
    }
}
