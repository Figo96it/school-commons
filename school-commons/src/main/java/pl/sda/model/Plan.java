package pl.sda.model;

public class Plan {

    private int id;
    private String className;
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
