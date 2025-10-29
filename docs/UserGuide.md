# **Command Features**

<box type="info">

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the [parameters](#parameters) to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* Accepted datetime formats for command parameters (i.e. START_DATETIME & END_DATETIME):
  * ISO_LOCAL_DATE_TIME: `2023-10-01T09:00:00`
  * Human-friendly: `d MMM uuuu, HH:mm` (e.g. `4 Oct 2025, 10:00`) or `d MMM uuuu HH:mm` (e.g. `4 Oct 2025 10:00`)

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  </box>

<br><br>

<box type="tip">

**Tip:** For any command using the INDEX parameter, you can mark multiple students at once using a range (e.g. `1:5`). This does not apply to the EXERCISE_INDEX or LAB_INDEX parameters
</box>

## **Data – Modifying**

### **Part 1: Person Data**
- Add
- Edit
- Delete
- Clear
- Undo

---
### **Part 2: Tracking milestones like labs, exercises and exams**

#### Mark a student's lab attendance: `marka`

You can use this command to record whether selected students attended or were absent for a particular lab.  

**Format:**
```
marka INDEX l/LAB_NUMBER s/STATUS
```

**Examples:**
- `marka 1 l/1 s/y` — Marks Lab 1 as *attended* for the 1st student.
- `marka 1:5 l/1 s/n` — Marks Lab 1 as *not attended* for students 1 through 5.

<box type="warning">

**Caution:** Attempting to re-mark a lab with the same status will trigger a message indicating it was already marked.
</box>

<br>

##### Mark a student's exercise for completion: `marke`

You can use this command to record or update whether selected students have completed a particular exercise.  

**Format:**
```
marke INDEX ei/EXERCISE_INDEX s/STATUS
```

**Examples:**
- `marke 1 ei/1 s/y` — Marks Exercise 1 as *done* for the 1st student.
- `marke 2:5 ei/3 s/n` — Marks Exercise 3 as *not done* for students 2 through 5.

<box type="warning">

**Caution:** If an exercise is already marked with the same status, the command will show a message indicating it was already marked.
</box>

<br>

##### Record a student's score for an exam: `grade`

You can use this command to assign or edit exam grades for selected students.  

**Format:**
```
grade INDEX... en/EXAM_NAME s/SCORE
```

**Examples:**
- `grade 1 en/Midterm s/87.5` — Records a score of 87.5 for the Midterm exam for the 1st student.
- `grade 2:4 en/Final s/90` — Assigns a score of 90 for the Final exam to students 2 through 4.

<box type="tip">

**Tip:** Scores will automatically be rounded down to one decimal place.  
</box>

<box type="warning">

**Caution:** The exam name must be one of the valid exams defined in the system. Additionally, the score must be a valid number (e.g., `85`, `92.5`) that is no greater than the maximum score of the exam you intend to mark.
</box>

| **Valid Exam Name** | **Maximum score** |
|----------------------|-------------------|
| `pe1`               | 40                |
| `midterm`           | 60                |
| `pe2`               | 40                |
| `final`             | 100               |

---

#### **Part 3: AddressBook Level Changes**
- Set-week

---

#### **Part 4: Timeslots & Consultations**
This section contains commands for managing unavailable times (blocked timeslots) and consultations. Timeslots can be used to mark regular events (e.g. classes), while consultation timeslots are specifically for CS2030S consultation sessions. These commands are designed to help users schedule and organize their consultation times more effectively.

<box type="tip">

**Tip:** Use `get-timeslots` to see your full schedule and `get-consultations` for an uncluttered view of your consultation schedule.

</box>

<box type="warning">
Note on overlaps: LambdaLab prevents overlapping timeslots. If you try to add a timeslot that partially or fully overlaps an existing timeslot, the command will be rejected with an error ("A timeslot at the same time already exists."). This safeguard applies to both generic timeslots (block-timeslot) and consultations (add-consultation).
</box>




<br><br>
### Blocking a timeslot: `block-timeslot`

You can use this command to add a timeslot to the application's timeslot store.

Format: `block-timeslot ts/START_DATETIME te/END_DATETIME`

Examples:
* Blocking out a timeslot on 4 October 2025 from 10am to 1pm: \
  * `block-timeslot ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00`
  * `block-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00`
  * `block-timeslot ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00`

<br><br>
### Unblocking a timeslot : `unblock-timeslot`

You can use this command to remove or trim stored timeslots that overlap the specified datetime range.
Format: `unblock-timeslot ts/START_DATETIME te/END_DATETIME`

Examples:
* Unblocking a timeslot on 4 October 2025 from 10am to 1pm: \
  * `unblock-timeslot ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00`
  * `unblock-timeslot ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00`
  * `unblock-timeslot tts/4 Oct 2025 10:00 te/4 Oct 2025 13:00`


<box type="tip">

**Tip:** You can use `get-timeslots` to get the correctly formatted datetimes for the timeslot you want to unblock.
</box>

<box type="warning">

**Caution:** The command will remove exact matches, trim edges, or split stored timeslots that contain the unblock range.

* If the unblock range is strictly inside a stored timeslot, the stored timeslot is split into two (before and after the unblock range).
* If the unblock range overlaps one end of a stored timeslot, the stored timeslot is trimmed accordingly.
</box>

<br>

### Adding a consultation: `add-consultation`

You can use this command to add a consultation timeslot associated with a student's name.

Format: `add-consultation ts/START_DATETIME te/END_DATETIME n/STUDENT_NAME`

Examples:
* Adding a consultation timeslot with John Doe on 4 October 2025 from 10am to 1pm: \
  * `add-consultation ts/2025-10-04T10:00:00 te/2025-10-04T13:00:00 n/John Doe`
  * `add-consultation ts/4 Oct 2025, 10:00 te/4 Oct 2025, 13:00 n/John Doe`
  * `add-consultation ts/4 Oct 2025 10:00 te/4 Oct 2025 13:00 n/John Doe`

<box type="tip">

**Tip:** The Timetable window renders consultations with a distinct appearance (star icon and student name shown under the time label) for easier identification.

</box>

<box type="warning">

**Caution:** The application prevents overlapping timeslots. If you try to add a timeslot that partially or fully overlaps an existing timeslot, the command will be rejected with an error ("A timeslot at the same time already exists."). This safeguard applies to both generic timeslots (block-timeslot) and consultations (add-consultation).
</box>

<br>

### Retrieving merged timeslot ranges: `get-timeslots`

You can use this command to display all timeslot ranges derived from stored timeslots. This allows the user to keep track of unavailable times for easier scheduling of consultations with students.

Format: `get-timeslots`

<box type="tip">

**Tip:** The UI can display these timeslot ranges in the Timetable window (when available). Note that the Timetable view only shows timeslots between 08:00 and 23:00.
![Timetable window](images/timetableWindow.png)

</box>

<br>

### Retrieving consultations only: `get-consultations`

You can use this command to display all consultation timeslot ranges derived from stored consultation entries (ignores generic blocked timeslots). You can use it when you want a quick overview of scheduled consultations (student-facing times) without other blocked times.

Format: `get-consultations`

<br>
### Clearing all timeslots : `clear-timeslots`

You can use this command to remove all stored timeslots (does not affect student records).

Format: `clear-timeslots`

<box type="warning">

**Caution:**
This will permanently remove all stored timeslots. There is no multi-step undo for timeslot clearing;
use immediately after a mistaken action if your environment supports undo of other operations.
</box>


---

## **Visualisation**
- Find
- Filter
- Sort
- List

---

## **Miscellaneous**
- Exit
- Help

---

# **Non-Command Features**
- Save
- Edit data file

---

# Parameters

| **Parameter**      | **Description** | **Prefix** | **Constraint** |
|---------------------|------------------|-------------|----------------|
| **STUDENT_INDEX**   | Index or range of student(s) in the displayed list. | *(no prefix — written before other parameters)* | Must be a whole number greater than 0, or a range in the format `X:Y`. |
| **EXERCISE_INDEX**  | Specific exercise number to mark. | `ei/` | Must be between 0–9 (inclusive). |
| **STATUS**          | Completion or attendance status (`y` = yes, `n` = no). | `s/` | Must be either `y` or `n`. |
| **LAB_NUMBER**      | Specific lab session to mark attendance for. | `l/` | Must be between 1–10 (inclusive). |
| **EXAM_NAME**       | Name of the exam to record or update a grade for. | `en/` | Must be one of: `pe1`, `midterm`, `pe2`, or `final`. |
| **SCORE**           | Numeric grade assigned for the exam. | `sc/` | Must be a number; up to one decimal place. |
| **START_DATETIME**   | Starting datetime of the timeslot | `ts/` | Must be in ISO_LOCAL_DATE_TIME or human-friendly format (specified in notes) |
| **END_DATETIME**   | Ending datetime of the timeslot | `ts/` | Must be in ISO_LOCAL_DATE_TIME or human-friendly format (specified in notes) |
| **STUDENT_NAME**          | Name of student in consultation | `n/` |  |
