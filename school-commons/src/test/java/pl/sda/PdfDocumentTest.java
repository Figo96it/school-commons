package pl.sda;

import org.junit.Before;
import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static pl.sda.TestSharedFunctions.checkIfAnyFieldIsNull;
import static pl.sda.TestSharedFunctions.checkIfFileExists;
import static pl.sda.mocks.MockDataResolver.findAllParents;


public class PdfDocumentTest {
    private final String PATH = ".";

    @Before
    public void populateData() {
        MockDataResolver.createFakeDbDataWithRelations();
    }

    @Test
    public void createParentReport() {
        List<Parent> allParents = findAllParents();
        assertFalse(checkIfAnyFieldIsNull(allParents));
        PdfDocument pdfDocument = new PdfDocument(allParents, PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfFileExists(String.format(PATH + "/Parent_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createGradesReport() {
        List<Grade> allGrades = MockDataResolver.findAllGrades();
        assertFalse(checkIfAnyFieldIsNull(allGrades));
        PdfDocument pdfDocument = new PdfDocument(allGrades, PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfFileExists(String.format(PATH + "/Grade_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createStudentReport() {
        List<Student> allStudents = MockDataResolver.findAllStudents();
        assertFalse(checkIfAnyFieldIsNull(allStudents));
        PdfDocument pdfDocument = new PdfDocument(allStudents, PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfFileExists(String.format(PATH + "/Student_report_%s.pdf", LocalDate.now().toString())));
    }

    @Test
    public void createClassReport() {
        List<Classroom> allClassrooms = MockDataResolver.findAllClassrooms();
        assertFalse(checkIfAnyFieldIsNull(allClassrooms));
        PdfDocument pdfDocument = new PdfDocument(allClassrooms, PATH);
        assertTrue(pdfDocument.generate());
        assertTrue(checkIfFileExists(String.format(PATH + "/Classroom_report_%s.pdf", LocalDate.now().toString())));
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
        assertTrue(checkIfFileExists(String.format(PATH + "/Integer_report_%s.pdf", LocalDate.now().toString())));
    }
}
