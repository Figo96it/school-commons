package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_grade")
public class StudentGrade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_student")
    private Student student;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_grade")
    private Grade grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        StudentGrade that = (StudentGrade) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (student != null ? !student.equals(that.student) : that.student != null) return false;
        return grade != null ? grade.equals(that.grade) : that.grade == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (student != null ? student.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StudentGrade{" +
                "id=" + id +
                ", student=" + student.getFirstName() + " " + student.getLastName() +
                ", grade=" + grade +
                '}';
    }
}
