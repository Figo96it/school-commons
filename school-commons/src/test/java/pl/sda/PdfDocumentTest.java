package pl.sda;

import org.junit.Before;
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

    @Before
    public void populateData(){
        MockDataResolver.createFakeDbDataWithRelations();
    }


    @Test
    public void createParentReport() {

        PdfDocument pdfDocument = new PdfDocument(findAllParents(), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Parent_report_%s.pdf", LocalDate.now().toString())));
    }
    @Test
    public void createGradesReport() {
        PdfDocument pdfDocument = new PdfDocument(MockDataResolver.findAllGrades(), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Grade_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createStudentReport() {

        PdfDocument pdfDocument = new PdfDocument(MockDataResolver.findAllStudents(), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Student_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createClassReport() {
        PdfDocument pdfDocument = new PdfDocument(MockDataResolver.findAllClassrooms(), PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfExists(String.format(PATH + "/Classroom_report_%s.pdf", LocalDate.now().toString())));
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
