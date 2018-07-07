package pl.sda.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_student")
    private Student student;

    @Column(name = "tell_number")
    private String tellNumber;

    @Column(name = "mobile_phone")
    private String mobilePhoneNumber;

    @Column(name = "mail")
    private String mail;

    @PreRemove
    private void preRemove() {
        student = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Parent parent = (Parent) o;

        if (id != null ? !id.equals(parent.id) : parent.id != null) return false;
        if (firstName != null ? !firstName.equals(parent.firstName) : parent.firstName != null) return false;
        if (surname != null ? !surname.equals(parent.surname) : parent.surname != null) return false;
        if (tellNumber != null ? !tellNumber.equals(parent.tellNumber) : parent.tellNumber != null) return false;
        if (mobilePhoneNumber != null ? !mobilePhoneNumber.equals(parent.mobilePhoneNumber) : parent.mobilePhoneNumber != null)
            return false;
        return mail != null ? mail.equals(parent.mail) : parent.mail == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (tellNumber != null ? tellNumber.hashCode() : 0);
        result = 31 * result + (mobilePhoneNumber != null ? mobilePhoneNumber.hashCode() : 0);
        result = 31 * result + (mail != null ? mail.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", surname='" + surname + '\'' +
                ", tellNumber='" + tellNumber + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
}

