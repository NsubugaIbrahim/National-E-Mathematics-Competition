import java.io.Serializable;
import java.sql.Date;
//import java.sql.Time;

public class Challenge implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    private final String challengeId;
    private final int duration;
    private final Date startDate;
    private final Date endDate;

    private final int numberOfQuestions;

    public Challenge(String challengeId, int duration, Date startDate, Date endDate, int numberOfQuestions) {
        this.challengeId = challengeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.numberOfQuestions = numberOfQuestions;
    }

    // Getters...

    public String getChallengeId() {
        return challengeId;
    }

    public int getDuration() {
        return duration;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }


    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
}
