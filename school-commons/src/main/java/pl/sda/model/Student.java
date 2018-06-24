package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Student {
    private int studentId;
    private int classId;
    private String firstName;
    private String lastName;
}