package pl.sda.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Subject {
    @Column
    private String  subjectName;
    @Id
    private int planId;
}
