package seedu.address.model.person.predicates.findpredicates;

import org.junit.jupiter.api.Test;
import seedu.address.testutil.PersonBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("lab1");
        List<String> secondPredicateKeywordList = Arrays.asList("cs2030s", "groupA");

        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy =
                new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different keywords -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_tagsContainKeywords_returnsTrue() {
        // One keyword
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("lab1"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Lab1").build()));

        // Multiple keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("project", "groupA"));
        assertTrue(predicate.test(new PersonBuilder().withTags("groupA").build()));          // matches "groupA"
        assertTrue(predicate.test(new PersonBuilder().withTags("projectX").build()));        // matches "project"

        // Substring within a tag
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("group"));
        assertTrue(predicate.test(new PersonBuilder().withTags("labGroup1").build()));

        // Mixed case across tags
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("cs2030"));
        assertTrue(predicate.test(new PersonBuilder().withTags("CS2030S", "weekly").build()));
    }

    @Test
    public void test_tagsDoNotContainKeywords_returnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("lab1").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Collections.singletonList("random"));
        assertFalse(predicate.test(new PersonBuilder().withTags("lab1", "cs2030s").build()));

        // Keywords match other fields, but not tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("alice", "nus"));
        assertFalse(predicate.test(new PersonBuilder()
                .withName("Alice")
                .withEmail("alice@nus.edu.sg")
                .withTags("groupB")
                .build()));
    }


    @Test
    public void getKeywords_returnsOriginalList() {
        List<String> list = Arrays.asList("lab", "proj");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(list);

        // Same contents
        assertEquals(list, predicate.getKeywords());
    }

    @Test
    public void successMessage_returnsExpected() {
        TagContainsKeywordsPredicate predicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("lab"));
        assertEquals(" tags", predicate.successMessage());
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("cs2030s", "lab1");
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(keywords);

        String expected = TagContainsKeywordsPredicate.class.getCanonicalName()
                + "{keywords=" + keywords + "}";
        assertEquals(expected, predicate.toString());
    }
}
