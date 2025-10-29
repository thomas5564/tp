package seedu.address.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;

import seedu.address.model.person.Examination;
import seedu.address.model.person.GradeMap;

/**
 * Jackson-friendly version of {@link GradeMap}.
 */
public class JsonAdaptedGradeMap {

    private Map<String, JsonAdaptedExamination> assessments = new HashMap<>();

    @JsonCreator
    public JsonAdaptedGradeMap() {

    }

    /**
     * Constructs a {@code JsonAdaptedGradeMap} from the given source {@code GradeMap}.
     */
    public JsonAdaptedGradeMap(GradeMap source) {
        this.assessments = source.getExamMap().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new JsonAdaptedExamination(entry.getValue())
                ));
    }

    /**
     * Converts this JSON-adapted grade map into the model's {@code GradeMap} object.
     */
    public GradeMap toModelType() {
        GradeMap modelMap = new GradeMap();

        assessments.forEach((key, adaptedExam) -> {
            Examination exam = adaptedExam.toModelType();
            modelMap.putExam(key, exam);
        });

        return modelMap;
    }

    public Map<String, JsonAdaptedExamination> getAssessments() {
        return assessments;
    }

    public void setAssessments(Map<String, JsonAdaptedExamination> assessments) {
        this.assessments = assessments;
    }
}
