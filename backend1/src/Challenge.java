public class Challenge {
    private int challengeId;
    private int numberOfQuestions;
    private String duration;
    private String startDate;
    private String endDate;

    public Challenge(int challengeId, int numberOfQuestions, String duration, String startDate, String endDate) {
        this.challengeId = challengeId;
        this.numberOfQuestions = numberOfQuestions;
        this.duration = duration;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getChallengeId() {
        return challengeId;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getDuration() {
        return duration;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}