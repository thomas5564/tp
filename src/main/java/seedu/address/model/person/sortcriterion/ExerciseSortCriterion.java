package seedu.address.model.person.sortcriterion;

import java.util.Comparator;

import seedu.address.model.person.Person;

/**
 * Sorts students by their exercise progress in descending order.
 */
public class ExerciseSortCriterion extends SortCriterion {
    public static final String CRITERION_KEYWORD = "ex";

    @Override
    public Comparator<Person> getComparator() {
        return Comparator.comparing(Person::getExerciseTracker, Comparator.reverseOrder());
    }

    @Override
    public String getDisplayString() {
        return "exercise progress";
    }

    @Override
    public String toString() {
        return CRITERION_KEYWORD;
    }
}
