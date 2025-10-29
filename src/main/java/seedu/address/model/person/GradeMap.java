package seedu.address.model.person;

import static seedu.address.logic.parser.GradeCommandParser.MESSAGE_INVALID_EXAM_NAME_FORMAT;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.exceptions.InvalidExamNameException;

/**
 * Wraps a HashMap with String keys and Gradeable values.
 */
public class GradeMap {
    public static final String[] VALID_EXAM_NAMES = {"pe1", "midterm", "pe2", "final"};
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);
    private final HashMap<String, Gradeable> gradeableHashMap;

    /** Initializes all valid exams in the map. */
    public GradeMap() {
        gradeableHashMap = new HashMap<>();
        Arrays.stream(VALID_EXAM_NAMES)
                .forEach(name -> gradeableHashMap.put(name, new Examination(name)));
    }

    @Override
    public String toString() {
        return Arrays.stream(VALID_EXAM_NAMES)
                .map(name -> gradeableHashMap.get(name).toString())
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GradeMap other)) {
            return false;
        }
        return gradeableHashMap.equals(other.gradeableHashMap);
    }

    public HashMap<String, Gradeable> getGradeableHashMap() {
        return gradeableHashMap;
    }

    public void putExam(String key, Examination exam) {
        gradeableHashMap.put(key, exam);
    }

    /**
     * Grades an exam with a score.
     * @param name the exam name
     * @param score the score to set
     * @throws InvalidExamNameException if the exam name is invalid
     */
    public void gradeExam(String name, double score) throws InvalidExamNameException {
        logger.info(String.format("Grading %s with %.2f", name, score));

        Gradeable exam = gradeableHashMap.get(name);
        if (exam == null) {
            throw new InvalidExamNameException(String.format(
                    MESSAGE_INVALID_EXAM_NAME_FORMAT,
                    name,
                    Arrays.toString(VALID_EXAM_NAMES)
            ));
        }

        exam.setScore(score);
    }

    /**
     * Returns a deep copy of this GradeMap.
     * @return a new GradeMap with copied Examination instances
     */
    public GradeMap copy() {
        GradeMap copy = new GradeMap();

        for (String name : VALID_EXAM_NAMES) {
            Gradeable original = gradeableHashMap.get(name);
            if (original instanceof Examination exam) {
                copy.putExam(name, exam.copy());
            } else {
                copy.putExam(name, new Examination(name));
            }
        }

        return copy;
    }
}
