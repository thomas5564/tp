package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.LabAttendanceList;
import seedu.address.model.person.LabList;
import seedu.address.model.person.Person;

/**
 * Marks the specified lab as attended of an existing person in the address book.
 */
public class MarkAttendanceCommand extends Command {
    public static final String COMMAND_WORD = "marka";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks the specific lab of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_LAB_NUMBER + "[LAB NUMBER]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_LAB_NUMBER + "1";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Lab %1$d marked as attended for Student: %2$s";
    public static final String MESSAGE_FAILURE_ALREADY_ATTENDED = "Lab %1$d already marked as attended";
    public static final String MESSAGE_FAILURE_INVALID_LAB_INDEX = "The lab index provided is invalid";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Lab Number: %2$d";

    private final Index index;
    private final Index labNumber;

    /**
     * @param index of the person in the filtered person list to mark their lab attendance
     * @param index of the lab number to be marked as attended
     */
    public MarkAttendanceCommand(Index index, Index labNumber) {
        requireAllNonNull(index, labNumber);

        this.index = index;
        this.labNumber = labNumber;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        LabAttendanceList labAttendanceList = ((LabList) personToEdit.getLabAttendanceList()).copy();
        try {
            labAttendanceList.markLabAsAttended(labNumber.getZeroBased());
        } catch (IndexOutOfBoundsException iob) {
            throw new CommandException(MESSAGE_FAILURE_INVALID_LAB_INDEX);
        } catch (IllegalStateException ise) {
            throw new CommandException(String.format(MESSAGE_FAILURE_ALREADY_ATTENDED, labNumber.getOneBased()));
        }

        Person editedPerson = new Person(
                personToEdit.getStudentId(), personToEdit.getName(), personToEdit.getPhone(),
                personToEdit.getEmail(), personToEdit.getTags(),
                personToEdit.getGithubUsername(), personToEdit.getExerciseTracker(), labAttendanceList);

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand otherCommand = (MarkAttendanceCommand) other;
        return index.equals(otherCommand.index)
                && labNumber.equals(otherCommand.labNumber);
    }

    /**
     * Generates a command execution success message based on whether
     * the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        return String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS, labNumber.getOneBased(), Messages.format(personToEdit));
    }


}
