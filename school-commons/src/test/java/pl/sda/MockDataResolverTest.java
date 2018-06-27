package pl.sda;

import org.junit.Test;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Grade;
import pl.sda.model.Parent;
import pl.sda.model.Student;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MockDataResolverTest {

    @Test
    public void checkIfParentDataIsNotNull() {
        List<Parent> parents = MockDataResolver.findAllParents();
        for (Parent parent : parents) {
            assertFalse(checkIfAnyFieldIsNull(parent));
        }
    }

    @Test
    public void checkIfStudentDataIsNotNull() {
        List<Student> students = MockDataResolver.findAllStudents();
        for (Student student : students) {
            assertFalse(checkIfAnyFieldIsNull(student));
        }
    }

    @Test
    public void checkIfGradeDataIdIsNotNull() {
        List<Grade> grades = MockDataResolver.findAllGrades();
        for (Grade grade : grades) {
            assertTrue(grade.getId() > 0
                    && (grade.getGrade() != null)
                    && grade.getASubject() != null);
        }
    }

    @Test
    public void checkIfGradeValueIsBetween1And6AndGradeValuesAreNotTheSame() {
        List<Grade> grades = MockDataResolver.findAllGrades();
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
}
