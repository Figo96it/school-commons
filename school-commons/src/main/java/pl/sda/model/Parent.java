package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Parent {
    @Id
    private int id;
    @Column
    private String surname;
    @Column
    private String firstName;
    @Column
    private String studentId;
    @Column
    private String telNumber;
    @Column
    private String mobilePhoneNumber;
    @Column
    private String mail;
}
