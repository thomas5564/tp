package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_LAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_LAB;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.LabAttendanceList;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkAttendanceTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markLab1_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        LabAttendanceList labAttendanceList = new LabList();
        labAttendanceList.markLabAsAttended(0);
        Person editedPerson = new PersonBuilder(firstPerson)
                .withLabAttendanceList(labAttendanceList.toString()).build();

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LAB);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                INDEX_FIRST_LAB.getOneBased(), Messages.format(editedPerson));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand = new MarkAttendanceCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LAB);

        // same values -> returns true
        MarkAttendanceCommand commandWithSameValues = new MarkAttendanceCommand(INDEX_FIRST_PERSON, INDEX_FIRST_LAB);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(INDEX_SECOND_PERSON, INDEX_FIRST_LAB)));

        // different remark -> returns false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(INDEX_FIRST_PERSON, INDEX_SECOND_LAB)));
    }

}
