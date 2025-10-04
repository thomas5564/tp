package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 * Represents lab session
 */
public class Lab extends Milestone {
    /**
     * Constructor for lab
     *
     * @param dateTime date and time on which lab occurs
     */
    public Lab(LocalDateTime dateTime) {
        super(dateTime);
    }
}
