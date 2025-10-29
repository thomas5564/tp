package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import seedu.address.commons.core.index.MultiIndex;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.InvalidIndexException;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new GradeCommand object.
 * Expected format:
 *   grade 1:3 n/pe1 s/y
 */
public class GradeCommandParser implements Parser<GradeCommand> {

    public static final String MESSAGE_INVALID_EXAM_NAME_FORMAT =
            "%s is an invalid exam name. Exam name must be one of %s";
    private static final String EMPTY_PREFIX_FORMAT = "Prefix %s : has empty value!";

    @Override
    public GradeCommand parse(String userInput) throws ParseException {
        requireNonNull(userInput);
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_EXAM_NAME, PREFIX_STATUS);

        MultiIndex studentIndex;
        String examName;
        String status;
        boolean isPassed;

        try {
            studentIndex = ParserUtil.parseMultiIndex(argumentMultimap.getPreamble());
        } catch (InvalidIndexException iie) {
            throw new ParseException("Student " + iie.getMessage());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE),
                    pe
            );
        }
        isPassed = ParserUtil.parseStatus(argumentMultimap.getValue(PREFIX_STATUS).orElse(""));

        try {
            examName = argumentMultimap.getValue(PREFIX_EXAM_NAME).orElseThrow(()
                    -> new ParseException(String.format(EMPTY_PREFIX_FORMAT, PREFIX_EXAM_NAME)));

        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, GradeCommand.MESSAGE_USAGE),
                    ive
            );
        }

        return new GradeCommand(studentIndex, examName, isPassed);
    }
}
