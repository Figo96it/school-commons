package pl.sda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Grade {
    @Id
    private int id;
    @Column
    private int subjectId;
    @Column
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
