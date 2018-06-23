package pl.sda.model;

public class Plan {

     private int id;
     private String class_name;
     private int year;

    public Plan(int id, String class_name, int year) {
        this.id = id;
        this.class_name = class_name;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
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
                ", class_name='" + class_name + '\'' +
                ", year=" + year +
                '}';
    }
}
