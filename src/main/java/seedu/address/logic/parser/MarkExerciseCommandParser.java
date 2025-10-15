package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXERCISE_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.MarkExerciseCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

/**
 * Parser of the mark exercise command
 */
public class MarkExerciseCommandParser implements Parser<MarkExerciseCommand> {
    public static final String INVALID_STATUS_FORMAT = "Invalid status. Must be one of: ";
    private static final String EMPTY_PREFIX_FORMAT = "Prefix %s : has empty value!";

    /**
     * Parse the user input into a mark exercise command
     * @param args user input
     * @return MarkExerciseCommand
     * @throws ParseException if user input is invalid
     */
    public MarkExerciseCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_EXERCISE_INDEX, PREFIX_STATUS);
        Index personIndex;
        Index exerciseIndex;
        Status status;
        String statusString;

        // Parse the person index (from the command preamble)
        try {
            personIndex = ParserUtil.parseIndex(argMultimap.getPreamble());
            exerciseIndex = ParserUtil.parseZeroBasedIndex(
                    argMultimap.getValue(PREFIX_EXERCISE_INDEX).orElseThrow(() -> new ParseException(
                            String.format(EMPTY_PREFIX_FORMAT, PREFIX_STATUS)
                    ))
            );
            statusString = argMultimap.getValue(PREFIX_STATUS).orElseThrow(() -> new ParseException(
                    String.format(EMPTY_PREFIX_FORMAT, PREFIX_STATUS)
            ));
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MarkExerciseCommand.MESSAGE_USAGE), ive);
        }
        try {
            status = ParserUtil.parseStatus(statusString.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(INVALID_STATUS_FORMAT
                    + Arrays.toString(Status.values()));
        }

        // Return new command
        return new MarkExerciseCommand(status, personIndex, exerciseIndex);
    }
}

