package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "parent")
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student aStudent;

    @Column(name ="tell_number" )
    private String telNumber;

    @Column(name = "mobile_phone")
    private String mobilePhoneNumber;

    @Column(name = "mail")
    private String mail;
}

