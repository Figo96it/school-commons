package pl.sda.eksporter;

import com.opencsv.CSVWriter;
import javafx.util.Pair;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Stream.of;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class CsvDocument {

    /**
     * Will create a csv file "[outputPath]/[classSimpleName]_dump_[currentDate].csv"
     *
     * @param objectsToSave List of objects to be saved to csv.
     * @param outputPath    Directory where to save to output path
     * @return true if successfully saved
     * @throws IOException
     */
    private static final String DEFAULT_CSV_PATH = ".";

    public static boolean write(List<?> objectsToSave, String outputPath) throws IOException {
        if (objectsToSave == null || objectsToSave.isEmpty()) {
            throw new IllegalArgumentException("Cannot save NULL / EMPTY list!");
        }
        Pair<Integer, String> indexAndPath = getPathFrom(outputPath, objectsToSave);
        int startIndex = indexAndPath.getKey();
        String csvOutputPath = indexAndPath.getValue();
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

    private static Pair<Integer, String> getPathFrom(String outputPath, List<?> objectsToSave) {
        for (int i = 0; i < objectsToSave.size(); i++) {
            if (objectsToSave.get(i) != null) {
                return Pair.of(i, String.format("%s/%s_dump_%s.csv", getOutputPath(outputPath),
                        objectsToSave.get(i).getClass().getSimpleName(),
                        LocalDate.now().toString()));
            }
        }
        throw new IllegalArgumentException("Only null elements in list!");
    }


    private static String getOutputPath(String outputPath) {
        return isBlank(outputPath) ? DEFAULT_CSV_PATH : outputPath.replace("\\", "/");
    }

    private static String[] getFieldsFrom(Object object) {
        return of(object.getClass().getDeclaredFields()).map(Field::getName).toArray(String[]::new);
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
