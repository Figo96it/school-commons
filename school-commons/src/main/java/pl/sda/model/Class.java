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
@ToString
@Entity
@Table(name = "class")
public class Class implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_class")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_school")
    private Integer idSchool;

    @Column(name = "class_name")
    private String className;

    @Column(name = "class_year")
    private Date year;

    @OneToOne
    @JoinColumn(name = "form_tutor_id" )
    private Employee formTutor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Class aClass = (Class) o;

        if (id != null ? !id.equals(aClass.id) : aClass.id != null) return false;
        if (idSchool != null ? !idSchool.equals(aClass.idSchool) : aClass.idSchool != null) return false;
        if (className != null ? !className.equals(aClass.className) : aClass.className != null) return false;
        if (year != null ? !year.equals(aClass.year) : aClass.year != null) return false;
        return formTutor != null ? formTutor.equals(aClass.formTutor) : aClass.formTutor == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (idSchool != null ? idSchool.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (formTutor != null ? formTutor.hashCode() : 0);
        return result;
    }
}
