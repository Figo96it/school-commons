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
@Table(name = "grade")
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_subject")
    private Subject subject;

    @Column(name = "grade")
    private Integer grade;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Grade grade1 = (Grade) o;

        if (id != null ? !id.equals(grade1.id) : grade1.id != null) return false;
        if (subject != null ? !subject.equals(grade1.subject) : grade1.subject != null) return false;
        return grade != null ? grade.equals(grade1.grade) : grade1.grade == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", subject=" + subject.getSubjectName() +
                ", grade=" + grade +
                '}';
    }
}
