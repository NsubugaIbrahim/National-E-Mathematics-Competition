import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Server {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/laravel";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("\n\nWelcome to the Competition!\n\n\n");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client connected...");
            new ClientHandler(socket).start();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;
        private Server serverInstance;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            this.serverInstance = new Server();
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String userMenu = in.readLine();
                System.out.println("User selected option: " + userMenu);

                if (userMenu.equals("Participant menu")) {
                    out.println("Please select an option:");
                    out.println("Register");
                    out.println("Login");

                    String participantChoice = in.readLine();
                    System.out.println("Participant selected option: " + participantChoice);

                    if (participantChoice.equals("Register")) {
                        out.println("Enter registration details (username\tfirstname\tlastname\temailAddress\tpassword\tdate_of_birth [YYYY-MM-DD]\tschool_registration_number\timage_file.png):");

                        String registrationDetails = in.readLine();
                        String[] details = registrationDetails.split("\t");

                        if (details.length != 8) {
                            out.println("Invalid input format. Please provide all details separated by tabs.");
                        } else {
                            String encodedImage = in.readLine();
                            byte[] imageBytes = Base64.getDecoder().decode(encodedImage);

                            // Ensure the images directory exists
                            File imagesDir = new File("images");
                            if (!imagesDir.exists()) {
                                imagesDir.mkdir();
                            }

                            String imagePath = "images/" + details[7];
                            try (FileOutputStream fos = new FileOutputStream(imagePath)) {
                                fos.write(imageBytes);
                            }

                            String schoolRegNo = details[6];
                            if (serverInstance.validateSchoolRegistrationNumber(schoolRegNo)) {
                                serverInstance.saveApplicantDetails(details);
                                out.println("Registration successful!");
                            } else {
                                out.println("Invalid school registration number.");
                            }
                        }
                    }

                } else if (userMenu.equals("Representative menu")) {
                    out.println("Please select an option:");
                    out.println("1. viewApplicants");
                    out.println("2. Exit");

                    String representativeChoice = in.readLine();
                    System.out.println("Representative selected option: " + representativeChoice);

                    if (representativeChoice.equals("viewApplicants")) {
                        out.println("In order to View Applicants you must first Login!");

                        String repName = in.readLine();
                        String repEmail = in.readLine();

                        boolean isValid = serverInstance.validateRepresentative(repName, repEmail);
                        if (isValid) {
                            out.println("Login successful");
                            String schoolRegNo = in.readLine();
                            serverInstance.handleViewApplicants(out, in, schoolRegNo);
                        } else {
                            out.println("Login failed. Invalid name or email!");
                        }
                    } else if (representativeChoice.equals("Exit")) {
                        out.println("Exiting...");
                    } else {
                        out.println("Invalid option selected.");
                    }
                } else {
                    out.println("Invalid option selected.");
                }

                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateRepresentative(String name, String email) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM representatives WHERE representativeName = ? AND representativeEmail = ?")) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateSchoolRegistrationNumber(String schoolRegNo) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM schools WHERE schoolRegNo = ?")) {
            stmt.setString(1, schoolRegNo);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveApplicantDetails(String[] details) {
        try (FileWriter writer = new FileWriter("applicants.csv", true)) {
            String csvData = String.join(",", details);
            writer.append(csvData).append("\n");

            String imagePath = "images/" + details[7];
            new File(imagePath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleViewApplicants(PrintWriter out, BufferedReader in, String schoolRegNo) throws IOException {
        List<Applicant> applicants = readApplicantsFromCSV("applicants.csv");
        List<Applicant> filteredApplicants = new ArrayList<>();
        System.out.println("School Registration Number entered by representative: " + schoolRegNo);
        for (Applicant applicant : applicants) {
            System.out.println("Applicant School Registration Number: " + applicant.getSchoolRegistrationNumber());
            if (applicant.getSchoolRegistrationNumber().equals(schoolRegNo)) {
                filteredApplicants.add(applicant);
            }
        }
    
        if (!filteredApplicants.isEmpty()) {
            for (Applicant applicant : filteredApplicants) {
                out.println("Applicant Details: " + applicant);
                out.println("Confirm acceptance of this applicant by entering: yes/no username");
    
                String response = in.readLine();
                String[] parts = response.split(" ");
                String confirmation = parts[0];
                String username = parts[1];
    
                if (confirmation.equalsIgnoreCase("yes") && username.equals(applicant.getUsername())) {
                    moveToParticipants(applicant);
                } else if (confirmation.equalsIgnoreCase("no") && username.equals(applicant.getUsername())) {
                    moveToRejected(applicant);
                }
            }
        } else {
            out.println("No applicants found for your school!");
        }
    
        out.println("END");
    }
    

    private List<Applicant> readApplicantsFromCSV(String filePath) {
        List<Applicant> applicants = new ArrayList<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                System.out.println("CSV Line Read: " + line);
                String[] values = line.split(",");
                if (values.length == 8) {
                    Applicant applicant = new Applicant(values[0], values[1], values[2], values[3], values[4], values[5], values[6], values[7]);
                    applicants.add(applicant);
                } else {
                    System.out.println("CSV Line does not have 8 values: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicants;
    }

    private void moveToParticipants(Applicant applicant) {
        String insertSQL = "INSERT INTO participants (username, firstName, lastName, email, password, dateOfBirth, schoolRegNo, imageFilePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
    
            stmt.setString(1, applicant.getUsername());
            stmt.setString(2, applicant.getFirstName());
            stmt.setString(3, applicant.getLastName());
            stmt.setString(4, applicant.getEmail());
            stmt.setString(5, applicant.getPassword());
            stmt.setString(6, applicant.getDateOfBirth());
            stmt.setString(7, applicant.getSchoolRegistrationNumber());
    
            String imagePath = "images/" + applicant.getImageFilename();
            stmt.setString(8, imagePath);
    
            stmt.executeUpdate();
    
            deleteFromCSV(applicant);
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void moveToRejected(Applicant applicant) {
        String insertSQL = "INSERT INTO rejected (username, firstName, lastName, email, password, dateOfBirth, schoolRegNo, imageFilePath) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertSQL)) {

                stmt.setString(1, applicant.getUsername());
                stmt.setString(2, applicant.getFirstName());
                stmt.setString(3, applicant.getLastName());
                stmt.setString(4, applicant.getEmail());
                stmt.setString(5, applicant.getPassword());
                stmt.setString(6, applicant.getDateOfBirth());
                stmt.setString(7, applicant.getSchoolRegistrationNumber());
                stmt.setString(8, "images/" + applicant.getImageFilename());

            stmt.executeUpdate();

            deleteFromCSV(applicant);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteFromCSV(Applicant applicant) {
        List<Applicant> applicants = readApplicantsFromCSV("applicants.csv");
        applicants.removeIf(a -> a.getUsername().equals(applicant.getUsername()));
    
        try (FileWriter writer = new FileWriter("applicants.csv", false)) {
            for (Applicant a : applicants) {
                writer.append(a.toString().replace(", ", ",")).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Applicant {
        private String username;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String dateOfBirth;
        private String schoolRegistrationNumber;
        private String imageFilename;

        public Applicant(String username, String firstName, String lastName, String email, String password, String dateOfBirth, String schoolRegistrationNumber, String imageFilename) {
            this.username = username;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.dateOfBirth = dateOfBirth;
            this.schoolRegistrationNumber = schoolRegistrationNumber;
            this.imageFilename = imageFilename;
        }

        public String getUsername() {
            return username;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
        
        public String getDateOfBirth() {
            return dateOfBirth;
        }

        public String getSchoolRegistrationNumber() {
            return schoolRegistrationNumber;
        }

        public String getImageFilename() {
            return imageFilename;
        }

        @Override
        public String toString() {
            return username + ", " + firstName + ", " + lastName + ", " + email + ", "+ password +", " + dateOfBirth + ", " + schoolRegistrationNumber + ", " + imageFilename;
        }
    }
}
