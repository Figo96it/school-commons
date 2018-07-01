package pl.sda.mocks;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.text.TextProducer;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import pl.sda.model.*;

import java.util.*;

import static java.lang.Math.random;
import static pl.sda.mocks.MockDataResolver.Position.TEACHER;

@Getter
public class MockDataResolver {


    public enum Position {JANITOR, TEACHER, PRINCIPAL, COOK, LAWYER}

    private enum SubjectEnum {CHEMISTRY, MATH, ENGLISH, HISTORY, PHYSICS, YOGA, GEOGRAPHY, BIOLOGY}

    private static final Fairy fairyData = Fairy.create();

    private static final int NUMBER_OF_RECORDS = 100;
    private static List<School> schoolsList = new ArrayList<>();
    private static List<Classroom> classroomList = new ArrayList<>();
    private static List<Employee> employeeList = new ArrayList<>();
    private static List<Plan> planList = new ArrayList<>();
    private static List<Student> studentList = new ArrayList<>();
    private static List<Parent> parentList = new ArrayList<>();
    private static List<StudentGrade> studentGradeList = new ArrayList<>();
    private static List<Grade> gradeList = new ArrayList<>();
    private static List<Subject> subjectList = new ArrayList<>();

    public static void createFakeDbDataWithRelations() {
        cleanAll();
        // 2 schools
        schoolsList.add(createFakeSchool());
        schoolsList.add(createFakeSchool());
        // 10 classrooms
        generateMockDataClassroom(NUMBER_OF_RECORDS / 10);
        fillClassroomsWithEmployees();

        generateMockDataStudents(NUMBER_OF_RECORDS);

        connectClassroomWithStudent();

        generateMockDataParents(NUMBER_OF_RECORDS);
        connectParentWithStudent();

        generateMockDataStudentGrades();
    }

    public static List<Parent> findAllParents() {
        if (CollectionUtils.isEmpty(parentList)) {
            return generateMockDataParents(NUMBER_OF_RECORDS);
        }
        return parentList;
    }

    public static List<Student> findAllStudents() {
        if (CollectionUtils.isEmpty(studentList)) {
            return generateMockDataStudents(NUMBER_OF_RECORDS);
        }
        return studentList;
    }

    public static List<Grade> findAllGrades() {
        if (CollectionUtils.isEmpty(gradeList)) {
            return generateMockDataGrades(NUMBER_OF_RECORDS);
        }
        return gradeList;
    }

    public static List<Classroom> findAllClassrooms() {
        if (CollectionUtils.isEmpty(classroomList)) {
            return generateMockDataClassroom(NUMBER_OF_RECORDS / 10);
        }
        return classroomList;
    }

    public static List<Employee> findAllEmployees() {
        if (CollectionUtils.isEmpty(employeeList)) {
            return generateMockDataEmployee(NUMBER_OF_RECORDS);
        }
        return employeeList;
    }

