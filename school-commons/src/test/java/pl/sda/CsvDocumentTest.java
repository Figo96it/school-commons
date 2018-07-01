package pl.sda;

import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.eksporter.CsvDocument;
import pl.sda.mocks.MockDataResolver;
import pl.sda.model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class CsvDocumentTest {
    private String PATH = ".";

    @Before
    public void populateData(){
        MockDataResolver.createFakeDbDataWithRelations();
    }


    private boolean checkIfExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkEmptyList() throws IOException {
        CsvDocument.write(new ArrayList<>(), PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNullValue() throws IOException {
        CsvDocument.write(null, PATH);
    }

    @Test
    public void checkSavingParents() throws IOException {
        Parent parent1 = new Parent();
        parent1.setFirstName("jacek");
        parent1.setSurname("Piekara");
        parent1.setMail("mail@mail.pl");
        Parent parent2 = new Parent();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        assertTrue(CsvDocument.write(Arrays.asList(parent1, parent2), PATH));
        assertTrue(checkIfExists(String.format("Parent_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingClass() throws IOException {
        assertTrue(CsvDocument.write(MockDataResolver.findAllClassrooms(), PATH));
        assertTrue(checkIfExists(String.format("Classroom_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingPlan() throws IOException {
        assertTrue(CsvDocument.write(MockDataResolver.getPlanList(), PATH));
        assertTrue(checkIfExists(String.format("Plan_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingSchool() throws IOException {
        School school = new School();
        school.setAddress("mickiewicza 1");
        school.setId(1);
        school.setName("SUPER SZKOLA");
        assertTrue(CsvDocument.write(singletonList(school), PATH));
        assertTrue(checkIfExists(String.format("School_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkIfFirstElementEmpty() throws IOException {
        Parent parent1 = null;
        Parent parent2 = new Parent();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        assertTrue(CsvDocument.write(Arrays.asList(parent1, parent2), PATH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfWholeListEmpty() throws IOException {
        Parent parent1 = null;
        Parent parent2 = null;
        assertTrue(CsvDocument.write(Arrays.asList(parent1, parent2), PATH));
    }
}
