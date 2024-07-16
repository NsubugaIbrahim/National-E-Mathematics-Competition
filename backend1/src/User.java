import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class User {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/e-challenge";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) {
        String username = "wanyanas";
        String firstname = "Wanyana";
        String lastname = "Swabirinah";
        String emailAddress = "swanyana@gmail.com";
        String password = "Wanyana@1234";
        String dateOfBirth = "2011-07-10";
        String schoolRegNo = "u290081";

        registerUser(username, firstname, lastname, emailAddress, password, dateOfBirth, schoolRegNo);
    }

    public static void registerUser(String username, String firstname, String lastname, String emailAddress, String password, String dateOfBirth, String schoolRegNo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establishing a connection
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // SQL query to insert data
            String sql = "INSERT INTO reg_users (username, firstname, lastname, emailAddress, password, date_of_birth, school_reg_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
            
            // Creating a PreparedStatement object
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, firstname);
            pstmt.setString(3, lastname);
            pstmt.setString(4, emailAddress);
            pstmt.setString(5, password);
            pstmt.setString(6, dateOfBirth);
            pstmt.setString(7, schoolRegNo);

            // Executing the statement
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
