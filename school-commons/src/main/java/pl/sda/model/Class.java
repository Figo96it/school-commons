package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Class {
    private Integer id;
    private Integer idSchool;
    private String className;
    private Date year;
    private Integer idFormTutor;
}
