package sde.virginia.edu.hw4;


import java.util.Optional;
import java.util.Set;

/**
 * This class represents a student at the University of Virginia
 */
public class Student {
    /**
     * Student 9 digit id number
     */
    private final long id;
    /**
     * Student's computing id (format: abc2def)
     */
    private final String computingId;

    /**
     * Student's first name
     */
    private String firstName;
    /**
     * Student's last name
     */
    private String lastName;
    /**
     * The student's year (first years = 1, second years = 2, third years = 3, etc.)
     */
    private int year;

    /**
     * The student's schedule, including enrolled and wait listed sections.
     */
    private final Schedule schedule;
    /**
     * The student's transcript
     */
    private final Transcript transcript;

    /**
     * Creates a new student
     * @param id the student's id number (9-digit number)
     * @param computingId the student's computing id (format: abc2def)
     * @param firstName the student's first name
     * @param lastName the student's last name
     * @param year the students years
     */
    public Student(long id, String computingId, String firstName, String lastName, int year) {
        this(id, computingId, firstName, lastName, year, new Schedule(), new Transcript());
    }

    protected Student(long id, String computingId, String firstName, String lastName,
                      int year, Schedule schedule, Transcript transcript) {
        if (schedule == null || transcript == null || year <= 0) {
            throw new IllegalArgumentException("Invalid Student configuration.");
        }
        this.id = id;
        this.computingId = computingId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.schedule = schedule;
        this.transcript = transcript;
    }

    /**
     * Get the student's id number
     * @return the student's id number as a long
     */
    public long getId() {
        return id;
    }

    /**
     * Get the student's computing id
     * @return a {@link String} computing id in the format {abc2def}
     */
    public String getComputingId() {
        return computingId;
    }

    /**
     * Get the student's first name
     * @return {@link String}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Change the student's first name
     * @param firstName the student's new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the student's last name
     * @return {@link String}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Change the student's last name
     * @param lastName the student's new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the student's current year
     * @return an int representing their year (1 for first year, 2 for second year, etc.)
     */
    public int getYear() {
        return year;
    }

    /**
     * Sets the student's current year
     * @param year 1 for first year, 2 for second year, etc.
     */
    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Invalid year: " + year);
        }
        this.year = year;
    }

    /**
     * Get the set of enrolled sections.
     * @return an immutable copy of the students enrolled sections as a {@link Set}
     */
    public Set<Section> getEnrolledSections() {
        return schedule.getEnrolledSections();
    }

    /**
     * Add a section to the student's enrolled sections
     * @return false if the student is already enrolled in the section. True otherwise.
     */
    public boolean addEnrolledSection(Section section) {
        return schedule.addEnrolledSection(section);
    }

    /**
     * Removes a section from the student's enrolled sections
     * @param section The {@link Section} to be removed
     * @return false if the student was not enrolled in the section. True otherwise.
     */

    public boolean removeEnrolledSection(Section section) {
        return schedule.removeEnrolledSection(section);
    }

    /**
     * Check if student is enrolled in a particular section
     * @param section the {@link Section} to check
     * @return true if the student is enrolled, false if not
     */
    public boolean isEnrolledInSection(Section section) {
        return schedule.isEnrolledInSection(section);
    }

    public boolean isEnrolledInCourse(Course course) {
        return schedule.isEnrolledInCourse(course);
    }

    /**
     * Get the set of wait listed sections.
     * @return an immutable copy of the students wait listed sections as a {@link Set}
     */
    public Set<Section> getWaitListedSections() {
        return schedule.getWaitListedSections();
    }

    /**
     * Add a section to the student's wait listed sections
     * @return false if the student is already enrolled in the section. True otherwise.
     */
    public boolean addWaitListedSection(Section section) {
        return schedule.addWaitListedSection(section);
    }

    /**
     * Remove a section to the student's wait listed sections.
     * @param section the {@link Section} to remove
     * @return false if the student was not enrolled in the section. True otherwise.
     */
    public boolean removeWaitListedSection(Section section) {
        return schedule.removeWaitListedSection(section);
    }

    /**
     * Check if student is wait listed in a particular section
     * @param section the {@link Section} to check
     * @return true if the student is wait listed in that section, false if not
     */
    public boolean isWaitListedInSection(Section section) {
        return schedule.isWaitListedInSection(section);
    }

    /**
     * Get the grade the student received in section
     * @param section a {@link Section} a student has received a grade for.
     * @throws IllegalArgumentException if the student hasn't taken a section.
     */
    public Grade getGrade(Section section) {
        return transcript.getGrade(section);
    }

    /**
     * Gets the best grade a student has for a course. Used to check {@link Prerequisite}
     * @param course the course to get the grade for.
     * @return An {@link Optional} of a {@link Grade}. Returns {@link Optional#empty()} if the student hasn't taken the
     * course.
     */
    public Optional<Grade> getBestGrade(Course course) {
        return transcript.getBestGrade(course);
    }

    /**
     * Add a grade to the student's transcript
     * @param section the {@link Section} the student received the grade in
     * @param grade the {@link Grade} received
     */
    public void addGrade(Section section, Grade grade) {
        transcript.add(section, grade);
    }

    /**
     * Get the list of Sections the student has taken
     * @return an unmodifiable {@link Set} of the {@link Section Sections} the student has credit for.
     */
    public Set<Section> getTranscriptSections() {
        return transcript.getSections();
    }

    /**
     * Get the student's current GPA, or grade point average.
     * @return the student's GPA as a double
     */
    public double getGPA() {
        return transcript.getGPA();
    }

    /**
     * Determine if the student's GPA puts them on probation.
     * @return true if the student's GPA is below the probation threshold, false otherwise.
     * @see Transcript#PROBATION_GPA_THRESHOLD
     */
    public boolean isOnProbation() {
        return transcript.isOnProbation();
    }

    /**
     * Gets the students credit limit (the maximum number of credits they can be enrolled and waitlisted in). Student's
     * on Probation receive a reduced credit limit.
     * @return the credit limit as an int
     */
    public int getCreditLimit() {
        if (isOnProbation()) {
            return Schedule.PROBATION_CREDIT_LIMIT;
        }
        else return Schedule.DEFAULT_CREDIT_LIMIT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return getId() == student.getId();
    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", computingId='" + computingId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year=" + year +
                '}';
    }
}
