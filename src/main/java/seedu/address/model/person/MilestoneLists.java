package seedu.address.model.person;

import java.time.LocalDateTime;

/**
 *
 */
public class MilestoneLists {
    private static ExerciseList exerciseList;
    private static LabList labList;
    static {
        exerciseList = new ExerciseList(
                1,
                10,
                LocalDateTime.parse("2025-01-14T10:00"));
        labList = new LabList(
                1,
                10,
                LocalDateTime.parse("2025-01-14T10:00")
        );
    }
    public static LabList getLabList() {
        return labList;
    }
    public static ExerciseList getExerciseList() {
        return exerciseList;
    }
}
