package seedu.address.model.person.predicates.findpredicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class GithubContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("dev");
        List<String> secondPredicateKeywordList = Arrays.asList("git", "hub");

        GithubContainsKeywordsPredicate firstPredicate =
                new GithubContainsKeywordsPredicate(firstPredicateKeywordList);
        GithubContainsKeywordsPredicate secondPredicate =
                new GithubContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        GithubContainsKeywordsPredicate firstPredicateCopy =
                new GithubContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_githubContainsKeywords_returnsTrue() {
        // One keyword
        GithubContainsKeywordsPredicate predicate =
                new GithubContainsKeywordsPredicate(Collections.singletonList("dev"));
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("teamDev123").build()));

        // Multiple keywords
        predicate = new GithubContainsKeywordsPredicate(Arrays.asList("alice", "hub"));
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("aliceGH").build())); // "alice" matches
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("git-hub").build())); // "hub" matches

        // Mixed characters and hyphens
        predicate = new GithubContainsKeywordsPredicate(Collections.singletonList("hub"));
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("git-test-hub-name").build()));
    }

    @Test
    public void test_githubDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        GithubContainsKeywordsPredicate predicate =
                new GithubContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withGithubUsername("testname").build()));

        // Non-matching keyword
        predicate = new GithubContainsKeywordsPredicate(Collections.singletonList("random"));
        assertFalse(predicate.test(new PersonBuilder().withGithubUsername("cs2103t").build()));

        // Keywords match other fields, but not github username
        predicate = new GithubContainsKeywordsPredicate(Arrays.asList("Alice", "gmail"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withEmail("amy@gmail.com")
                .withGithubUsername("us-er")
                .build()));
    }

    @Test
    public void test_substringPositions_returnsExpected() {
        GithubContainsKeywordsPredicate predicate =
                new GithubContainsKeywordsPredicate(Collections.singletonList("git"));

        // Present return true, different positions
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("gitname").build())); // start
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("mygitname").build())); // middle
        assertTrue(predicate.test(new PersonBuilder().withGithubUsername("iamusinggit").build())); // end

        // Not present as contiguous substring return false
        assertFalse(predicate.test(new PersonBuilder().withGithubUsername("gikt").build())); // non-contiguous
        assertFalse(predicate.test(new PersonBuilder().withGithubUsername("gi").build())); // partial
        assertFalse(predicate.test(new PersonBuilder().withGithubUsername("it").build())); // partial
    }

    @Test
    public void getKeywords_returnsOriginalList() {
        List<String> list = Arrays.asList("dev", "ops");
        GithubContainsKeywordsPredicate predicate = new GithubContainsKeywordsPredicate(list);

        // Same contents
        assertEquals(list, predicate.getKeywords());
    }

    @Test
    public void successMessage_returnsExpected() {
        GithubContainsKeywordsPredicate predicate =
                new GithubContainsKeywordsPredicate(Collections.singletonList("dev"));
        assertEquals(" github username", predicate.successMessage());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("ghuser", "hub");
        GithubContainsKeywordsPredicate predicate = new GithubContainsKeywordsPredicate(keywords);

        String expected = GithubContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
