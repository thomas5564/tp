package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 * Class representing milestone
 */
public class Exercise extends Milestone {
    /**
     * Constructor for milestone
     *
     * @param dateTime date and time on which milestone occurs
     */
    public Exercise(LocalDateTime dateTime) {
        super(dateTime);
    }
}
