package seedu.address.model.person;

import java.util.ArrayList;

/**
 * List containing milestones
 * @param <T> Milestone
 */
public class MilestoneList<T extends Milestone> {
    private ArrayList<T> milestones;
    public void markDone(int index) {
        milestones.get(index).markDone();
    }
    public void unmark(int index) {
        milestones.get(index).unmark();
    }
    public Status getStatus(int index) {
        return milestones.get(index).getStatus();
    }
}
