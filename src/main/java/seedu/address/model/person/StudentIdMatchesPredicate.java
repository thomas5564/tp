package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code StudentId} matches the given student ID.
 */
public class StudentIdMatchesPredicate implements Predicate<Person> {
    private final StudentId studentId;

    public StudentIdMatchesPredicate(StudentId studentId) {
        this.studentId = studentId;
    }

    @Override
    public boolean test(Person person) {
        return person.getStudentId().equals(studentId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof StudentIdMatchesPredicate
                && studentId.equals(((StudentIdMatchesPredicate) other).studentId));
    }
}
