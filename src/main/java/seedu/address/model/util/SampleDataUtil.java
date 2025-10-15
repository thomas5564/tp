package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Email;
import seedu.address.model.person.ExerciseTracker;
import seedu.address.model.person.GithubUsername;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new StudentId("A1231230B"), new Name("Alex Yeoh"), new Phone("87438807"),
                    new Email("alexyeoh@example.com"),
                    getTagSet("friends"),
                    new GithubUsername("AlexYeoh"),
                    new ExerciseTracker(), new LabList()),
            new Person(new StudentId("A1231231B"), new Name("Bernice Yu"), new Phone("99272758"),
                    new Email("berniceyu@example.com"),
                    getTagSet("colleagues", "friends"),
                    new GithubUsername("BerniceYu"),
                    new ExerciseTracker(), new LabList()),
            new Person(new StudentId("A1231232B"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                    new Email("charlotte@example.com"),
                    getTagSet("neighbours"),
                    new GithubUsername("CharlotteOliveiro"),
                    new ExerciseTracker(), new LabList()),
            new Person(new StudentId("A1231233B"), new Name("David Li"), new Phone("91031282"),
                    new Email("lidavid@example.com"),
                    getTagSet("family"),
                    new GithubUsername("DavidLi"),
                    new ExerciseTracker(), new LabList()),
            new Person(new StudentId("A1231234B"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                    new Email("irfan@example.com"),
                    getTagSet("classmates"),
                    new GithubUsername("IrfanIbrahim"),
                    new ExerciseTracker(), new LabList()),
            new Person(new StudentId("A1231235B"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                    new Email("royb@example.com"),
                    getTagSet("colleagues"),
                    new GithubUsername("RoyBalakrishnan"),
                    new ExerciseTracker(), new LabList())
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }
}
