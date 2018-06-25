package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Grade {
    private Integer id;
    private Integer subjectId;
    private Integer studentGradeId;
    private Integer grade;
}
