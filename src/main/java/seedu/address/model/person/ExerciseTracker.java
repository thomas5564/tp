package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;

/**
 * Represents a Person's exercise tracker in the address book.
 * Tracks completion status for a fixed number of exercises.
 */
public class ExerciseTracker implements Comparable<ExerciseTracker> {

    public static final String MESSAGE_CONSTRAINTS = "Exercise tracker takes in statuses";
    public static final int NUMBER_OF_EXERCISES = 10;

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private static final String INDEX_OUT_OF_BOUNDS_FORMAT = "Index should be between 0 and %s";

    private static int currentWeekNumber;
    private final ArrayList<Exercise> exercises;

    // ===========================
    // Constructors
    // ===========================

    /**
     * Initializes all exercises as not done.
     */
    public ExerciseTracker() {
        this.exercises = initializeExercises(new ArrayList<>());
        assert exercises.size() == NUMBER_OF_EXERCISES : "Exercise tracker must have exactly 10 exercises";
    }

    /**
     * Initializes exercises using a provided list of completion statuses.
     * Each index corresponds to an exercise number.
     */
    public ExerciseTracker(ArrayList<Boolean> isDoneList) {
        requireNonNull(isDoneList);
        if (isDoneList.size() > NUMBER_OF_EXERCISES) {
            throw new IllegalArgumentException("Too many statuses! Expected at most " + NUMBER_OF_EXERCISES);
        }
        this.exercises = initializeExercises(isDoneList);
    }

    private ArrayList<Exercise> initializeExercises(List<Boolean> isDoneList) {
        ArrayList<Exercise> list = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            boolean done = (i < isDoneList.size()) && isDoneList.get(i);
            list.add(new Exercise(i, done, currentWeekNumber));
        }
        return list;
    }

    // ===========================
    // Setters and Getters
    // ===========================

    public static void setCurrentWeek(int week) {
        currentWeekNumber = week;
    }

    public ArrayList<Boolean> getIsDoneList() {
        assert exercises != null && !exercises.isEmpty() : "Exercises must be initialized";
        return exercises.stream()
                .map(Exercise::isDone)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<Status> getStatuses() {
        return exercises.stream().map(Exercise::getStatus).toList();
    }

    /**
     * Marks the exercise at the given index with the specified status.
     */
    public void markExercise(Index index, boolean isDone) {
        requireNonNull(index);
        logger.info(String.format("Marking exercise %d as %s", index.getOneBased(), isDone));
        validateIndex(index);
        updateExerciseStatus(index, isDone);
    }

    private void validateIndex(Index index) {
        if (index.getZeroBased() < 0 || index.getZeroBased() >= NUMBER_OF_EXERCISES) {
            throw new IndexOutOfBoundsException(
                    String.format(INDEX_OUT_OF_BOUNDS_FORMAT, NUMBER_OF_EXERCISES - 1)
            );
        }
    }

    private void updateExerciseStatus(Index index, boolean isDone) {
        exercises.get(index.getZeroBased()).markStatus(isDone);
    }

    /**
     * Calculates a student's exercise progress as a percentage (0.0–100.0).
     */
    public double calculateProgress() {
        long completedCount = exercises.stream()
                .filter(ex -> ex.getStatus() == Status.DONE)
                .count();
        return (completedCount / (double) NUMBER_OF_EXERCISES) * 100.0;
    }

    /**
     * Returns a deep copy of this ExerciseTracker.
     */
    public ExerciseTracker copy() {
        ArrayList<Boolean> copiedStatuses = new ArrayList<>(this.getIsDoneList());
        return new ExerciseTracker(copiedStatuses);
    }

    /**
     * Returns true if a given string is a valid exercise tracker format.
     */
    public static boolean isValidExerciseTracker(String exerciseTrackerString) {
        //Prevents NullPointerException and ensures we only process real strings.
        if (exerciseTrackerString == null) {
            return false;
        }
        // Split the string into parts separated by one or more whitespace characters.
        //Example:
        //"ex 0: D ex 1: N"  →  ["ex", "0:", "D", "ex", "1:", "N"]
        String[] parts = exerciseTrackerString.trim().split("\\s+");
        //check if the total number of exercise entries (e.g. ex : D) is valid
        if (parts.length != NUMBER_OF_EXERCISES * 3) {
            return false;
        }
        //checks each exercise entry individually
        for (int i = 0; i < NUMBER_OF_EXERCISES; i++) {
            if (!isValidExerciseEntry(parts, i)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidExerciseEntry(String[] parts, int index) {
        return isValidKeyword(parts[index * 3])
                && isValidIndexFormat(parts[index * 3 + 1], index)
                && isValidStatus(parts[index * 3 + 2]);
    }

    private static boolean isValidKeyword(String keyword) {
        return keyword.equals("ex");
    }

    private static boolean isValidIndexFormat(String token, int index) {
        return token.equals(index + ":");
    }

    private static boolean isValidStatus(String status) {
        return status.equals("N") || status.equals("D")
                || status.equals("I") || status.equals("O");
    }

    @Override
    public String toString() {
        return exercises.stream()
                .map(Exercise::toString)
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ExerciseTracker)) {
            return false;
        }
        ExerciseTracker otherTracker = (ExerciseTracker) other;
        return exercises.equals(otherTracker.exercises);
    }

    @Override
    public int hashCode() {
        return exercises.hashCode();
    }

    @Override
    public int compareTo(ExerciseTracker other) {
        return Double.compare(this.calculateProgress(), other.calculateProgress());
    }
}
