package pl.sda;

import org.junit.Test;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.*;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.sda.mocks.MockDataResolver.*;

public class MockDataResolverTest {

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
            assertTrue(grade.getId() > 0
                    && (grade.getGrade() != null)
                    && grade.getSubject() != null);
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

    private static boolean checkIfAnyFieldIsNull(Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value == null) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return false;
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
    public void testRun(){
        MockDataResolver mdr = new MockDataResolver();
        mdr.createFakeDbDataWithRelations();
        assertFalse(checkIfAnyFieldIsNull(mdr));
    }

}

