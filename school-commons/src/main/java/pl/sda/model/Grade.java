package pl.sda.model;



public class Grade {
    private int id;
    private int subjectId;
    private int studentGradeId;
    private int grade;

    public Grade(int id, int subjectId, int studentGradeId) {
        this.id = id;
        this.subjectId = subjectId;
        this.studentGradeId = studentGradeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getStudentGradeId() {
        return studentGradeId;
    }

    public void setStudentGradeId(int studentGradeId) {
        this.studentGradeId = studentGradeId;
    }

    public int getGrade() { return grade;}

    public void setGrade(int grade) {this.grade = grade; }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", studentGradeId=" + studentGradeId +
                '}';
    }
}
