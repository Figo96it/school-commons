package pl.sda;

import org.junit.Test;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Parent;

import java.lang.reflect.Field;
import java.util.List;

import static junit.framework.Assert.assertFalse;

public class MockDataResolverTest {

    @Test
    public void checkIfDataIsNotNull() {
        List<Parent> parents = MockDataResolver.findAllParents();
        for (Parent parent : parents) {
            assertFalse(checkIfAnyFieldIsNull(parent));
        }
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
