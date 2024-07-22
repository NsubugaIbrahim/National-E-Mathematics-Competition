import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Server1 {
    public static void main(String[] args) throws IOException,ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(2003);
        System.out.println("Server started. Waiting for client connection...");
        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");
       // InputStream clientInput =new InputStreamReader(socket.getInputStream());
        //BufferedReader K=new BufferedReader(ClientInput);
        
        InputStreamReader M=new InputStreamReader(socket.getInputStream());
        BufferedReader K=new BufferedReader(M);
        PrintWriter PR=new PrintWriter(socket.getOutputStream());
        while (true) { 
        String userOption=K.readLine();
        System.out.println(userOption);
        if(userOption.equals("Register")){
            PR.println("Your information is being captured");
            PR.flush();
            PrintStream PS=new PrintStream(new FileOutputStream("Participant_data.bin", true));
            String un=K.readLine();
            System.out.println(un);
            PS.print("userName: ");
            PS.print(un);
            PS.print(", ");
            String fn=K.readLine();
            System.out.println(fn);
            PS.print("firstName:");
            PS.print(fn);
            PS.print(", ");
            String ln=K.readLine();
            System.out.println(ln);
            PS.print("lastName: ");
            PS.print(ln);
            PS.print(", ");
            String mail=K.readLine();
            System.out.println(mail);
            PS.print("emailAddress: ");
            PS.print(mail);
            PS.print(", ");
            String DOB=K.readLine();
            System.out.println(DOB);
            PS.print("dateOfBirth: ");
            PS.print(DOB);
            PS.print(", ");
            String SchholReg=K.readLine();
            System.out.println(SchholReg);
            PS.print("schoolRegistrationNumber: ");
            PS.print(SchholReg);
            PS.print(", ");
            String ep=K.readLine();
            System.out.println(ep);
            PS.print("password: ");
            PS.print(ep);
            PS.print(", ");
            InputStream imageInput=socket.getInputStream();
            FileOutputStream fop=new FileOutputStream("Participant_data.bin", true);
            byte[] buffer=new byte[5242880];
            int bytesRead;
            while((bytesRead=imageInput.read(buffer)) !=-1){
                fop.write(buffer, 0, bytesRead);
            }
            imageInput.close();
            fop.close();       
        }//Registration loop.
        else if(userOption.equals("Validation Status")){
            PR.println("Please enter your user name");
            PR.flush();
            String userName=K.readLine();
            System.out.println(userName);
            PR.println("Checking files. Please wait...");
            PR.flush();
            String ss=K.readLine();
            System.out.println(ss);
            //Checking the database for login status
            System.out.println(checkUsername(userName));
            String ValidationStatus=checkUsername(userName);
            PR.println(ValidationStatus);
            PR.flush();
        }//Validation loop ends here.
        else if(userOption.equals("Login")){
            PR.println("Login as;");
            PR.println("Participant");
            PR.println("SchoolRepresentative");
            PR.flush();
            String loginOption=K.readLine();
            System.out.println(loginOption);
            if (loginOption.equals("Participant")){

            }//Participant Menu.
            else if (loginOption.equals("SchoolRepresentative")){

            }//SchoolRepresentative menu.
        }//Login loop and menus in each login option end here
        else if(userOption.equals("Exit")){
            PR.println("Thank you for contacting us...");
            PR.flush();
        }//Exit loop ends here
        else{
            PR.println("Invalid Option");
            PR.flush();
        }//Invalid option ends 
       /*  FileOutputStream fileOutputStream = new FileOutputStream("user_data.bin"); This kind of format overights the existing information in the file.

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, bytesRead);
        }

        fileOutputStream.close();
        socket.close();
        serverSocket.close();

        System.out.println("Data stored in file: " + new File("user_data.bin").getAbsolutePath());
         FileOutputStream fileOutputStream = new FileOutputStream("user_data.bin", true); 
            ObjectOutputStream oos=new ObjectOutputStream(fileOutputStream);
            String un=K.readLine();
            System.out.println(un);
            oos.writeObject(un);
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream("user_data.bin", true));*/
         }//Reptition ends here
             }//Main ends here.
    //Method to Check database for user name in case of validation.
    public static String checkUsername(String userName) throws ClassNotFoundException{
        String result = "Not yet Validated";
        Connection conn = null;
        try {
            // Load the database driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/julian", "root", "");

            // Create a statement to execute the query
            Statement stmt = conn.createStatement();

            // Query the accepted table
            ResultSet acceptedResultSet = stmt.executeQuery("SELECT * FROM accepted WHERE userName='" + userName + "'");

            // Check if the username exists in the accepted table
            if (acceptedResultSet.next()) {
                result = "accepted";
            } else {
                // Query the rejected table
                ResultSet rejectedResultSet = stmt.executeQuery("SELECT * FROM rejected WHERE username='" + userName + "'");
                // Check if the username exists in the rejected table
                if (rejectedResultSet.next()) {
                    result = "rejected";
                }/*else{
                    result="Not yet validated";
                }*/
            }

            // Close the statement and connection
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
        return result;
    }

}
