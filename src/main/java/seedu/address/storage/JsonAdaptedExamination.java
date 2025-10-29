package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.person.Examination;

/**
 * Jackson-friendly version of {@link Examination}.
 */
public class JsonAdaptedExamination {

    private final String name;
    private final String result; // "passed", "failed", or "na"

    /**
     * Constructs JsonAdaptedExamination from the following
     * @param name of the examination
     * @param result whether the examination is passed or failed
     */
    @JsonCreator
    public JsonAdaptedExamination(@JsonProperty("name") String name,
                                  @JsonProperty("result") String result) {
        this.name = name;
        this.result = result;
    }

    /**
     * Constructs JsonAdaptedExamination from an examination
     * @param source the examination to model after
     */
    public JsonAdaptedExamination(Examination source) {
        this.name = source.getName();
        this.result = source.isPassed()
                .map(p -> p ? "passed" : "failed")
                .orElse("na");
    }

    /**
     * Converts this JSON-friendly exam back into a model {@code Examination}.
     */
    public Examination toModelType() {
        Examination exam = new Examination(name);

        if (result != null) {
            if (result.equalsIgnoreCase("passed")) {
                exam.markPassed();
            } else if (result.equalsIgnoreCase("failed")) {
                exam.markFailed();
            }
        }

        return exam;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }
}
