import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Server1 {
    public static void main(String[] args)throws IOException,ClassNotFoundException, SQLException {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader K = null;
        PrintWriter PR = null;

        try {
            serverSocket = new ServerSocket(2003);
            System.out.println("Server started. Waiting for client connection...");
            socket = serverSocket.accept();
            System.out.println("Client connected.");

            K = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PR = new PrintWriter(socket.getOutputStream(), true);

            String userOption;

            while ((userOption = K.readLine()) != null) {
                System.out.println("Received option from client: " + userOption);

                if (userOption.equals("Register")) {
                    PR.println("Your information is being captured");

                    // Read user details
                    String un = K.readLine();
                    System.out.println("Username: " + un);
                    // Capture to file or database
                    // Example: Capture to a file
                    FileOutputStream fop = new FileOutputStream("Participant_data.bin", true);
                    fop.write(("userName: " + un + ", ").getBytes());

                    // Close the file output stream
                    fop.close();
                    
                    // Continue capturing other details...
                } else if (userOption.equals("Validation Status")) {
                    PR.println("Please enter your user name");
                    String userName = K.readLine();
                    System.out.println("Username for validation: " + userName);

                    // Check validation status
                    String validationStatus = checkUsername(userName);
                    PR.println(validationStatus);
                } else if (userOption.equals("Login")) {
                    PR.println("Login as;");
                    PR.println("Participant");
                    PR.println("SchoolRepresentative");

                    String loginOption = K.readLine();
                    System.out.println("Login option: " + loginOption);

                    if (loginOption.equals("Participant")) {
                        // Handle participant login
                    } else if (loginOption.equals("SchoolRepresentative")) {
                        // Handle school representative login
                    }
                } else if (userOption.equals("Exit")) {
                    PR.println("Thank you for contacting us...");
                    break;  // Exit the loop
                } else {
                    PR.println("Invalid Option");
                }
            }
        } catch (IOException e) {
            System.err.println("Error in server: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("SQL Error: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found");
            e.printStackTrace();
        } finally {
            try {
                // Close all resources
                if (K != null) K.close();
                if (PR != null) PR.close();
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    // Method to validate username
public static String checkUsername(String userName)throws ClassNotFoundException, SQLException {
    String result = "Not yet Validated";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet acceptedResultSet = null;
    ResultSet rejectedResultSet = null;

    try {
        // Load the database driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish a connection to the database
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/julian", "root", "");

        // Query the accepted table
        String acceptedQuery = "SELECT * FROM accepted WHERE userName=?";
        pstmt = conn.prepareStatement(acceptedQuery);
        pstmt.setString(1, userName);
        acceptedResultSet = pstmt.executeQuery();

        // Check if the username exists in the accepted table
        if (acceptedResultSet.next()) {
            result = "Accepted";
        } else {
            // Query the rejected table
            String rejectedQuery = "SELECT * FROM rejected WHERE username=?";
            pstmt = conn.prepareStatement(rejectedQuery);
            pstmt.setString(1, userName);
            rejectedResultSet = pstmt.executeQuery();

            // Check if the username exists in the rejected table
            if (rejectedResultSet.next()) {
                result = "Rejected";
            } else {
                // If not found in either table, set result to "Not yet Validated"
                result = "Not yet Validated";
            }
        }
    } catch (ClassNotFoundException | SQLException e) {
        // Handle exceptions
        e.printStackTrace();
    } finally {
        // Close result sets
        try {
            if (acceptedResultSet != null) acceptedResultSet.close();
            if (rejectedResultSet != null) rejectedResultSet.close();
            // Close prepared statement
            if (pstmt != null) pstmt.close();
            // Close connection
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return result;
}
}
