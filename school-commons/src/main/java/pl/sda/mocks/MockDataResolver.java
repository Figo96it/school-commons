package pl.sda.mocks;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import pl.sda.model.Parent;

import java.util.ArrayList;
import java.util.List;

public class MockDataResolver {

    private static final int NUMBER_OF_RECORDS = 100;
    private static List<Parent> parentList = new ArrayList<>();

    private static List<Parent> getListOfFakeDataParents(int numberOfParents) {
        for (int i = 0; i < numberOfParents; i++) {
            Fairy fairy = Fairy.create();
            Person person = fairy.person();
            Parent parent = new Parent(i + 1, person.getLastName(),
                    person.getFirstName(), String.valueOf(i + 1), person.getTelephoneNumber(),
                    person.getTelephoneNumber(), person.getEmail());
            parentList.add(parent);
        }
        return parentList;
    }

    public static List<Parent> findAllParents() {
        return getListOfFakeDataParents(NUMBER_OF_RECORDS);
    }

    public static void main(String[] args) {
        System.out.println(getListOfFakeDataParents(10));
    }
}
