package pl.sda.eksporter;


import java.io.IOException;

import com.opencsv.CSVWriter;
import pl.sda.model.Parents;

import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CsvDocument {

    private static String[] getFieldsFrom(Object object) {
        return Stream.of(object.getClass().getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }


    public static boolean write(List<Object> objectsToSave) throws IOException {
        if (objectsToSave == null || objectsToSave.isEmpty()) {
            throw new IllegalArgumentException("Cannot save NULL / EMPTY list!");
        }
        String CSV_OUTPUT_PATH = String.format("../../%s_dump_%s.csv", objectsToSave.get(0).getClass().getSimpleName(),
                LocalDate.now().toString());
        // try with resources

        try (
                Writer writer = Files.newBufferedWriter(Paths.get(CSV_OUTPUT_PATH));
                CSVWriter csvWriter = new CSVWriter(writer,
                        ';',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)
        ) {
            csvWriter.writeNext(getFieldsFrom(objectsToSave.get(0)));
            for (Object object : objectsToSave
                    ) {
                csvWriter.writeNext(getStringArrayFromObject(object));
            }
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String[] getStringArrayFromObject(Object object) throws IllegalAccessException {
        List<String> values = new LinkedList<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(object);
            if (value == null) {
                values.add("");
            } else {
                values.add(value.toString());
            }
        }
        return values.toArray(new String[values.size()]);
    }

    public static void main(String[] args) throws IOException {
        Parents parent1 = new Parents();
        parent1.setFirstName("jacek");
        parent1.setSurname("Piekara");
        parent1.setMail("dupa");
        Parents parent2 = new Parents();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");

        write(Arrays.asList(new Object[]{parent1, parent2}));
    }

}
