package pl.sda;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.eksporter.CsvDocument;
import pl.sda.model.*;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class CsvDocumentTest {
    private String PATH = ".";

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

        Classroom classroom1 = new Classroom(1, new School(), "A", new Date(1990), new Employee());
        Classroom classroom2 = new Classroom(2, new School(), "B", new Date(1991), new Employee());
        Classroom classroom3 = new Classroom(3, new School(), "C", new Date(1992), new Employee());
        Classroom classroom4 = new Classroom(4, new School(), "D", new Date(1993), new Employee());
        Classroom classroom5 = new Classroom(5, new School(), "E", new Date(1994), new Employee());
        Classroom classroom6 = new Classroom(6, new School(), "F", new Date(1995), new Employee());

        assertTrue(CsvDocument.write(Arrays.asList(classroom1, classroom2, classroom3, classroom4, classroom5, classroom6), PATH));
        assertTrue(checkIfExists(String.format("Class_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingPlan() throws IOException {
        Plan plan1 = new Plan(1, new Classroom());
        Plan plan2 = new Plan(2, new Classroom());
        Plan plan3 = new Plan(3, new Classroom());
        Plan plan4 = new Plan(4, new Classroom());
        Plan plan5 = new Plan(5, new Classroom());
        Plan plan6 = new Plan(6, new Classroom());
        assertTrue(CsvDocument.write(Arrays.asList(plan1, plan2, plan3, plan4, plan5, plan6), PATH));
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
