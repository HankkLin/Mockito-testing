package sde.virginia.edu.hw4;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a student's schedule, including their enrolled and waitlisted {@link Section Sections}.
 * @see Student
 */
public class Schedule {
    public static final int DEFAULT_CREDIT_LIMIT = 17;
    public static final int PROBATION_CREDIT_LIMIT = 15;

    /**
     * The section a student is enrolled in.
     */
    private final Set<Section> enrolledSections;
    /**
     * The courses the student is wait-listed in.
     */
    private final Set<Section> waitListedSections;

    /**
     * Creates an empty schedule
     */
    public Schedule() {
        this(new HashSet<>(), new HashSet<>());
    }

    /**
     * Create a non-empty schedule
     * @param enrolledSections the sections the student is enrolled in
     * @param waitListedSections the sections the student is wait-listed in
     */
    protected Schedule(Set<Section> enrolledSections, Set<Section> waitListedSections) {
        this.enrolledSections = enrolledSections;
        this.waitListedSections = waitListedSections;
    }

    /**
     * Get the sections the student is enrolled in
     * @return An unmodifiable {@link Set} of {@link Section Sections} the student is enrolled in.
     */
    public Set<Section> getEnrolledSections() {
        return Collections.unmodifiableSet(enrolledSections);
    }

    /**
     * Adds a section to the students schedule of enrolled sections
     * @param section the section to be added to the students enrolled sections..
     * @return true if the section was added to the enrolled sections. False if the student was already enrolled in
     * that section.
     */
    public boolean addEnrolledSection(Section section) {
        return enrolledSections.add(section);
    }

    /**
     * Remove a section from the student's schedule of enrolled sections.
     * @param section the section to be removed from the enrolled sections.
     * @return true if the sections was removed. False if the student was not enrolled in that section.
     */

    public boolean removeEnrolledSection(Section section) {
        return enrolledSections.remove(section);
    }

    public boolean isEnrolledInSection(Section section) {
        return enrolledSections.contains(section);
    }

    /**
     * Returns true if the student is enrolled in any {@link Section} that is an offering of course
     * @param course a {@link Course}
     * @return true if the student is enrolled in a {@link Section} associated with the course.
     */
    public boolean isEnrolledInCourse(Course course) {
        return enrolledSections.stream()
                .map(Section::getCourse)
                .anyMatch(c -> c.equals(course));
    }

    /**
     * Get the sections the student is wait-listed in
     * @return An unmodifiable {@link Set} of {@link Section Sections} the student is waitlisted in.
     */
    public Set<Section> getWaitListedSections() {
        return Collections.unmodifiableSet(waitListedSections);
    }

    /**
     * Adds a section to the student's schedule of waitlisted courses
     * @param section the section to be added to the waitlist of.
     * @return true if the section was added to the waitlisted sections. False if the student was already waitlisted in
     * that section.
     */
    public boolean addWaitListedSection(Section section) {
        return waitListedSections.add(section);
    }

    /**
     * Remove a section from the student's schedule of waitlisted sections.
     * @param section the section to be removed from the waitlisted sections.
     * @return true if the sections was removed. False if the student was not waitlisted in that section.
     */
    public boolean removeWaitListedSection(Section section) {
        return waitListedSections.remove(section);
    }

    public boolean isWaitListedInSection(Section section) {
        return waitListedSections.contains(section);
    }


}
