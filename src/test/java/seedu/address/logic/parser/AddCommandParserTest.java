package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_USERNAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GITHUB_USERNAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_GITHUB_USERNAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENTID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENTID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_USERNAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB_USERNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.person.Email;
import seedu.address.model.person.GithubUsername;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.StudentId;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + STUDENTID_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB + TAG_DESC_FRIEND
                + GITHUB_USERNAME_DESC_BOB, new AddCommand(expectedPerson));

        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB;

        // multiple student ids
        assertParseFailure(parser, STUDENTID_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple GitHub usernames
        assertParseFailure(parser, GITHUB_USERNAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GITHUB_USERNAME));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + NAME_DESC_AMY + GITHUB_USERNAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID, PREFIX_NAME,
                        PREFIX_EMAIL, PREFIX_PHONE, PREFIX_GITHUB_USERNAME));

        // invalid value followed by valid value

        // invalid student id
        assertParseFailure(parser, INVALID_STUDENTID_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid GitHub username
        assertParseFailure(parser, INVALID_GITHUB_USERNAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GITHUB_USERNAME));

        // valid value followed by invalid value

        // invalid student id
        assertParseFailure(parser, validExpectedPersonString + INVALID_STUDENTID_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STUDENTID));

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, STUDENTID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY
                        + EMAIL_DESC_AMY + GITHUB_USERNAME_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing student id prefix
        assertParseFailure(parser, VALID_STUDENTID_BOB + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_USERNAME_DESC_BOB,
                expectedMessage);

        // missing name prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + VALID_NAME_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_USERNAME_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB
                        + VALID_PHONE_BOB + EMAIL_DESC_BOB + GITHUB_USERNAME_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB
                        + PHONE_DESC_BOB + VALID_EMAIL_BOB + GITHUB_USERNAME_DESC_BOB,
                expectedMessage);


        // missing GitHub username prefix
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                         + VALID_GITHUB_USERNAME_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_STUDENTID_BOB + VALID_NAME_BOB
                        + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_GITHUB_USERNAME_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid student id
        assertParseFailure(parser, INVALID_STUDENTID_DESC + NAME_DESC_BOB
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB, StudentId.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_NAME_DESC
                + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB
                + INVALID_PHONE_DESC + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB
                + PHONE_DESC_BOB + INVALID_EMAIL_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND + GITHUB_USERNAME_DESC_BOB, Tag.MESSAGE_CONSTRAINTS);

        // invalid GitHub username
        assertParseFailure(parser, STUDENTID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND
                + INVALID_GITHUB_USERNAME_DESC, GithubUsername.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, STUDENTID_DESC_BOB + INVALID_NAME_DESC
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB + GITHUB_USERNAME_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + STUDENTID_DESC_BOB + NAME_DESC_BOB
                        + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND + GITHUB_USERNAME_DESC_BOB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
