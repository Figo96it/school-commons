package pl.sda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StudentGrade {
    @Id
    private int id;
    @Column
    private int idStudent;
    @Column
    private int idGrade;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(int idGrade) {
        this.idGrade = idGrade;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "id=" + id +
                ", idStudent=" + idStudent +
                ", idGrade=" + idGrade +
                '}';
    }
}
