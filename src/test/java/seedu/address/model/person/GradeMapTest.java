package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.InvalidExamNameException;

class GradeMapTest {

    private GradeMap gradeMap;

    @BeforeEach
    void setUp() {
        gradeMap = new GradeMap();
    }

    @Test
    void markExamPassed_validExam_marksAsPassed() throws InvalidExamNameException {
        // Given
        String validExamName = "pe1";

        // When
        gradeMap.markExamPassed(validExamName);

        // Then
        Examination exam = gradeMap.getExamMap().get(validExamName);
        assertTrue(exam.isPassed().isPresent(), "Exam should have a pass/fail status");
        assertTrue(exam.isPassed().get(), "Exam should be marked as passed");
    }

    @Test
    void markExamPassed_invalidExam_throwsInvalidExamNameException() {
        // Given
        String invalidExamName = "quiz1";

        // When + Then
        InvalidExamNameException exception = assertThrows(
                InvalidExamNameException.class, ()
                        -> gradeMap.markExamPassed(invalidExamName)
        );

        assertTrue(
                exception.getMessage().contains("Exam name is invalid!"),
                "Exception message should indicate invalid exam name"
        );
    }
}
