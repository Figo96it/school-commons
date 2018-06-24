package pl.sda.model;

import lombok.Data;

@Data
public class Class {
    private Long id;
    private Long idSchool;
    private String className;
    private Integer year;
    private Long idFormTutor;
}
