package seedu.address.model.person;

/**
 * Represents a collection of lab attendance records for a student across all lab sessions.
 */
public class LabList implements LabAttendanceList {
    public static final int NUMBER_OF_LABS = 10;
    public static final String MESSAGE_CONSTRAINTS =
            "Lab attendance list should be in the format 'L1: Y/N ... L10: Y/N'";
    private final LabAttendance[] labs;

    /**
     * Constructs a {@code LabList} with all labs initialized to not attended.
     */
    public LabList() {
        this(createDefaultLabs());
    }

    /**
     * Constructs a {@code LabAttendanceList} with the specified lab attendance records.
     *
     * @param labs an array of {@code Lab} objects representing the lab attendance records
     */
    public LabList(LabAttendance[] labs) {
        assert(labs.length == NUMBER_OF_LABS);
        this.labs = labs;
    }

    private static LabAttendance[] createDefaultLabs() {
        LabAttendance[] labAttendanceList = new LabAttendance[NUMBER_OF_LABS];
        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            labAttendanceList[i] = new Lab(i + 1);
        }
        return labAttendanceList;
    }

    /**
     * Marks the specified lab session as attended.
     * @param index the zero-based index of the lab session.
     */
    @Override
    public void markLabAsAttended(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= NUMBER_OF_LABS) {
            throw new IndexOutOfBoundsException("Index should be between 0 and " + (NUMBER_OF_LABS - 1));
        }
        labs[index].markAsAttended();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof LabList)) {
            return false;
        }

        LabList otherLabList = (LabList) other;
        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            if (this.labs[i].isAttended() != otherLabList.labs[i].isAttended()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Creates and returns a copy of this LabList with all the same attendance states.
     *
     * @return a new LabList with copied attendance records
     */
    public LabList copy() {
        LabAttendance[] copiedLabs = new LabAttendance[NUMBER_OF_LABS];
        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            Lab originalLab = (Lab) this.labs[i];
            Lab newLab = new Lab(i + 1);
            if (originalLab.isAttended()) {
                newLab.markAsAttended();
            }
            copiedLabs[i] = newLab;
        }
        return new LabList(copiedLabs);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            result.append(labs[i].toString()).append(" ");
        }
        return result.toString();
    }

    /**
     * Returns true if a given string is a valid lab attendance list format.
     */
    public static boolean isValidLabList(String labListString) {
        if (labListString == null) {
            return false;
        }

        String trimmed = labListString.trim();
        String[] parts = trimmed.split("\\s+");

        // Each lab would have two parts (eg L1, Y)
        if (parts.length != NUMBER_OF_LABS * 2) {
            return false;
        }

        for (int i = 0; i < NUMBER_OF_LABS; i++) {
            String labLabel = parts[i * 2];
            String status = parts[i * 2 + 1];

            if (!labLabel.equals("L" + (i + 1) + ":")) {
                return false;
            }

            if (!status.equals("Y") && !status.equals("N")) {
                return false;
            }
        }
        return true;
    }
}
