package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classroom")
public class Classroom implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_school")
    private School school;

    @Column(name = "class_name")
    private String className;

    @Column(name = "class_year")
    private Integer year;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_form_tutor")
    private Employee formTutor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Classroom classroom = (Classroom) o;

        if (id != null ? !id.equals(classroom.id) : classroom.id != null) return false;
        if (school != null ? !school.equals(classroom.school) : classroom.school != null) return false;
        if (className != null ? !className.equals(classroom.className) : classroom.className != null) return false;
        if (year != null ? !year.equals(classroom.year) : classroom.year != null) return false;
        return formTutor != null ? formTutor.equals(classroom.formTutor) : classroom.formTutor == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (school != null ? school.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (formTutor != null ? formTutor.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Classroom{" +
                "id=" + id +
                ", school=" + school.getName() +
                ", className='" + className + '\'' +
                ", year=" + year +
                ", formTutor=" + formTutor.getFirstName() + " " + formTutor.getLastName() +
                '}';
    }
}
