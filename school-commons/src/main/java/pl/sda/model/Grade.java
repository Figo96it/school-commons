package pl.sda.model;

public class Grade {

    private int id;
    private int subjectId;
    private int studentGradeId;

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

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", studentGradeId=" + studentGradeId +
                '}';
    }
}
