package seedu.address.model.person;

import static seedu.address.model.person.Status.DONE;
import static seedu.address.model.person.Status.NOT_DONE;

import java.time.LocalDateTime;

/**
 * Includes exercises and labs
 */

public class Milestone {
    private Status status = NOT_DONE;
    private LocalDateTime dateTime;
    /**
     * Constructor for milestone
     * @param dateTime date and time on which milestone occurs
     */
    public Milestone(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public void markDone() {
        status = DONE;
    }
    public void unmark() {
        status = NOT_DONE;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status s) {
        status = s;
    }
}
