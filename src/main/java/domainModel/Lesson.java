package domainModel;

public class Lesson {
    private final int adID;
    private String studentCF;

    public Lesson(int adID, String studentCF){
        this.adID = adID;
        this.studentCF = studentCF;
    }

    // Getters
    public int getAdID() {
        return adID;
    }

    public String getStudentCF() {
        return studentCF;
    }

    // Setters
    public void setStudentCF(String newStud){
        this.studentCF = newStud;
    }


}
