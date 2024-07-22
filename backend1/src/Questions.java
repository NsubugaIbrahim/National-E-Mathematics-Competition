public class Questions {
    private int questionId;
    private String questionText;

    public Questions(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }
}
