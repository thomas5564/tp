package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.InvalidExamNameException;

/**
 * Unit tests for the GradeMap class.
 */
public class GradeMapTest {

    private GradeMap gradeMap;

    @BeforeEach
    public void setUp() {
        gradeMap = new GradeMap();
    }

    @Test
    public void constructor_initializesAllValidExams() {
        for (String examName : GradeMap.VALID_EXAM_NAMES) {
            assertTrue(gradeMap.getExamMap().containsKey(examName));
        }
    }

    @Test
    public void markExamPassed_validExam_marksAsPassed() throws InvalidExamNameException {
        gradeMap.markExamPassed("pe1");

        Examination exam = gradeMap.getExamMap().get("pe1");

        assertTrue(exam.isPassed().isPresent());
        assertTrue(exam.isPassed().get());
    }

    @Test
    public void markExamFailed_validExam_marksAsFailed() throws InvalidExamNameException {
        gradeMap.markExamFailed("midterm");

        Examination exam = gradeMap.getExamMap().get("midterm");

        assertTrue(exam.isPassed().isPresent());
        assertFalse(exam.isPassed().get());
    }

    @Test
    public void markExamPassed_invalidExam_throwsException() {
        assertThrows(
                InvalidExamNameException.class, () -> gradeMap.markExamPassed("quiz1")
        );
    }

    @Test
    public void markExamFailed_invalidExam_throwsException() {
        assertThrows(
                InvalidExamNameException.class, () -> gradeMap.markExamFailed("unknown")
        );
    }

    @Test
    public void toString_reflectsPassFailStatus() throws InvalidExamNameException {
        gradeMap.markExamPassed("pe1");
        gradeMap.markExamFailed("pe2");

        String result = gradeMap.toString();

        assertTrue(result.contains("pe1: Passed"));
        assertTrue(result.contains("pe2: Failed"));
    }

    @Test
    public void copy_createsDeepCopyWithSameStatus() throws InvalidExamNameException {
        gradeMap.markExamPassed("pe1");
        gradeMap.markExamFailed("final");

        GradeMap copied = gradeMap.copy();

        for (String examName : GradeMap.VALID_EXAM_NAMES) {
            Examination original = gradeMap.getExamMap().get(examName);
            Examination clone = copied.getExamMap().get(examName);

            if (original.isPassed().isPresent()) {
                assertEquals(original.isPassed().get(), clone.isPassed().get());
            } else {
                assertTrue(clone.isPassed().isEmpty());
            }
        }

        // Ensure modifying the copy doesn't affect the original
        copied.markExamPassed("midterm");
        assertNotEquals(
                gradeMap.getExamMap().get("midterm").isPassed(),
                copied.getExamMap().get("midterm").isPassed()
        );
    }

    @Test
    public void equals_sameContent_returnsTrue() throws InvalidExamNameException {
        GradeMap another = new GradeMap();

        gradeMap.markExamPassed("pe1");
        another.markExamPassed("pe1");

        assertEquals(gradeMap, another);
    }

    @Test
    public void equals_differentContent_returnsFalse() throws InvalidExamNameException {
        GradeMap another = new GradeMap();

        gradeMap.markExamPassed("pe1");
        another.markExamFailed("pe1");

        assertNotEquals(gradeMap, another);
    }
}
