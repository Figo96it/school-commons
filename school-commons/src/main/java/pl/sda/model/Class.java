package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Class {
    private Long id;
    private Long idSchool;
    private String className;
    private Integer year;
    private Long idFormTutor;
}
