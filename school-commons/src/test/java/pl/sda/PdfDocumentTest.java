package pl.sda;

import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Classroom;
import pl.sda.model.Employee;
import pl.sda.model.Parent;
import pl.sda.model.School;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static pl.sda.mocks.MockDataResolver.findAllParents;


public class PdfDocumentTest {
    private final String PATH = ".";

    private boolean checkIfExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    @Test
    public void createParentReport() {

        MockDataResolver dbMock = new MockDataResolver();
        PdfDocument pdfDocument = new PdfDocument(findAllParents(), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Parent_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createClassReport() {

        Classroom classroom1 = new Classroom(1, new School(), "A", new Date(1990), new Employee());
        Classroom classroom2 = new Classroom(1, new School(), "B", new Date(1991), new Employee());
        Classroom classroom3 = new Classroom(1, new School(), "C", new Date(1992), new Employee());
        Classroom classroom4 = new Classroom(1, new School(), "D", new Date(1993), new Employee());
        Classroom classroom5 = new Classroom(1, new School(), "E", new Date(1994), new Employee());
        Classroom classroom6 = new Classroom(1, new School(), "F", new Date(1995), new Employee());

        PdfDocument pdfDocument = new PdfDocument(asList(classroom1, classroom2, classroom3, classroom4, classroom5, classroom6), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Class_report_%s.pdf", LocalDate.now().toString())));
    }


    @Test
    public void columnNamesTest() {
        Parent parent1 = new Parent();
        PdfDocument pdfDocument = new PdfDocument(singletonList(parent1), PATH);
        List<String> ref = new ArrayList<>();
        ref.add("ID");
        ref.add("FIRSTNAME");
        ref.add("SURNAME");
        ref.add("STUDENT");
        ref.add("TELLNUMBER");
        ref.add("MOBILEPHONENUMBER");
        ref.add("MAIL");
        assertThat(pdfDocument.columnNames, is(ref));

    }

    @Test
    public void integerListTest() {
        PdfDocument pdfDocument = new PdfDocument(asList(1, 2, 3, 4, 5), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Integer_report_%s.pdf", LocalDate.now().toString())));
    }
}
