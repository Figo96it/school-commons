package pl.sda;

import java.io.File;
import java.lang.reflect.Field;

class TestSharedFunctions {

    static boolean checkIfAnyFieldIsNull(Object object) {
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

    static boolean checkIfFileExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }


}
