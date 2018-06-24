package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@Data
@Entity
public class Class {
    @Id
    private Long id;
    @Column
    private Long idSchool;
    @Column
    private String className;
    @Column
    private Integer year;
    @Column
    private Long idFormTutor;
}
