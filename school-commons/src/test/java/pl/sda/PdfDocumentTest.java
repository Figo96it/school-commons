package pl.sda;

import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.Class;
import pl.sda.model.Parent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PdfDocumentTest {
    private final String PATH = ".";

    @Test
    public void runTest() {

        MockDataResolver dbMock = new MockDataResolver();
        PdfDocument pdfDocument = new PdfDocument(dbMock.findAllParents(), PATH);
        pdfDocument.createPdfDocument();
    }

    @Test
    public void createClassReport() {
        Class class1 = new Class(1L, 1L, "A", 1990, 1L);
        Class class2 = new Class(2L, 1L, "B", 1991, 2L);
        Class class3 = new Class(3L, 1L, "C", 1992, 3L);
        Class class4 = new Class(4L, 1L, "D", 1993, 4L);
        Class class5 = new Class(5L, 1L, "SKAUCI", 1994, 1L);
        Class class6 = new Class(6L, 1L, "D", 1990, 2L);
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Object[]{class1, class2, class3, class4, class5, class6}), "C:\\__SDA_java\\_FINAL_PROJECT");
        pdfDocument.createPdfDocument();

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


//    @Test
//    public void testDate(){
//        System.out.println(PdfDocument.getCreationDate());
//    }
}
