package pl.sda.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Employee {
    @Id
    private Long id;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String position;
    @Column
    private Long classId;
}