    public static List<Subject> findAllSubjects() {
        if (CollectionUtils.isEmpty(subjectList)) {
            return generateMockDataSubject();
        }
        return subjectList;
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
            Student student = new Student(i + 1, new Classroom(), person.getFirstName(), person.getLastName(),person.getAge());
            studentList.add(student);
        }
        return studentList;
    }

    private static List<Grade> generateMockDataGrades(int numberOfGrades) {
        for (int i = 0; i < numberOfGrades; i++) {
            Grade grade = new Grade(i + 1, new Subject(), (int) (random() * 6 + 1), new Date());
            gradeList.add(grade);
        }
        return gradeList;
    }

    private static List<Classroom> generateMockDataClassroom(int numberOfClassrooms) {
        School school = new School();
        Random random = new Random();
        for (int i = 0; i < numberOfClassrooms; i++) {
            TextProducer textProducer = fairyData.textProducer();
            Integer year = random.nextInt(20) + 1998;
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
            Employee employee = new Employee(i + 1, person.getFirstName(), person.getLastName(), position.toString(),new Date(), new Classroom());
            employeeList.add(employee);
        }
        return employeeList;
    }


    private static List<Subject> generateMockDataSubject() {
        SubjectEnum[] values = SubjectEnum.values();
        for (int i = 0; i < values.length; i++) {
            subjectList.add(new Subject(i + 1, values[i].name(), new Plan()));
        }
        return subjectList;
    }

    private static List<Subject> generateMockDataSubject(Plan plan) {
        SubjectEnum[] values = SubjectEnum.values();
        Classroom classroom = plan.getClassroom();
        List<Subject> createdSubjects = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            String subjectName = String.format("%s_%s%d", values[i].name(), classroom.getClassName(), classroom.getYear());

            Subject subject = new Subject(subjectList.size(), subjectName, plan);
            createdSubjects.add(subject);
            subjectList.add(subject);
        }
        return createdSubjects;
    }


    private static void connectParentWithStudent() {
        for (int i = 0; i < parentList.size(); i++) {
            Parent currentParent = parentList.get(i);
            currentParent.setStudent(studentList.get(i));
        }
    }

    private static void connectClassroomWithStudent() {
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            currentStudent.setClassroom(classroomList.get(i / classroomList.size()));
        }
    }

    private static void fillClassroomsWithEmployees() {
        for (int i = 0; i < classroomList.size(); i++) {
            Classroom currentClassroom = classroomList.get(i);
            currentClassroom.setSchool(schoolsList.get(i % 2));
            createFakeEmployeeFor(currentClassroom);
            createFakePlanFor(currentClassroom);
        }
    }

    private static void generateMockDataStudentGrades() {
        for (int i = 0; i < studentList.size(); i++) {
            Student currentStudent = studentList.get(i);
            List<Subject> studentsSubjects = getSubjectsFrom(currentStudent);
            assert studentsSubjects != null;
            for (Subject subject : studentsSubjects) {
                for (int j = 0; j < 5; j++) {
                    Grade grade = createGrade(subject);
                    StudentGrade studentGrade = new StudentGrade(studentGradeList.size(), currentStudent, grade);
                    studentGradeList.add(studentGrade);
                }
            }
        }
    }

    private static Grade createGrade(Subject subject) {
        Grade grade = new Grade(gradeList.size(), subject, (int) (random() * 6 + 1), new Date());
        gradeList.add(grade);
        return grade;
    }

    private static void generateMockDataGradesForAllSubjects(int numberOfGrades) {
        for (int i = 0; i < numberOfGrades; i++) {
            gradeList.add(new Grade(gradeList.size(), null, (int) (random() * 6 + 1), new Date()));
        }
    }

    private static List<Subject> getSubjectsFrom(Student currentStudent) {
        Integer studentsClassroomId = currentStudent.getClassroom().getId();
        Optional<Plan> classroomsPlan = planList.stream().filter(plan -> plan.getClassroom().getId().equals(studentsClassroomId)).findFirst();
        if (classroomsPlan.isPresent()) {
            Plan plan = classroomsPlan.get();
            return plan.getSubjects();
        }
        return null;
    }


    private static void createFakePlanFor(Classroom currentClassroom) {
        Plan plan = new Plan(planList.size(), currentClassroom, null);
        List<Subject> subjects = generateMockDataSubject(plan);
        plan.setSubjects(subjects);
        planList.add(plan);
    }

    private static void cleanAll() {
        schoolsList = new ArrayList<>();
        subjectList = new ArrayList<>();
        employeeList = new ArrayList<>();
        classroomList = new ArrayList<>();
        gradeList = new ArrayList<>();
        planList = new ArrayList<>();
        parentList = new ArrayList<>();
        studentList = new ArrayList<>();
        studentGradeList = new ArrayList<>();
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
                TEACHER.name(),new Date(),
                classroom
        );
        employeeList.add(employee);
        classroom.setFormTutor(employee);
    }

    public static List<Plan> getPlanList() {
        return planList;
    }

    public static List<School> getSchoolList() {
        return schoolsList;
    }

    public static List<StudentGrade> getStudentGradeList() {
        return studentGradeList;
    }
}
