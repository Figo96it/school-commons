package pl.sda.mocks;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import pl.sda.model.*;
import pl.sda.model.Classroom;
import java.util.concurrent.ThreadLocalRandom;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

public class MockDataResolver {

    private static final int NUMBER_OF_RECORDS = 100;
    private static List<Parent> parentList = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();
    private static List<Grade> gradeList = new ArrayList<>();

    public static List<Parent> findAllParents() {
        return generateMockDataParents(NUMBER_OF_RECORDS);
    }

    public static List<Student> findAllStudents() {
        return generateMockDataStudents(NUMBER_OF_RECORDS);
    }

    public static List<Grade> findAllGrades() {
        return generateMockDataGrades(NUMBER_OF_RECORDS);
    }

    //student object set to null.
    private static List<Parent> generateMockDataParents(int numberOfParents) {
        for (int i = 0; i < numberOfParents; i++) {
            Fairy fairy = Fairy.create();
            Person person = fairy.person();
            Parent parent = new Parent(i + 1, person.getLastName(),
                    person.getFirstName(), null, person.getTelephoneNumber(),
                    person.getTelephoneNumber(), person.getEmail());
            parentList.add(parent);
        }
        return parentList;
    }
    //classroom object set to null.
    private static List<Student> generateMockDataStudents(int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            Fairy fairy = Fairy.create();
            Person person = fairy.person();
            Student student = new Student(i + 1, null, person.getFirstName(), person.getLastName());
            studentList.add(student);
        }
        return studentList;
    }
    //subject and studentGrade set to null
    private static List<Grade> generateMockDataGrades(int numberOfGrades) {
        for (int i = 0; i < numberOfGrades; i++) {
            Grade grade = new Grade(i + 1, new Subject(),ThreadLocalRandom.current().nextInt(1,6));
            gradeList.add(grade);
        }
        return gradeList;
    }
}
