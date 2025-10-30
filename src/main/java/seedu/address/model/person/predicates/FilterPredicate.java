package seedu.address.model.person.predicates;

import seedu.address.model.person.Person;

import java.util.Objects;
import java.util.function.Predicate;

public interface FilterPredicate extends Predicate<Person> {
    String successMessage();

    default FilterPredicate and(FilterPredicate other) {
        Objects.requireNonNull(other);
        FilterPredicate self = this;
        return new FilterPredicate() {
            @Override public boolean test(Person t) {
                return self.test(t) && other.test(t);
            }
            @Override public String successMessage() {
                return self.successMessage() + " and " + other.successMessage();
            }
        };
    }

}
