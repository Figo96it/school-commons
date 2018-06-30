package pl.sda;

import junitparams.JUnitParamsRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.model.Class;
import pl.sda.model.Parent;
import pl.sda.model.Plan;
import pl.sda.model.School;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.String.format;
import static java.time.LocalDate.now;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static junit.framework.TestCase.assertTrue;
import static pl.sda.eksporter.CsvDocument.write;

@RunWith(JUnitParamsRunner.class)
public class CsvDocumentTest {
    private String PATH = ".";

    private boolean checkIfExists(String filePathString) {
        File f = new File(filePathString);
        return f.exists() && !f.isDirectory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkEmptyList() throws IOException {
        write(new ArrayList<>(), PATH);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNullValue() throws IOException {
        write(null, PATH);
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
        assertTrue(write(asList(parent1, parent2), PATH));
        assertTrue(checkIfExists(format("Parent_dump_%s.csv", now().toString())));
    }

    @Test
    public void checkSavingClass() throws IOException {
        Class class1 = new Class(1, 1, "A", new Date(1990), 1);
        Class class2 = new Class(2, 1, "B", new Date(1991), 2);
        Class class3 = new Class(3, 1, "C", new Date(1992), 3);
        Class class4 = new Class(4, 1, "D", new Date(1993), 4);
        Class class5 = new Class(5, 1, "SKAUCI", new Date(1994), 1);
        Class class6 = new Class(6, 1, "D", new Date(1990), 2);
        assertTrue(write(asList(class1, class2, class3, class4, class5, class6), PATH));
        assertTrue(checkIfExists(format("Class_dump_%s.csv", now().toString())));
    }

    @Test
    public void checkSavingPlan() throws IOException {
        Plan plan1 = new Plan(1, "a", 1990);
        Plan plan2 = new Plan(2, "b", 1990);
        Plan plan3 = new Plan(3, "c", 1990);
        Plan plan4 = new Plan(4, "d", 1990);
        Plan plan5 = new Plan(5, "e", 1990);
        Plan plan6 = new Plan(6, "f", 1990);
        assertTrue(write(asList(plan1, plan2, plan3, plan4, plan5, plan6), PATH));
        assertTrue(checkIfExists(format("Plan_dump_%s.csv", now().toString())));
    }

    @Test
    public void checkSavingSchool() throws IOException {
        School school = new School();
        school.setAddress("mickiewicza 1");
        school.setClassId(1);
        school.setId(1);
        school.setName("SUPER SZKOLA");
        assertTrue(write(singletonList(school), PATH));
        assertTrue(checkIfExists(format("School_dump_%s.csv", now().toString())));
    }

    @Test
    public void checkIfFirstElementEmpty() throws IOException {
        Parent parent2 = new Parent();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        assertTrue(write(asList(null, parent2), PATH));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfWholeListEmpty() throws IOException {
        assertTrue(write(asList(null, null), PATH));
    }
}
