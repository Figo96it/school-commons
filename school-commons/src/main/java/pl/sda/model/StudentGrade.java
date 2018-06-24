package pl.sda.model;

public class StudentGrade {
    private int id;
    private int idStudent;
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
