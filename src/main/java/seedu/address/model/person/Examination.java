package seedu.address.model.person;

import java.util.Optional;

import seedu.address.model.person.exceptions.InvalidScoreException;

/**
 * Represents a graded examination.
 */
public class Examination implements Gradeable {
    public static final double MAX_PE1_SCORE = 40.0;
    public static final double MAX_MIDTERM_SCORE = 60.0;
    public static final double MAX_PE2_SCORE = 40.0;
    public static final double MAX_FINAL_SCORE = 100.0;
    private static final String INVALID_SCORE_FORMAT =
            "%.1f is invalid as a score. Input a number from 0 to %.1f";

    private final String name;
    private final double maxScore;
    private Optional<Double> score;

    /**
     * Sets the fields accordingly.
     *
     * @param name the name of the examination
     */
    public Examination(String name) {
        this.name = name;
        this.maxScore = getMaxScoreFor(name);
        this.score = Optional.empty();
    }

    @Override
    public double getScore() {
        return score.orElse(-1.0);
    }

    @Override
    public void setScore(double inputScore) throws InvalidScoreException {
        if (inputScore < 0 || inputScore > maxScore) {
            throw new InvalidScoreException(
                    String.format(INVALID_SCORE_FORMAT, inputScore, maxScore),
                    maxScore
            );
        }
        this.score = Optional.of(inputScore / maxScore * 100.0);
    }

    @Override
    public void setPercentageScore(double score) {
        if (score < 0 || score > 100.0) {
            throw new InvalidScoreException(
                    String.format(INVALID_SCORE_FORMAT, score, 100.0),
                    100.0
            );
        }
        this.score = Optional.of(score);
    }

    /**
     * Creates and returns a deep copy of this Examination.
     * @return a new Examination instance with the same name and score (if present)
     */
    public Examination copy() {
        Examination copy = new Examination(this.name);
        this.score.ifPresent(copy::setPercentageScore);
        return copy;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", name, score.map(Object::toString).orElse("NA"));
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
        return name.equals(other.name)
                && Double.compare(maxScore, other.maxScore) == 0
                && score.equals(other.score);
    }

    public static double getMaxScoreFor(String name) {
        return switch (name.toLowerCase()) {
        case "pe1" -> MAX_PE1_SCORE;
        case "midterm" -> MAX_MIDTERM_SCORE;
        case "pe2" -> MAX_PE2_SCORE;
        case "final" -> MAX_FINAL_SCORE;
        default -> throw new IllegalArgumentException("Unknown exam: " + name);
        };
    }

    public String getName() {
        return name;
    }
}
