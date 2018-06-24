package pl.sda;

import org.junit.Test;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Parent;

import java.lang.reflect.Field;
import java.util.List;

import static junit.framework.Assert.assertFalse;

public class MockDataResolverTest {

    @Test
    public void checkIfDataISNotNull() {
        List<Parent> parents = MockDataResolver.findAllParents();
        boolean isAnyNull = false;
        for (int i = 0; i < parents.size(); i++) {
            if (checkIfAnyFieldIsNull(parents.get(i))) {
                isAnyNull = true;
                break;
            }
        }
        assertFalse(isAnyNull);
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
