package seedu.address.model.person.predicates.findpredicates;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EmailContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("nus.edu");
        List<String> secondPredicateKeywordList = Arrays.asList("gmail", "edu");

        EmailContainsKeywordsPredicate firstPredicate =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        EmailContainsKeywordsPredicate secondPredicate =
                new EmailContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EmailContainsKeywordsPredicate firstPredicateCopy =
                new EmailContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_emailContainsKeywords_returnsTrue() {
        // One keyword
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("nus.edu"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@NUS.EDU.SG").build()));

        // Multiple keywords
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("gmail", "alice"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@school.org").build())); // "alice" matches
        assertTrue(predicate.test(new PersonBuilder().withEmail("x@gmail.com").build()));      // "gmail" matches

    }

    @Test
    public void test_emailDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@nus.edu.sg").build()));

        // Non-matching keyword
        predicate = new EmailContainsKeywordsPredicate(Collections.singletonList("yahoo"));
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@nus.edu.sg").build()));

        // Keywords match other fields, but not email
        predicate = new EmailContainsKeywordsPredicate(Arrays.asList("Alice", "github"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withGithubUsername("githubUser")
                .withEmail("xyz@jkl.abc")
                .build()));
    }

    @Test
    public void test_substringPositions_returnsExpected() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("edu.sg"));

        // Present return true, different positions
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@nus.edu.sg").build()));   // end
        assertTrue(predicate.test(new PersonBuilder().withEmail("edu.sg@domain.com").build()));  // start
        assertTrue(predicate.test(new PersonBuilder().withEmail("x@my-edu.sg-domain.com").build())); // middle

        // Not present as contiguous substring return false
        assertFalse(predicate.test(new PersonBuilder().withEmail("alice@edusg.com").build()));   // missing dot
    }

    @Test
    public void getKeywords_returnsOriginalList() {
        List<String> list = Arrays.asList("nus", "edu");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(list);

        // Same contents
        assertEquals(list, predicate.getKeywords());
    }

    @Test
    public void successMessage_returnsExpected() {
        EmailContainsKeywordsPredicate predicate =
                new EmailContainsKeywordsPredicate(Collections.singletonList("gmail"));
        assertEquals(" email", predicate.successMessage());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("nus", "edu");
        EmailContainsKeywordsPredicate predicate = new EmailContainsKeywordsPredicate(keywords);

        String expected = EmailContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
