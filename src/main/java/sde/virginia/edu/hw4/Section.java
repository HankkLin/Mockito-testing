package sde.virginia.edu.hw4;

import java.util.*;

public class Section {
    /**
     * CRN (Course Registration Number) a unique 5-digit ID to identify a specific section
     */
    private final int courseRegistrationNumber;
    /**
     * The section number of this section to differentiate it from other offerings of
     * the same course.
     */
    private final int sectionNumber;

    /**
     * The course the section is an offering for.
     */
    private final Course course;

    /**
     * The semester this section is offered.
     */
    private final Semester semester;

    /**
     * What classroom the course is held in.
     */
    private final Location location;

    /**
     * The timeslot for this section
     */
    private final TimeSlot timeSlot;

    /**
     * The lecturer for this section
     */
    private final Lecturer lecturer;

    /**
     * The maximum number of students that can be enrolled in the course
     */
    private int enrollmentCapacity;

    /**
     * The maximum number of students that can be wait listed in the course
     */
    private int waitListCapacity;

    /**
     * The set of students enrolled in the course
     */
    private final Set<Student> enrolledStudents;

    /**
     * The list of students on the wait list, ordered as a queue, where the student
     * in the list the longest is first (index 0)
     */
    private final List<Student> waitListedStudents;

    private EnrollmentStatus enrollmentStatus;

    public Section(int courseRegistrationNumber, int sectionNumber, Course course, Semester semester, Location location,
                   TimeSlot timeSlot, Lecturer lecturer, int enrollmentCapacity, int waitListCapacity) {
        this(courseRegistrationNumber, sectionNumber, course, semester, location, timeSlot, lecturer,
                enrollmentCapacity, waitListCapacity, new HashSet<>(), new ArrayList<>(), EnrollmentStatus.OPEN);
    }

    public Section(int courseRegistrationNumber, int sectionNumber, Course course, Semester semester, Location location,
                   TimeSlot timeSlot, Lecturer lecturer, int enrollmentCapacity, int waitListCapacity,
                   Set<Student> enrolledStudents, List<Student> waitListedStudents, EnrollmentStatus enrollmentStatus) {

        validateInputs(courseRegistrationNumber, sectionNumber, course, semester, location, timeSlot, lecturer,
                enrollmentCapacity, waitListCapacity, enrolledStudents, waitListedStudents, enrollmentStatus);

        this.courseRegistrationNumber = courseRegistrationNumber;
        this.sectionNumber = sectionNumber;
        this.course = course;
        this.semester = semester;
        this.location = location;
        this.timeSlot = timeSlot;
        this.lecturer = lecturer;
        this.enrollmentCapacity = enrollmentCapacity;
        this.waitListCapacity = waitListCapacity;
        this.enrolledStudents = enrolledStudents;
        this.waitListedStudents = waitListedStudents;
        this.enrollmentStatus = enrollmentStatus;
    }

    private void validateInputs(int courseRegistrationNumber, int sectionNumber, Course course, Semester semester, Location location, TimeSlot timeSlot, Lecturer lecturer, int enrollmentCapacity, int waitListCapacity, Set<Student> enrolledStudents, List<Student> waitListedStudents, EnrollmentStatus enrollmentStatus) {
        if (courseRegistrationNumber < 0 || sectionNumber < 0 || course == null || semester == null ||
                location == null || timeSlot == null || lecturer == null || enrollmentCapacity < 0 ||
                waitListCapacity < 0 || enrolledStudents == null || waitListedStudents == null ||
                enrollmentStatus == null) {
            throw new IllegalArgumentException("Invalid Section Initialization");
        }

        if (enrollmentCapacity > location.roomCapacity()) {
            throw new IllegalArgumentException(String.format(
                    "Enrollment capacity: %d excedes the location fire code capacity %d",
                    enrollmentCapacity, location.roomCapacity()));
        }
    }



    /**
     * Gets the CRN (course registration number). This is typically a 5-digit unique numeric identifier for the course
     * @return the course registration number as an int
     */
    public int getCourseRegistrationNumber() {
        return courseRegistrationNumber;
    }

