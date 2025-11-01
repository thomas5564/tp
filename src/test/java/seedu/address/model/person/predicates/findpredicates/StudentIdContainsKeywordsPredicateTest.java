package seedu.address.model.person.predicates.findpredicates;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class StudentIdContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("A1231234B");
        List<String> secondPredicateKeywordList = Arrays.asList("A9879876G", "A1091235L");

        StudentIdContainsKeywordsPredicate firstPredicate =
                new StudentIdContainsKeywordsPredicate(firstPredicateKeywordList);
        StudentIdContainsKeywordsPredicate secondPredicate =
                new StudentIdContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        StudentIdContainsKeywordsPredicate firstPredicateCopy =
                new StudentIdContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_studentIdContainsKeywords_returnsTrue() {
        // One keyword
        StudentIdContainsKeywordsPredicate predicate =
                new StudentIdContainsKeywordsPredicate(Collections.singletonList("a123"));
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567X").build()));

        // Multiple keywords
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("a765", "9x"));
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A7654321Z").build()));
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1231239X").build()));

        // Digits-only substring
        predicate = new StudentIdContainsKeywordsPredicate(Collections.singletonList("3456"));
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567X").build()));
    }

    @Test
    public void test_studentIdDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        StudentIdContainsKeywordsPredicate predicate =
                new StudentIdContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234567X").build()));

        // Non-matching keyword
        predicate = new StudentIdContainsKeywordsPredicate(Collections.singletonList("C000"));
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234567X").build()));

        // Keywords match other fields, but not student ID
        predicate = new StudentIdContainsKeywordsPredicate(Arrays.asList("Alice", "gmail"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@gmail.com")
                .withStudentId("A7654321Z")
                .build()));
    }

    @Test
    public void test_substringPositions_returnsExpected() {
        StudentIdContainsKeywordsPredicate predicate =
                new StudentIdContainsKeywordsPredicate(Collections.singletonList("A1234"));

        // Present return true, different positions
        assertTrue(predicate.test(new PersonBuilder().withStudentId("A1234567X").build())); // start

        predicate = new StudentIdContainsKeywordsPredicate(Collections.singletonList("7123"));

        assertTrue(predicate.test(new PersonBuilder().withStudentId("A5671234X").build())); // middle

        predicate = new StudentIdContainsKeywordsPredicate(Collections.singletonList("340F"));

        assertTrue(predicate.test(new PersonBuilder().withStudentId("A0012340F").build())); // end

        // Not present as contiguous substring return false
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A3041021F").build())); // non-contiguous
        assertFalse(predicate.test(new PersonBuilder().withStudentId("A1234056B").build())); // partial of keyword
    }

    @Test
    public void getKeywords_returnsOriginalList() {
        List<String> list = Arrays.asList("A12", "X");
        StudentIdContainsKeywordsPredicate predicate = new StudentIdContainsKeywordsPredicate(list);

        // Same contents
        assertEquals(list, predicate.getKeywords());
    }

    @Test
    public void successMessage_returnsExpected() {
        StudentIdContainsKeywordsPredicate predicate =
                new StudentIdContainsKeywordsPredicate(Collections.singletonList("A123"));

        assertEquals(" student id", predicate.successMessage());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("A12", "345");
        StudentIdContainsKeywordsPredicate predicate = new StudentIdContainsKeywordsPredicate(keywords);

        String expected = StudentIdContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
