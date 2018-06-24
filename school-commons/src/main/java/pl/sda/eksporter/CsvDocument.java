package pl.sda.eksporter;

import com.opencsv.CSVWriter;
import org.apache.commons.lang3.StringUtils;

import java.io.Writer;
import java.lang.reflect.Field;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class CsvDocument {

    /**
     * Will create a csv file "[outputPath]/[classSimpleName]_dump_[currentDate].csv"
     * @param objectsToSave List of objects to be saved to csv.
     * @param outputPath    Directory where to save to output path
     * @return true if successfully saved
     * @throws IOException
     */
    private static int startIndex;

    public static boolean write(List<Object> objectsToSave, String outputPath) throws IOException {
        if (objectsToSave == null || objectsToSave.isEmpty()) {
            throw new IllegalArgumentException("Cannot save NULL / EMPTY list!");
        }
        startIndex = 0;
        String csvOutputPath = getPathFrom(outputPath, objectsToSave);
        // try with resources
        try (
                Writer writer = Files.newBufferedWriter(Paths.get(csvOutputPath));
                CSVWriter csvWriter = new CSVWriter(writer,
                        ';',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END)
        ) {
            csvWriter.writeNext(getFieldsFrom(objectsToSave.get(startIndex)));
            for (Object object : objectsToSave.subList(startIndex, objectsToSave.size())) {
                csvWriter.writeNext(getStringArrayFromObject(object));
            }
            return true;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static String getPathFrom(String outputPath, List<Object> objectsToSave) {
        for (int i = 0; i < objectsToSave.size(); i++) {
            if (objectsToSave.get(i) != null) {
                startIndex = i;
                return String.format("%s/%s_dump_%s.csv", getOutputPath(outputPath),
                        objectsToSave.get(i).getClass().getSimpleName(),
                        LocalDate.now().toString());
            }
        }
        throw new IllegalArgumentException("Only null elements in list!");
    }


    private static String getOutputPath(String outputPath) {
        return StringUtils.isBlank(outputPath) ? "../.." : outputPath.replace("\\", "/");
    }

    private static String[] getFieldsFrom(Object object) {
        return Stream.of(object.getClass().getDeclaredFields()).map(Field::getName).toArray(String[]::new);
    }

    private static String[] getStringArrayFromObject(Object object) throws IllegalAccessException {
        List<String> values = new LinkedList<>();
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            Object value = field.get(object);
            values.add(value == null ? "" : value.toString());
        }
        return values.toArray(new String[values.size()]);
    }
}
