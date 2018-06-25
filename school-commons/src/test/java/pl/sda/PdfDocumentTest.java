package pl.sda;

import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Class;
import pl.sda.model.Parent;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PdfDocumentTest {
    private final String PATH = ".";

    private boolean checkIfExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    @Test
    public void createParentReport() {

        MockDataResolver dbMock = new MockDataResolver();
        PdfDocument pdfDocument = new PdfDocument(dbMock.findAllParents(), PATH);
        assertTrue(pdfDocument.createPdfDocument());
        assertTrue(checkIfExists(String.format(PATH + "/Parent_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createClassReport() {
        Class class1 = new Class(1, 1, "A", new Date(1990), 1);
        Class class2 = new Class(2, 1, "B", new Date(1991), 2);
        Class class3 = new Class(3, 1, "C", new Date(1992), 3);
        Class class4 = new Class(4, 1, "D", new Date(1993), 4);
        Class class5 = new Class(5, 1, "SKAUCI", new Date(1994), 1);
        Class class6 = new Class(6, 1, "D", new Date(1990), 2);
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Class[]{class1, class2, class3, class4, class5, class6}), PATH);
        assertTrue(pdfDocument.createPdfDocument());
        assertTrue(checkIfExists(String.format(PATH + "/Class_report_%s.pdf", LocalDate.now().toString())));
    }


    @Test
    public void columnNamesTest() {
        Parent parent1 = new Parent();
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Parent[]{parent1}), PATH);
        List<String> ref = new ArrayList<>();
        ref.add("ID");
        ref.add("SURNAME");
        ref.add("FIRSTNAME");
        ref.add("STUDENTID");
        ref.add("TELNUMBER");
        ref.add("MOBILEPHONENUMBER");
        ref.add("MAIL");
        assertThat(pdfDocument.columnNames, is(ref));

    }

    @Test
    public void integerListTest(){
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Integer[]{1,2,3,4,5}), PATH);
        assertTrue(pdfDocument.createPdfDocument());
        assertTrue(checkIfExists(String.format(PATH + "/Integer_report_%s.pdf", LocalDate.now().toString())));
    }
}