package pl.sda;

import org.junit.Test;
import pl.sda.eksporter.PdfDocument;
import pl.sda.model.Parents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class PdfDocumentTest {
    private final String PATH = ".";

    @Test
    public void runTest(){

        Parents parent1 = new Parents();
        parent1.setFirstName("jacek");
        parent1.setSurname("Piekara");
        parent1.setMail("mail@mail.pl");
        Parents parent2 = new Parents();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Object[]{parent1, parent2}), "C:\\__SDA_java\\_FINAL_PROJECT");
        pdfDocument.createPdfDocument();
    }


    @Test
    public void columnNamesTest(){
        Parents parent1 = new Parents();
        PdfDocument pdfDocument = new PdfDocument(Arrays.asList(new Object[]{parent1}), PATH);
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
