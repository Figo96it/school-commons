package pl.sda;

import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.model.Class;
import pl.sda.model.Parent;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.time.LocalDate.now;
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
        //when
        PdfDocument pdfDocument = new PdfDocument(findAllParents(), PATH);

        //then
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(format(PATH + "/Parent_report_%s.pdf", now().toString())));
    }
    @Test
    public void createGradesReport() {
        PdfDocument pdfDocument = new PdfDocument(MockDataResolver.findAllGrades(), PATH);
        assertTrue(pdfDocument.createPdfDocument());
        assertTrue(checkIfExists(String.format(PATH + "/Grade_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createStudentReport() {

        PdfDocument pdfDocument = new PdfDocument(MockDataResolver.findAllStudents(), PATH);
        assertTrue(pdfDocument.createPdfDocument());
        assertTrue(checkIfExists(String.format(PATH + "/Student_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createClassReport() {
        //given
        Class class1 = new Class(1, 1, "A", new Date(1990), 1);
        Class class2 = new Class(2, 1, "B", new Date(1991), 2);
        Class class3 = new Class(3, 1, "C", new Date(1992), 3);
        Class class4 = new Class(4, 1, "D", new Date(1993), 4);
        Class class5 = new Class(5, 1, "SKAUCI", new Date(1994), 1);
        Class class6 = new Class(6, 1, "D", new Date(1990), 2);

        //when
        PdfDocument pdfDocument = new PdfDocument(asList(class1, class2, class3, class4, class5, class6), PATH);

        //then
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(format(PATH + "/Class_report_%s.pdf", now().toString())));
    }


    @Test
    public void columnNamesTest() {
        //given
        Parent parent1 = new Parent();
        List<String> ref = new ArrayList<>();
        ref.add("ID");
        ref.add("SURNAME");
        ref.add("FIRSTNAME");
        ref.add("STUDENTID");
        ref.add("TELNUMBER");
        ref.add("MOBILEPHONENUMBER");
        ref.add("MAIL");

        //when
        PdfDocument pdfDocument = new PdfDocument(singletonList(parent1), PATH);

        //then
        assertThat(pdfDocument.columnNames, is(ref));

    }

    @Test
    public void integerListTest() {
        //when
        PdfDocument pdfDocument = new PdfDocument(asList(1, 2, 3, 4, 5), PATH);

        //then
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(format(PATH + "/Integer_report_%s.pdf", now().toString())));
    }
}
