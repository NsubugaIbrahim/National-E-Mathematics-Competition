import java.io.*;
import java.net.*;
import java.security.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class Client1 {
    public static void main(String []args) throws IOException{
        Socket s=new Socket("localhost", 2003);
        PrintWriter PR=new PrintWriter(s.getOutputStream(), true);
       // PR.println("How are you");
      // PR.flush();
        InputStreamReader M=new InputStreamReader(s.getInputStream());
        BufferedReader K=new BufferedReader(new InputStreamReader(s.getInputStream()));//Recieves input from the server.
         BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));{
System.out.println("Connected To Server. Please Enter your choice");
System.out.println("MENU");
System.out.println("Register--To Register");
System.out.println("Validation Status --To check Validation Status");
System.out.println("Login --To Login");
System.out.println("Resset Password --To Resset Password");
System.out.println("Fogot Password --In case of fogotten password");
System.out.println("Exit --To Exit");
System.out.println("Please Enter your choice");
  String userOption = userInput.readLine();
  System.out.println(userOption);
  PR.println(userOption);
       PR.flush();
        //String mm=K.readLine();
       // System.out.println(mm);
if (userOption.equals("Register")){
    String mm=K.readLine();
        System.out.println(mm);
    System.out.println("Enter Your userName");
    String userName=userInput.readLine();
    PR.println(userName);
    PR.flush();
    System.out.println("Enter Your firstName");
    String firstName=userInput.readLine();
    PR.println(firstName);
      PR.flush();
    System.out.println("Enter Your lastName");
    String lastName=userInput.readLine();
    PR.println(lastName);
       PR.flush();
    System.out.println("Enter Your emailAddress. Make sure it is in the right format of a gmail.");
      String email;

        do {
            System.out.print("Enter your Gmail address: ");
            email = userInput.readLine();
            
            if (isValidGmail(email)) {
                System.out.println("Valid Gmail address. Thank you!");
                break;
            } else {
                System.out.println("Invalid Gmail address format. Please try again.");
            }
        } while (true);
        PR.println(email);
        PR.flush();
       // reader.close();
   // String emailAddress=userInput.readLine();
    System.out.println("Enter Your dateOfBirth");
    System.out.println("please use the format of yyyy-mm-dd");
    String dateOfBirth=userInput.readLine();
    PR.println(dateOfBirth);
      PR.flush();
    System.out.println("Enter Your schoolRegistrationNumber");
    String schoolRegistrationNumber=userInput.readLine();
    PR.println(schoolRegistrationNumber);
      PR.flush();
     // Prompt user to set password
        System.out.println("Set your password:");
        char[] passwordChars = getPasswordFromUser(userInput);
          // Convert char array to String
        String password = new String(passwordChars);

        // Confirm password
        String confirmedPassword;
        do {
            System.out.println("Confirm your password:");
            char[] confirmedPasswordChars = getPasswordFromUser(userInput);
            confirmedPassword = new String(confirmedPasswordChars);
              if (!confirmedPassword.equals(password)) {
                System.out.println("Passwords do not match. Please try again.");
            }
        } while (!confirmedPassword.equals(password));

        // Encrypting the password (for demonstration, using simple encryption)
        String encryptedPassword = encryptPassword(password);

        System.out.println("Password set and encrypted successfully.");
        System.out.println("Encrypted password: " + encryptedPassword);
        PR.println(encryptedPassword);
      PR.flush();
        System.out.println("You are now to input your image which must be a .png");
 // Create a file chooser
        JFileChooser fileChooser = new JFileChooser();

        // Restrict the file chooser to accept only image files
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files",  "png");
        fileChooser.setFileFilter(filter);

        // Show the file chooser dialog
        int result = fileChooser.showOpenDialog(null);

        // Check if the user selected a file
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Check if the selected file is an image
            if (isImageFile(selectedFile)) {
                JOptionPane.showMessageDialog(null, "You have selected an image: " + selectedFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(null, "The selected file is not an image.");
            }
        }
        File selectedFile = fileChooser.getSelectedFile();
        FileInputStream FIS=new FileInputStream(selectedFile);
        OutputStream OPS=s.getOutputStream();
        byte[] buffer=new byte[5242880];
        int bytesRead;
        while((bytesRead=FIS.read(buffer)) !=-1){
            OPS.write(buffer, 0, bytesRead);
        }
        FIS.close();
        OPS.close();
       // PR.println(isImageFile(null));
      //PR.flush();
        System.out.println("Image has been captured.");
        System.out.println("Registration process complete....");
        System.out.println("Wait for validation by your school representative");
}//Register Loop ends here.
    }
}//Main method ends here.
  // Method to validate if the email is in Gmail format
    public static boolean isValidGmail(String email) {
        // Gmail format regex pattern
        String gmailPattern = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";

        // Check if the email matches the pattern
        return email.matches(gmailPattern);
    }//Method for validating email ends here.
     // Method to get password from user and display asterisks
    private static char[] getPasswordFromUser(BufferedReader reader) throws IOException {
        System.out.print("Enter password: ");
        // Turn off echoing for password input
        String password = "";
        try {
            password = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        char[] passwordChars = password.toCharArray();

        // For security, immediately display asterisks
       // System.out.print("Confirm password (displayed as asterisks): ");
        for (int i = 0; i < passwordChars.length; i++) {
            System.out.print("*");
        }
        System.out.println(); // Move to the next line after displaying asterisks

        return passwordChars;
    }//Method to display User password as asterisk ends here.,
      // Simple encryption method (for demonstration purposes)
    private static String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = md.digest(password.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }//Method to encrypt password ends here.
       // Method to check if a file is an image based on its extension
    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".png");
    }//Method to validate the image format ends here.
}