package sde.virginia.edu.hw4;

import java.util.*;

/**
 * //TODO: Extract the enrollment logic from {@link Section} into this class
 */

public class Enrollment {

    private int enrollmentCapacity;
    private int waitListCapacity;
    private Set<Student> enrolledStudents;
    private List<Student> waitListedStudents;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(int enrollmentCapacity, int waitListCapacity) {
        this(enrollmentCapacity, waitListCapacity, new HashSet<>(), new ArrayList<>(), EnrollmentStatus.OPEN);
    }

    protected Enrollment(int enrollmentCapacity, int waitListCapacity,
                      Set<Student> enrolledStudents, List<Student> waitListedStudents,
                      EnrollmentStatus enrollmentStatus) {
        if (enrollmentCapacity < 0 || waitListCapacity < 0 || enrolledStudents == null || waitListedStudents == null ||
        enrollmentStatus == null) {
            throw new IllegalArgumentException();
        }

        this.enrollmentCapacity = enrollmentCapacity;
        this.waitListCapacity = waitListCapacity;
        this.enrolledStudents = enrolledStudents;
        this.waitListedStudents = waitListedStudents;
        this.enrollmentStatus = enrollmentStatus;
    }
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

}