    /**
     * Returns the section number. This is used to differentiate multiple sections of the same course in a given
     * semester.
     * @return the section number as an int
     */
    public int getSectionNumber() {
        return sectionNumber;
    }

    /**
     * Get the {@link Course} this section is an offering for.
     * @return the {@link Course} associated with this section.
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Get the {@link Semester} this section is offered in
     * @return the section's {@link Semester}
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Get the {@link Location} (i.e., the classroom) of the section
     * @return the {@link Location} of the section.
     * @see Location
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Returns the {@link TimeSlot} (the scheduled time) of the section.
     * @return the {@link TimeSlot} for the section
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public boolean overlapsWith(TimeSlot otherTimeSlot) {
        return otherTimeSlot.overlapsWith(timeSlot);
    }


    /**
     * Get the {@link Lecturer} (teacher) of the section
     * @return the {@link Lecturer} of the section
     */
    public Lecturer getLecturer() {
        return lecturer;
    }

    /**
     * Get the enrollment capacity for the section
     * @return the number of students which can be enrolled in the course.
     */
    public int getEnrollmentCapacity() {
        return enrollmentCapacity;
    }

    /**
     * Changes the enrollmentCapacity of the course. Note that if this number is smaller than the current number of
     * enrolled students, no students will be removed from enrollment. However, no one can add the class while
     * the number of enrolled students is greater than or equal to the capacity.
     * @param enrollmentCapacity the new enrollmentCapacity
     * @throws IllegalArgumentException if the new enrollment capacity is larger than the {@link Location}'s fire code
     * capacity
     * @see Section#addStudentToEnrollment(Student)
     */
    public void setEnrollmentCapacity(int enrollmentCapacity) {
        if (enrollmentCapacity < 0) {
            throw new IllegalArgumentException("Enrollment Capacity cannot be negative");
        }
        if (location.roomCapacity() < enrollmentCapacity) {
            throw new IllegalArgumentException("New enrollment capacity: " + enrollmentCapacity +
                    " is too large for " + location + ". Cannot change enrollment capacity for: " + this);
        }
        this.enrollmentCapacity = enrollmentCapacity;
    }

    /**
     * Get the current number of enrolled students
     * @return the number of students currently enrolled.
     */
    public int getEnrollmentSize() {
        return enrolledStudents.size();
    }

    public boolean isEnrollmentFull() {
        return getEnrollmentSize() >= enrollmentCapacity;
    }

    /**
     * Returns the set of students enrolled in the section
     * @return an unmodifiable {@link Set} of students enrolled in the course.
     */
    public Set<Student> getEnrolledStudents() {
        return Collections.unmodifiableSet(enrolledStudents);
    }

    /**
     * Adds the student to the section enrollment if there is space.
     * @param student the student to add to enrollment
     * @throws IllegalStateException if the section enrollment is already full.
     * @throws IllegalArgumentException if the student is already enrolled in the section.
     */
    public void addStudentToEnrollment(Student student) {
        if (!isEnrollmentOpen()) {
            throw new IllegalStateException("Enrollment closed");
        }
        if (isEnrollmentFull()) {
            throw new IllegalStateException(
                    "Enrollment full. Cannot add student: " + student + " to enrollment for " + this);
        }
        if (enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student: " + student + " is already enrolled in the section " + this);
        }

        enrolledStudents.add(student);
    }

    /**
     * Checks if a student is enrolled
     * @param student the {@Student}
     * @return true if the student is enrolled, false if wait listed or not enrolled at all.
     */
    public boolean isStudentEnrolled(Student student) {
        return enrolledStudents.contains(student);
    }

    /**
     * Removes the student from the section enrollment.
     * @param student the student to be removed from the section enrollment
     */
    public void removeStudentFromEnrolled(Student student) {
        if (!enrolledStudents.contains(student)) {
            throw new IllegalArgumentException(
                    "Student: " + student + " is not enrolled in " + this);
        }
        enrolledStudents.remove(student);
    }

