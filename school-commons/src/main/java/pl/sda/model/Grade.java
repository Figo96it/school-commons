package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Grade {
    private int id;
    private int subjectId;
    private int studentGradeId;
    private int grade;
}
