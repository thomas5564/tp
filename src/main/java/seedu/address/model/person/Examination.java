package seedu.address.model.person;

import java.util.Optional;

/**
 * Represents a graded examination that stores only pass/fail.
 */
public class Examination {
    private final String name;
    private Optional<Boolean> passed;

    /**
     * Creates an Examination with the given name and no result yet.
     *
     * @param name the name of the examination
     */
    public Examination(String name) {
        this.name = name;
        this.passed = Optional.empty(); // initially no result
    }

    /**
     * Marks the exam as passed.
     */
    public void markPassed() {
        this.passed = Optional.of(true);
    }

    /**
     * Marks the exam as failed.
     */
    public void markFailed() {
        this.passed = Optional.of(false);
    }

    /**
     * Returns whether the exam was passed (Optional.empty() if not graded yet).
     */
    public Optional<Boolean> isPassed() {
        return passed;
    }

    /**
     * Returns a string representation, e.g. "PE1: Passed", "Final: Failed", or "Midterm: NA".
     */
    @Override
    public String toString() {
        return String.format("%s: %s", name, passed.map(p -> {
            if (p) {
                return "Passed";
            } else {
                return "Failed";
            }
        }).orElse("NA"));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Examination)) {
            return false;
        }

        Examination other = (Examination) obj;

        if (!this.name.equals(other.name)) {
            return false;
        }

        return this.passed.equals(other.passed);
    }

    public String getName() {
        return name;
    }
}
