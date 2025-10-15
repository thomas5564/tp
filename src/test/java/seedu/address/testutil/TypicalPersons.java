package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_USERNAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_USERNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.ExerciseTracker;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Person ALICE = new PersonBuilder().withStudentId("A1231230X")
            .withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .withGithubUsername("Alice")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person BENSON = new PersonBuilder().withStudentId("A1231231X")
            .withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withGithubUsername("Benson")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .withTags("owesMoney", "friends").build();

    public static final Person CARL = new PersonBuilder().withStudentId("A1231232X")
            .withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com")
            .withGithubUsername("Carl")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person DANIEL = new PersonBuilder().withStudentId("A1231233X")
            .withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withGithubUsername("Daniel").withTags("friends")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person ELLE = new PersonBuilder().withStudentId("A1231234X")
            .withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com")
            .withGithubUsername("Elle")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person FIONA = new PersonBuilder().withStudentId("A1231235X")
            .withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com")
            .withGithubUsername("Fiona")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person GEORGE = new PersonBuilder().withStudentId("A1231236X")
            .withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com")
            .withGithubUsername("George")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    // Manually added
    public static final Person HOON = new PersonBuilder().withStudentId("A1231237X")
            .withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person IDA = new PersonBuilder().withStudentId("A1231238X")
            .withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com")
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withStudentId("A1231239X")
            .withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND)
            .withGithubUsername(VALID_GITHUB_USERNAME_AMY)
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final Person BOB = new PersonBuilder().withStudentId("A1231240X")
            .withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .withGithubUsername(VALID_GITHUB_USERNAME_BOB)
            .withLabAttendanceList(new LabList().toString())
            .withExerciseTracker(new ExerciseTracker().toString())
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
