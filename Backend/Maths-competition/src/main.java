import java.io.*;
public static void main(String[] args) {
    String PENDING_PARTICIPANTS_FILE = "src/pending_participants.txt";
    File file = new File(PENDING_PARTICIPANTS_FILE);



    try {
        // Ensure the parent directory exists
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        // Create the file if it does not exist
        if (!file.exists()) {
            file.createNewFile();
        }

        System.out.println("File and directories created successfully.");
    } catch (IOException e) {
        e.printStackTrace();
    }
}
