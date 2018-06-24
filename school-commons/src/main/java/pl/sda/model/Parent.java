package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Parent {
    private int id;
    private String surname;
    private String firstName;
    private String studentId;
    private String telNumber;
    private String mobilePhoneNumber;
    private String mail;
}
