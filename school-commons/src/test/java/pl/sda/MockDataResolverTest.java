package pl.sda;

import org.junit.Test;
import pl.sda.model.Grade;
import pl.sda.model.Parent;
import pl.sda.model.Student;

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
                    && grade.getStudentGradeId() > 0
                    && grade.getSubjectId() > 0);
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
}
