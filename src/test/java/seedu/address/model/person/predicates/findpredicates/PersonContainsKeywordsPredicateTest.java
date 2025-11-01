package seedu.address.model.person.predicates.findpredicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        // Inner predicates
        NameContainsKeywordsPredicate nameA = new NameContainsKeywordsPredicate(List.of("alice"));
        EmailContainsKeywordsPredicate emailNus = new EmailContainsKeywordsPredicate(List.of("nus.edu"));
        GithubContainsKeywordsPredicate ghName = new GithubContainsKeywordsPredicate(List.of("ghuser"));

        PersonContainsKeywordsPredicate a =
                new PersonContainsKeywordsPredicate(List.of(nameA, emailNus));
        PersonContainsKeywordsPredicate aCopy =
                new PersonContainsKeywordsPredicate(List.of(new NameContainsKeywordsPredicate(List.of("alice")),
                        new EmailContainsKeywordsPredicate(List.of("nus.edu"))));
        PersonContainsKeywordsPredicate b =
                new PersonContainsKeywordsPredicate(List.of(ghName));

        // same object -> true
        assertEquals(a, a);
        // same values -> true
        assertEquals(a, aCopy);
        // different -> false
        assertNotEquals(a, b);
        // null -> false
        assertNotEquals(a, null);
        // different type -> false
        assertNotEquals(a, "not a predicate");
    }

    @Test
    public void equals_sameElementsDifferentOrder_returnsFalse() {
        PersonContainsKeywordsPredicate firstThenSecond =
                new PersonContainsKeywordsPredicate(List.of(
                        new NameContainsKeywordsPredicate(List.of("alice")),
                        new EmailContainsKeywordsPredicate(List.of("nus.edu"))
                ));
        PersonContainsKeywordsPredicate secondThenFirst =
                new PersonContainsKeywordsPredicate(List.of(
                        new EmailContainsKeywordsPredicate(List.of("nus.edu")),
                        new NameContainsKeywordsPredicate(List.of("alice"))
                ));
        // Order matters
        assertNotEquals(firstThenSecond, secondThenFirst);
    }


    @Test
    public void test_orAcrossFieldsWithOneMatch_returnsTrue() {
        // Person with name match other fields default, all individual predicates already ttested
        Person person = new PersonBuilder()
                .withName("Alice Tan")
                .build();

        NameContainsKeywordsPredicate nameMatch =
                new NameContainsKeywordsPredicate(List.of("ali")); // matches
        EmailContainsKeywordsPredicate emailNo =
                new EmailContainsKeywordsPredicate(List.of("nus")); // default email "amy@gmail.com" no match
        GithubContainsKeywordsPredicate ghNo =
                new GithubContainsKeywordsPredicate(List.of("ghuser")); // default gh "TestUsername" no match

        PersonContainsKeywordsPredicate combined =
                new PersonContainsKeywordsPredicate(List.of(nameMatch, emailNo, ghNo));

        assertTrue(combined.test(person));
    }

    @Test
    public void test_orAcrossFieldsWithNoMatch_returnsFalse() {
        // Person with name match; other fields default
        Person person = new PersonBuilder()
                .withName("Alice Tan")
                .build();

        NameContainsKeywordsPredicate nameNo =
                new NameContainsKeywordsPredicate(List.of("bob")); // no match with Alice Tan
        EmailContainsKeywordsPredicate emailNo =
                new EmailContainsKeywordsPredicate(List.of("nus")); // default email "amy@gmail.com" no match
        GithubContainsKeywordsPredicate ghNo =
                new GithubContainsKeywordsPredicate(List.of("ghuser")); // default gh "TestUsername" no match
        PhoneContainsKeywordsPredicate phNo =
                new PhoneContainsKeywordsPredicate(List.of("98765432")); // default ph "85355255" no match

        PersonContainsKeywordsPredicate combined =
                new PersonContainsKeywordsPredicate(List.of(nameNo, emailNo, ghNo, phNo));

        assertFalse(combined.test(person));
    }

    @Test
    public void successMessage_withThreeFields_includesRelevantFieldLabels() {
        // Build with name + email + tag predicates
        String[] search = {"alice", "nus", "lab"};
        NameContainsKeywordsPredicate namePred = new NameContainsKeywordsPredicate(List.of(search));
        EmailContainsKeywordsPredicate emailPred = new EmailContainsKeywordsPredicate(List.of(search));
        TagContainsKeywordsPredicate tagPred = new TagContainsKeywordsPredicate(List.of(search));

        PersonContainsKeywordsPredicate combined =
                new PersonContainsKeywordsPredicate(List.of(namePred, emailPred, tagPred));

        String msg = combined.successMessage();
        // Contains the fields
        assertTrue(msg.toLowerCase().contains("name"));
        assertTrue(msg.toLowerCase().contains("email"));
        assertTrue(msg.toLowerCase().contains("tag"));

        // Contains the keywords
        assertTrue(msg.toLowerCase().contains("alice"));
        assertTrue(msg.toLowerCase().contains("nus"));
        assertTrue(msg.toLowerCase().contains("lab"));
    }

    @Test
    public void successMessage_allFields_success() {
        // Build all predicates
        NameContainsKeywordsPredicate namePred =
                new NameContainsKeywordsPredicate(List.of("alice"));
        StudentIdContainsKeywordsPredicate sidPred =
                new StudentIdContainsKeywordsPredicate(List.of("alice"));
        PhoneContainsKeywordsPredicate phonePred =
                new PhoneContainsKeywordsPredicate(List.of("alice"));
        EmailContainsKeywordsPredicate emailPred =
                new EmailContainsKeywordsPredicate(List.of("alice"));
        GithubContainsKeywordsPredicate ghPred =
                new GithubContainsKeywordsPredicate(List.of("alice"));
        TagContainsKeywordsPredicate tagPred =
                new TagContainsKeywordsPredicate(List.of("alice"));

        PersonContainsKeywordsPredicate combined =
                new PersonContainsKeywordsPredicate(
                        List.of(namePred, sidPred, phonePred, emailPred, ghPred, tagPred));

        String msg = combined.successMessage();

        assertTrue(msg.contains(" within all fields."));
        // Also contains the keywords:
        assertTrue(msg.toLowerCase().contains("alice"));
    }

    @Test
    public void successMessage_selectedFields_listsFieldNames() {
        NameContainsKeywordsPredicate namePred =
                new NameContainsKeywordsPredicate(List.of("bob"));
        EmailContainsKeywordsPredicate emailPred =
                new EmailContainsKeywordsPredicate(List.of("bob"));

        PersonContainsKeywordsPredicate combined =
                new PersonContainsKeywordsPredicate(List.of(namePred, emailPred));

        String msg = combined.successMessage();
        assertFalse(msg.contains(" within all fields.")); // not all
        assertTrue(msg.toLowerCase().contains(" within")); // has “within … fields.”
        assertTrue(msg.toLowerCase().contains(" name")); // field labels come from child successMessage()
        assertTrue(msg.toLowerCase().contains(" email"));
        assertTrue(msg.toLowerCase().contains("bob")); // shows keywords
    }


}
