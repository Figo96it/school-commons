package pl.sda;

import org.junit.Before;
import org.junit.Test;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.sda.TestSharedFunctions.checkIfAnyFieldIsNull;
import static pl.sda.mocks.MockDataResolver.*;

public class MockDataResolverTest {

    @Before
    public void populateData() {
        MockDataResolver.createFakeDbDataWithRelations();
    }

    @Test
    public void checkIfParentDataIsNotNull() {
        List<Parent> parents = findAllParents();
        for (Parent parent : parents) {
            assertFalse(checkIfAnyFieldIsNull(parent));
        }
    }

    @Test
    public void checkIfStudentDataIsNotNull() {
        List<Student> students = findAllStudents();
        for (Student student : students) {
            assertFalse(checkIfAnyFieldIsNull(student));
        }
    }

    @Test
    public void checkIfGradeDataIdIsNotNull() {
        List<Grade> grades = findAllGrades();
        for (Grade grade : grades) {
            assertFalse(checkIfAnyFieldIsNull(grade));
        }
    }

    @Test
    public void checkIfGradeValueIsBetween1And6AndGradeValuesAreNotTheSame() {
        List<Grade> grades = findAllGrades();
        Set<Integer> gradeSet = new HashSet<>();
        for (Grade grade : grades) {
            gradeSet.add(grade.getGrade());
            assertTrue(grade.getGrade() > 0
                    && grade.getGrade() < 7);
        }
        assertTrue(gradeSet.size() > 1);
    }


    @Test
    public void checkIfClassroomsIsNotNull() {
        List<Classroom> classrooms = findAllClassrooms();
        assertFalse(classrooms.isEmpty());
        for (Classroom classroom : classrooms) {
            System.out.println(classroom);
            assertFalse(checkIfAnyFieldIsNull(classroom));
        }
    }

    @Test
    public void checkIfEmployeesIsNotNull() {
        List<Employee> employees = MockDataResolver.findAllEmployees();
        assertFalse(employees.isEmpty());
        for (Employee employee : employees) {
            System.out.println(employee);
            assertFalse(checkIfAnyFieldIsNull(employee));
        }
    }

    @Test
    public void checkIfSubjectsIsNotNull() {
        List<Subject> subjects = MockDataResolver.findAllSubjects();
        assertFalse(subjects.isEmpty());
        for (Subject subject : subjects) {
            System.out.println(subject);
            assertFalse(checkIfAnyFieldIsNull(subject));
        }
    }

    @Test
    public void checkIfTestSchoolIsNotNull() {
        School fakeSchool = MockDataResolver.createFakeSchool();
        System.out.println(fakeSchool);
        assertFalse(checkIfAnyFieldIsNull(fakeSchool));
    }

    @Test
    public void checkIfFakeDataCreationWorks() {
        MockDataResolver mdr = new MockDataResolver();
        MockDataResolver.createFakeDbDataWithRelations();
        assertFalse(checkIfAnyFieldIsNull(mdr));

        iterateOverList(MockDataResolver.findAllEmployees());
        iterateOverList(MockDataResolver.findAllClassrooms());
        iterateOverList(MockDataResolver.findAllGrades());
        iterateOverList(MockDataResolver.findAllParents());
        iterateOverList(MockDataResolver.findAllStudents());
        iterateOverList(MockDataResolver.findAllSubjects());
    }

    private void iterateOverList(List<?> listOfObjects) {
        for (Object object : listOfObjects) {
            assertFalse(checkIfAnyFieldIsNull(object));
        }
    }
}

