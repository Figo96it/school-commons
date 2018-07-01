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
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static pl.sda.TestSharedFunctions.checkIfAnyFieldIsNull;
import static pl.sda.TestSharedFunctions.checkIfFileExists;

@RunWith(JUnitParamsRunner.class)
public class CsvDocumentTest {
    private String PATH = ".";

    @Before
    public void populateData(){
        MockDataResolver.createFakeDbDataWithRelations();
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
        List<Parent> allParents = MockDataResolver.findAllParents();
        assertFalse(checkIfAnyFieldIsNull(allParents));
        assertTrue(CsvDocument.write(allParents, PATH));
        assertTrue(checkIfFileExists(String.format("Parent_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingClass() throws IOException {
        List<Classroom> allClassrooms = MockDataResolver.findAllClassrooms();
        assertFalse(checkIfAnyFieldIsNull(allClassrooms));
        assertTrue(CsvDocument.write(allClassrooms, PATH));
        assertTrue(checkIfFileExists(String.format("Classroom_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingPlan() throws IOException {
        List<Plan> planList = MockDataResolver.getPlanList();
        assertFalse(checkIfAnyFieldIsNull(planList));
        assertTrue(CsvDocument.write(planList, PATH));
        assertTrue(checkIfFileExists(String.format("Plan_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkSavingSchool() throws IOException {
        List<School> schoolList = MockDataResolver.getSchoolList();
        assertFalse(checkIfAnyFieldIsNull(schoolList));
        assertTrue(CsvDocument.write(schoolList, PATH));
        assertTrue(checkIfFileExists(String.format("School_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test
    public void checkIfFirstElementEmpty() throws IOException {
        Parent parent1 = null;
        Parent parent2 = new Parent();
        parent2.setFirstName("anna");
        parent2.setSurname("ldas");
        parent2.setMail("ksahd");
        assertTrue(CsvDocument.write(Arrays.asList(parent1, parent2), PATH));
        assertTrue(checkIfFileExists(String.format("Parent_dump_%s.csv", LocalDate.now().toString())));
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkIfWholeListEmpty() throws IOException {
        Parent parent1 = null;
        Parent parent2 = null;
        assertTrue(CsvDocument.write(Arrays.asList(parent1, parent2), PATH));
    }
}
