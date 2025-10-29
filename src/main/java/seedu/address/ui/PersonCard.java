package seedu.address.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Examination;
import seedu.address.model.person.GradeMap;
import seedu.address.model.person.LabAttendance;
import seedu.address.model.person.Person;
import seedu.address.model.person.Status;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label studentId;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane exerciseStatus;
    @FXML
    private Label githubUsername;
    @FXML
    private FlowPane labAttendance;
    @FXML
    private FlowPane grades;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        studentId.setText(person.getStudentId().value);
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        email.setText(person.getEmail().value);
        List<Status> exerciseStatuses = person.getExerciseTracker().getStatuses();
        for (int i = 0; i < exerciseStatuses.size(); i++) {
            Label exerciseLabel = new Label("EX" + i);
            String statusClass = switch (exerciseStatuses.get(i)) {
            case NOT_DONE -> "exercise-not-done";
            case DONE -> "exercise-done";
            case OVERDUE -> "exercise-overdue";
            };
            exerciseLabel.getStyleClass().addAll("status-label", statusClass);
            exerciseStatus.getChildren().add(exerciseLabel);
        }

        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        githubUsername.setText(person.getGithubUsername().value);
        LabAttendance[] labs = person.getLabAttendanceList().getLabs();

        for (LabAttendance lab : labs) {
            Label labLabel = new Label("L" + lab.getLabNumber());
            String statusClass = switch (lab.getStatus()) {
            case "Y" -> "lab-attended";
            case "A" -> "lab-absent";
            default -> "lab-not-attended"; // "N"
            };
            labLabel.getStyleClass().addAll("status-label", statusClass);
            labAttendance.getChildren().add(labLabel);
        }

        HashMap<String, Examination> examMap = person.getGradeMap().getExamMap();

        for (String examName : GradeMap.VALID_EXAM_NAMES) {
            Examination exam = examMap.get(examName);
            Label gradeLabel = new Label(examName.toUpperCase());

            if (exam.isPassed().isEmpty()) {
                gradeLabel.getStyleClass().addAll("status-label", "exam-not-graded");
            } else {
                if (exam.isPassed().get()) {
                    gradeLabel.getStyleClass().addAll("status-label", "exam-pass");
                } else {
                    gradeLabel.getStyleClass().addAll("status-label", "exam-fail");
                }
            }

            grades.getChildren().add(gradeLabel);
        }

    }
}
