

import java.io.*;
import java.net.*;
import java.sql.*;

public class hello {
    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        ServerSocket serverSocket = new ServerSocket(6667);
        System.out.println("\n\nWelcome to the Competition!\n\n\n");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected...");

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Read client choice
        String clientChoice = in.readLine();
        System.out.println("Client selected option: " + clientChoice);

        if (clientChoice.equals("participant")) {
            // Registration process
            out.println("Please enter your details in the format:");
            out.println("Register username firstname lastname emailAddress password date_of_birth school_reg_no");

            String registrationDetails = in.readLine();
            System.out.println("Registration details received: " + registrationDetails);

            try {
                splitter(registrationDetails);
                out.println("Registration successful!");
            } catch (Exception e) {
                e.printStackTrace();
                out.println("Registration failed. Please try again.");
            }

        } else if (clientChoice.equals("ViewChallenges")) {
            // View challenges
            out.println("Challenges to be viewed here...");

        } else if (clientChoice.equals("AttemptChallenge")) {
            // Attempt challenge
            out.println("Please enter two numbers separated by a space to add:");

            String numbers = in.readLine();
            String[] numArray = numbers.split(" ");
            if (numArray.length == 2) {
                try {
                    int num1 = Integer.parseInt(numArray[0]);
                    int num2 = Integer.parseInt(numArray[1]);
                    int result = num1 + num2;
                    out.println("The sum is: " + result);
                } catch (NumberFormatException e) {
                    out.println("Invalid input. Please enter valid numbers.");
                }
            } else {
                out.println("Invalid input format. Please enter two numbers separated by a space.");
            }

        } else {
            out.println("Invalid option selected.");
        }

        socket.close();
        serverSocket.close();
    }

        public static void applicants(String username, String firstname, String lastname, String emailAddress,
            String password, String date_of_birth, String school_reg_no) throws SQLException, ClassNotFoundException {
            String query = "INSERT INTO reg_users (username, firstname, lastname, emailAddress, password, date_of_birth, school_reg_no) VALUES (?, ?, ?, ?, ?, ?, ?)";
            String sql = "SELECT * FROM reg_users";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/e-challenge", "root", "")) {
            try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, firstname);
            statement.setString(3, lastname);
            statement.setString(4, emailAddress);
            statement.setString(5, password); // Use provided password
            statement.setString(6, date_of_birth);
            statement.setString(7, school_reg_no);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
            System.out.println("Registration successful!");
            } else {
            System.out.println("Registration failed. No rows were inserted.");
            }
            } catch (SQLIntegrityConstraintViolationException e) {
            // Handle duplicate entry exception
            System.out.println("Registration failed. School registration number already exists.");
            throw e; // Rethrow to propagate the exception
            } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow to propagate the exception
            }

            try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(sql)) {
            while (resultSet.next()) {
            System.out.println("ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
            System.out.println("First Name: " + resultSet.getString("firstname"));
            System.out.println("Last Name: " + resultSet.getString("lastname"));
            System.out.println("Email Address: " + resultSet.getString("emailAddress"));
            System.out.println("Date of Birth: " + resultSet.getString("date_of_birth"));
            System.out.println("School Reg No: " + resultSet.getString("school_reg_no"));
            System.out.println("----------------------------");
            }
                } catch (SQLException e) {
                e.printStackTrace();
                throw e; // Rethrow to propagate the exception
                }
            } 
            
            catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow to propagate the exception
            }
        }


        public static void splitter(String input) throws SQLException, ClassNotFoundException {
            String[] str = input.split(" ");
            if (str.length != 7) {
                System.out.println("Invalid input format.");
                return;
            }
            String username = str[1];
            String firstname = str[2];
            String lastname = str[3];
            String email = str[4];
            String password = str[5]; // Assuming this is provided in the input
            String dob = str[6];
            String reg_no = str[7];

            applicants(username, firstname, lastname, email, password, dob, reg_no);
        }

}
