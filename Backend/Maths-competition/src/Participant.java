import java.io.Serializable;

public class Participant implements Serializable{
    private static final long serialVersionUID = 1L;
    private final String participantId;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String dateOfBirth;
    private final String schoolRegNo;
    private final String imageFile;
    private int attempts;

    public Participant(String participantId, String username, String firstName, String lastName, String email, String dateOfBirth, String schoolRegNo, String imageFile) {
        this.participantId = participantId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.schoolRegNo = schoolRegNo;
        this.imageFile = imageFile;
        this.attempts = 0;
    }

    public String getUsername() {
        return username;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
    }
    public String getParticipantId() {
        return participantId;
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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getSchoolRegNo() {
        return schoolRegNo;
    }

    public String getImageFile() {
        return imageFile;
    }

}

