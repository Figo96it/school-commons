package pl.sda;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.sda.credentials.Password;
import pl.sda.eksporter.CsvDocument;
import pl.sda.model.Class;
import pl.sda.model.Parents;
import pl.sda.model.Plan;
import pl.sda.model.School;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(JUnitParamsRunner.class)
public class CsvDocumentTest {

     @Test(expected = IllegalArgumentException.class)
    public void checkEmptyList() throws IOException {
         CsvDocument.write(new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkNullValue() throws IOException {
        CsvDocument.write(null);
    }

    @Test
    public void checkSavingParents() throws IOException {
        Parents parent1 = new Parents();
        parent1.setFirstName("jacek");
        parent1.setSurname("Piekara");
        parent1.setMail("mail@mail.pl");
        Parents parent2 = new Parents();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        CsvDocument.write(Arrays.asList(new Object[]{parent1, parent2}));
    }
    @Test
    public void checkSavingClass() throws IOException {
         Class class1 = new Class(1L, 1L, "A", 1990, 1L);
         Class class2 = new Class(2L, 1L, "B", 1991, 2L);
         Class class3 = new Class(3L, 1L, "C", 1992, 3L);
         Class class4 = new Class(4L, 1L, "D", 1993, 4L);
         Class class5 = new Class(5L, 1L, "SKAUCI", 1994, 1L);
         Class class6 = new Class(6L, 1L, "D", 1990, 2L);
        assertTrue(CsvDocument.write(Arrays.asList(new Object[]{class1, class2, class3, class4, class5, class6})));
    }
    @Test
    public void checkSavingPlan() throws IOException {
        Plan plan1 = new Plan(1, "a", 1990);
        Plan plan2 = new Plan(2, "b", 1990);
        Plan plan3 = new Plan(3, "c", 1990);
        Plan plan4 = new Plan(4, "d", 1990);
        Plan plan5 = new Plan(5, "e", 1990);
        Plan plan6 = new Plan(6, "f", 1990);
        assertTrue(CsvDocument.write(Arrays.asList(new Object[]{plan1, plan2, plan3, plan4, plan5, plan6})));
    }
    @Test
    public void checkSavingSchool() throws IOException {
        School school = new School();
        school.setAddress("mickiewicza 1");
        school.setClassId(1);
        school.setId(1);
        school.setName("SUPER SZKOLA");
        assertTrue(CsvDocument.write(Arrays.asList(new Object[]{school})));
    }
}
