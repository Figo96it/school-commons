package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "plan")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_class")
    private Classroom classroom;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST,CascadeType.MERGE })
    @JoinColumn(name = "id")
    private List<Subject> subjects;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Plan plan = (Plan) o;

        if (id != null ? !id.equals(plan.id) : plan.id != null) return false;
        return classroom != null ? classroom.equals(plan.classroom) : plan.classroom == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (classroom != null ? classroom.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", classroom=" + classroom.getClassName()  + " " + classroom.getYear() +
                ", subjects=" + subjects.stream().map(Subject::getSubjectName).collect(Collectors.toList()).toString() +
                '}';
    }
}
