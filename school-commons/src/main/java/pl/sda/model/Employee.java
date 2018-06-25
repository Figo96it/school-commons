package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Employee {
    private Long id;
    private String firstName;
    private String lastName;
    private String position;
    private Long classId;
}
