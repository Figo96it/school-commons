package pl.sda.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Plan {
    @Id
    private int id;
    @Column
    private String className;
    @Column
    private int year;

    public Plan(int id, String className, int year) {
        this.id = id;
        this.className = className;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Plan{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", year=" + year +
                '}';
    }
}