    /**
     * Get the waitlisted capacity for the section
     * @return the number of students which can be waitlisted in the course.
     */
    public int getWaitListCapacity() {
        if (waitListCapacity < 0) {
            throw new IllegalArgumentException("Enrollment Capacity cannot be negative");
        }
        return waitListCapacity;
    }

    /**
     * Get the current number of students on the wait list
     * @return the number of students currently wait listed.
     */
    public int getWaitListSize() {
        return waitListedStudents.size();
    }

    /**
     * Checks if the wait list is full
     * @return true if the wait list is full or over capacity.
     */
    public boolean isWaitListFull() {
        return getWaitListSize() >= waitListCapacity;
    }

    /**
     * Changes the waitListCapacity of the course. This does not remove students already on the wait list if the
     * capacity is less than the size.
     * @param waitListCapacity the new wait list capacity for the course.
     */
    public void setWaitListCapacity(int waitListCapacity) {
        if (waitListCapacity < 0) {
            throw new IllegalArgumentException("Cannot have negative capacity");
        }
        this.waitListCapacity = waitListCapacity;
    }

    /**
     * Returns the list of students enrolled in the section, in order of their wait list priority
     * @return an unmodifiable {@link List} of students waitListed in the course.
     */
    public List<Student> getWaitListedStudents() {
        return Collections.unmodifiableList(waitListedStudents);
    }

    /**
     * Returns the first student on the wait-list (the next student to be added if space opens up)
     * @return the first student on the wait list.
     */
    public Student getFirstStudentOnWaitList() {
        if (waitListedStudents.isEmpty()) {
            throw new IllegalStateException("Wait list is empty for section " + this);
        }
        return waitListedStudents.get(0);
    }

    /**
     * Add a student to the wait list if the section enrollment is already full
     * @param student the student to add to the wait list.
     * @throws IllegalStateException if the section's enrollment is not full (that is, the student can enroll directly)
     * OR the wait list is already full.
     * @throws IllegalArgumentException if the student is already enrolled or waitlisted in that section.
     */
    public void addStudentToWaitList(Student student) {
        if (!isEnrollmentOpen()) {
            throw new IllegalStateException("Enrollment closed");
        }
        if (!isEnrollmentFull()) {
            throw new IllegalStateException(
                    "Enrollment not full. Cannot add student: " + student + " to wait list for " + this);
        }
        if (isWaitListFull()) {
            throw new IllegalStateException("Wait list is full. Cannot ads student: " + student + " to wait list for " + this);
        }
        if (enrolledStudents.contains(student)) {
            throw new IllegalArgumentException("Student " + student + " is already enrolled in section " + this);
        }
        if (waitListedStudents.contains(student)) {
            throw new IllegalArgumentException("Student " + student + " is already on the waitlist for section " + this);
        }

        waitListedStudents.add(student);
    }

    /**
     * Checks if a student is wait listed
     * @param student the {@Student}
     * @return true if the student is wait-listed, false if enrolled or not enrolled at all.
     */
    public boolean isStudentWaitListed(Student student) {
        return waitListedStudents.contains(student);
    }

    /**
     * Removes a student from the wait list.
     * @param student the student to be removed from the wait list.
     * @throws IllegalArgumentException if the student is not on the wait list.
     */
    public void removeStudentFromWaitList(Student student) {
        if (!waitListedStudents.contains(student)) {
            throw new IllegalArgumentException(
                    "Student: " + student + " is not on wait list for " + this);
        }
        waitListedStudents.remove(student);
    }

    public boolean isEnrollmentOpen() {
        return enrollmentStatus == EnrollmentStatus.OPEN;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Section section = (Section) o;

        if (getSectionNumber() != section.getSectionNumber()) return false;
        if (!getCourse().equals(section.getCourse())) return false;
        return getSemester().equals(section.getSemester());
    }

    @Override
    public int hashCode() {
        int result = getSectionNumber();
        result = 31 * result + getCourse().hashCode();
        result = 31 * result + getSemester().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Section{" +
                "courseRegistrationNumber=" + courseRegistrationNumber +
                ", sectionNumber=" + sectionNumber +
                ", course=" + course +
                '}';
    }
}
