package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class Parent {
    private Integer id;
    private String surname;
    private String firstName;
    private Integer studentId;
    private String telNumber;
    private String mobilePhoneNumber;
    private String mail;
}
