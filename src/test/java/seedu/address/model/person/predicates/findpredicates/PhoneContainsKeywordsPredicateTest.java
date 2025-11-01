package seedu.address.model.person.predicates.findpredicates;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PhoneContainsKeywordsPredicateTest {
    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("123");
        List<String> secondPredicateKeywordList = Arrays.asList("123", "456");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneContainsKeywords_returnsTrue() {
        // One keyword
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("123"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("555", "999"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("00055511").build()));

        // Only one matching keyword among many
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("777", "888", "234"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("01234").build()));
    }

    @Test
    public void test_phoneDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("999"));
        assertFalse(predicate.test(new PersonBuilder().withPhone("12345678").build()));

        // Keywords match other fields, but not phone
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Alice", "gmail"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withEmail("amy@gmail.com")
                .withPhone("7778888")
                .build()));
    }

    @Test
    public void test_substringPositions_returnsExpected() {
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Arrays.asList("1234"));

        // Present return true, different positions
        assertTrue(predicate.test(new PersonBuilder().withPhone("1234").build()));        // exact equality
        assertTrue(predicate.test(new PersonBuilder().withPhone("123456").build()));     // start
        assertTrue(predicate.test(new PersonBuilder().withPhone("90123490").build()));    // middle
        assertTrue(predicate.test(new PersonBuilder().withPhone("991234").build()));       // end

        // Not present as contiguous substring return false
        assertFalse(predicate.test(new PersonBuilder().withPhone("1020304").build()));    // non-contiguous
        assertFalse(predicate.test(new PersonBuilder().withPhone("123").build()));         // partial of keyword
        assertFalse(predicate.test(new PersonBuilder().withPhone("234").build()));         // partial of keyword
    }

    @Test
    public void getKeywords_returnsOriginalList() {
        List<String> list = Arrays.asList("12", "34");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(list);

        // Same contents
        assertEquals(list, predicate.getKeywords());
    }

    @Test
    public void successMessage_returnsExpected() {
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("123"));
        assertEquals(" phone number", predicate.successMessage());
    }


    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("12", "34");
        PhoneContainsKeywordsPredicate predicate = new PhoneContainsKeywordsPredicate(keywords);

        String expected = PhoneContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}



