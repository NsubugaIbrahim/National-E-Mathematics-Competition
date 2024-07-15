import java.io.Serializable;
public class Question implements Serializable{
    private static final long serialVersionUID = 1L;
    private String questionId;
    private String challengeId;
    private String questionText;
    //private String answer;
    //private int marks;

    public Question(String questionId, String challengeId, String questionText) {
        this.questionId = questionId;
        this.challengeId = challengeId;
        this.questionText = questionText;
    }

    // Getters...

    public String getQuestionId() {
        return questionId;
    }

    public String getChallengeId() {
        return challengeId;
    }

    public String getQuestionText() {
        return questionText;
    }

}
