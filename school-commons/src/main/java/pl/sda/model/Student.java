package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    private int studentId;
    @Column
    private int classId;
    @Column
    private String firstName;
    @Column
    private String lastName;
}