package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXAM_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.MultiIndex;
import seedu.address.logic.commands.GradeCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Unit tests for {@link GradeCommandParser}.
 */
public class GradeCommandParserTest {

    private GradeCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new GradeCommandParser();
    }

    @Test
    public void parse_validInputSingleIndexPassed_success() throws Exception {
        String userInput = "1 " + PREFIX_EXAM_NAME + "pe1 " + PREFIX_STATUS + "y";
        GradeCommand command = parser.parse(userInput);

        MultiIndex expectedIndex = new MultiIndex(Index.fromOneBased(1));
        GradeCommand expectedCommand = new GradeCommand(expectedIndex, "pe1", true);

        assertEquals(expectedCommand, command);
    }


    @Test
    public void parse_validInputRangeFailed_success() throws Exception {
        String userInput = "2:4 " + PREFIX_EXAM_NAME + "midterm " + PREFIX_STATUS + "n";
        GradeCommand command = parser.parse(userInput);

        MultiIndex expectedIndex = new MultiIndex(Index.fromOneBased(2), Index.fromOneBased(4));
        GradeCommand expectedCommand = new GradeCommand(expectedIndex, "midterm", false);

        assertEquals(expectedCommand, command);
    }

    @Test
    public void parse_missingExamPrefix_throwsParseException() {
        String userInput = "1 " + PREFIX_STATUS + "y";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_missingStatusPrefix_throwsParseException() {
        String userInput = "1 " + PREFIX_EXAM_NAME + "pe1";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidStatusCharacter_throwsParseException() {
        String userInput = "1 " + PREFIX_EXAM_NAME + "pe1 " + PREFIX_STATUS + "maybe";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        String userInput = "zero " + PREFIX_EXAM_NAME + "pe1 " + PREFIX_STATUS + "y";

        assertThrows(ParseException.class, () -> parser.parse(userInput));
    }
}
