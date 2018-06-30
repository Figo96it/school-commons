package pl.sda.mocks;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;
import pl.sda.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.random;

public class MockDataResolver {

    public static List<School> getSchoolsList() {
        return schoolsList;
    }

    private enum Position {JANITOR, TEACHER, PRINCIPAL, COOK, LAWYER}

    private enum SubjectEnum {CHEMISTRY, MATH, ENGLISH, HISTORY, PHYSICS, YOGA, GEOGRAPHY, BIOLOGY}

    private static final Fairy fairyData = Fairy.create();

    private static final int NUMBER_OF_RECORDS = 100;
    private static List<School> schoolsList = new ArrayList<>();
    private static List<Classroom> classroomList = new ArrayList<>();
    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Plan> plansList = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();
    private static List<Parent> parentList = new ArrayList<>();
    private static List<StudentGrade> studentGradesList = new ArrayList<>();
    private static List<Grade> gradeList = new ArrayList<>();
    private static List<Subject> subjectsList = new ArrayList<>();

    public static List<Parent> findAllParents() {
        return generateMockDataParents(NUMBER_OF_RECORDS);
    }

    public static List<Student> findAllStudents() {
        return generateMockDataStudents(NUMBER_OF_RECORDS);
    }

    public static List<Grade> findAllGrades() {
        return generateMockDataGrades(NUMBER_OF_RECORDS);
    }

    public static List<Classroom> findAllClassrooms() {
        return generateMockDataClassroom(NUMBER_OF_RECORDS / 10);
    }

    public static List<Employee> findAllEmployees() {
        return generateMockDataEmployee(NUMBER_OF_RECORDS);
    }

    public static List<Subject> findAllSubjects() {
        return generateMockDataSubject();
    }


    private static List<Parent> generateMockDataParents(int numberOfParents) {
        for (int i = 0; i < numberOfParents; i++) {
            Person person = fairyData.person();
            Parent parent = new Parent(i + 1, person.getLastName(),
                    person.getFirstName(), new Student(), person.getTelephoneNumber(),
                    person.getTelephoneNumber(), person.getEmail());
            parentList.add(parent);
        }
        return parentList;
    }

    private static List<Student> generateMockDataStudents(int numberOfStudents) {
        for (int i = 0; i < numberOfStudents; i++) {
            Person person = fairyData.person();
            Student student = new Student(i + 1, new Classroom(), person.getFirstName(), person.getLastName());
            studentList.add(student);
        }
        return studentList;
    }

    private static List<Grade> generateMockDataGrades(int numberOfGrades) {
        for (int i = 0; i < numberOfGrades; i++) {
            Grade grade = new Grade(i + 1, new Subject(),
                    new StudentGrade(), (int) (random() * 6 + 1));
            gradeList.add(grade);
        }
        return gradeList;
    }

    private static List<Classroom> generateMockDataClassroom(int numberOfClassrooms) {
        School school = new School();
        for (int i = 0; i < numberOfClassrooms; i++) {
            TextProducer textProducer = fairyData.textProducer();
            Integer year = fairyData.dateProducer().randomDateBetweenYears(1990, 2018).toDate().getYear();
            Classroom classroom = new Classroom(i + 1, school, textProducer.latinWord(1), year,
                    new Employee());
            classroomList.add(classroom);
        }
        return classroomList;
    }


    private static List<Employee> generateMockDataEmployee(int numberOfRecords) {
        Random random = new Random();
        for (int i = 0; i < numberOfRecords; i++) {
            Person person = fairyData.person();
            Position position = Position.values()[random.nextInt(Position.values().length)];
            Employee employee = new Employee(i + 1, person.getFirstName(), person.getLastName(), position.toString(), new Classroom());
            employeeList.add(employee);
        }
        return employeeList;
    }


    private static List<Subject> generateMockDataSubject() {
        SubjectEnum[] values = SubjectEnum.values();
        for (int i = 0; i < values.length; i++) {
            subjectsList.add(new Subject(i + 1, values[i].name(), new Plan()));
        }
        return subjectsList;
    }

    private static void generateMockDataSubject(Plan plan) {
        SubjectEnum[] values = SubjectEnum.values();
        Classroom classroom = plan.getClassroom();
        for (int i = 0; i < values.length; i++) {
            String subjectName = String.format("%s_%s%d", values[i].name(), classroom.getClassName(), classroom.getYear());

            Subject subject = new Subject(subjectsList.size(), subjectName, plan);
            subjectsList.add(subject);
        }
    }


    public static void createFakeDbDataWithRelations() {
        cleanAll();
        // 2 schools
        schoolsList.add(createFakeSchool());
        schoolsList.add(createFakeSchool());
        // 10 classrooms
        generateMockDataClassroom(NUMBER_OF_RECORDS / 10);
        // Fill classrooms with employees
        for (int i = 0; i < classroomList.size(); i++) {
            Classroom currentClassroom = classroomList.get(i);
            currentClassroom.setSchool(schoolsList.get(i % 2));
            createFakeEmployeeFor(currentClassroom);
            createFakePlanFor(currentClassroom);
        }

        // create 100 students
        generateMockDataStudents(NUMBER_OF_RECORDS);
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            currentStudent.setClassroom(classroomList.get(i / classroomList.size()));
        }

        generateMockDataParents(NUMBER_OF_RECORDS);
        for (int i = 0; i < parentList.size(); i++) {
            Parent currentParent = parentList.get(i);
            currentParent.setStudent(studentList.get(i));
        }
        generateMockDataSubject();

       // create 5 grades per subject per student
        generateMockDataGradesForAllSubjects(NUMBER_OF_RECORDS * SubjectEnum.values().length * 5);


    }

    private static void generateMockDataGradesForAllSubjects(int numberOfGrades) {
        SubjectEnum[] values = SubjectEnum.values();
        for (int i = 0; i < numberOfGrades; i += values.length) {
            for (int j = 0; j < values.length ; j++) {
                // FAKAP!!! wczesniej utworzyc subjecty!!!
            }
        }
    }

    private static void createFakePlanFor(Classroom currentClassroom) {
        return;
    }

    private static void cleanAll() {
        schoolsList = null;
        subjectsList = null;
        employeeList = null;
        classroomList = null;
        gradeList = null;
        plansList = null;
        parentList = null;
        studentList = null;
        studentGradesList = null;
    }

    public static School createFakeSchool() {
        return new School(schoolsList.size() + 1,
                fairyData.company().getName(),
                fairyData.person().getAddress().toString());
    }

    private static void createFakeEmployeeFor(Classroom classroom) {
        Employee employee = new Employee(employeeList.size(),
                fairyData.person().getFirstName(),
                fairyData.person().getLastName(),
                Position.TEACHER.name(),
                classroom
        );
        employeeList.add(employee);
        classroom.setFormTutor(employee);
    }


}
