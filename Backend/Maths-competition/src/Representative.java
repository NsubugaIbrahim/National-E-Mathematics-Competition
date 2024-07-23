import java.io.Serializable;

public class Representative implements Serializable{
    private static final long serialVersionUID = 1L;

    private final String representativeName;
    private final String representativeEmail;
    private final String schoolRegNo;

    public Representative(String representativeName, String representativeEmail, String schoolRegNo) {
        this.representativeName = representativeName;
        this.representativeEmail = representativeEmail;
        this.schoolRegNo = schoolRegNo;
    }
    public String getRepresentativeName() {return representativeName;}

    public String getRepresentativeEmail() {return representativeEmail;}

    public String getSchoolRegNo() {
        return schoolRegNo;
    }

}

