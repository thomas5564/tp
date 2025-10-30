package seedu.address.model.person.predicates.findpredicates;

import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class FindPredicate implements Predicate<Person> {

    public abstract List<String> getKeywords();

    public abstract String successMessage();


}
