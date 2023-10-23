package sde.virginia.edu.hw4;

import java.util.*;

/**
 * Represents a student's transcript: for each section the student has taken as well as the grade for that section.
 * @see Student
 * @see Section
 * @see Grade
 */
public class Transcript {
    public static final double PROBATION_GPA_THRESHOLD = 1.8;

    /**
     * The history of {@link Section sections} a student has received a grade for,
     * mapped to the {@link Grade grade} received.
     */
    private final Map<Section, Grade> history;

    /**
     * Creates a new empty Transcript
     */
    public Transcript() {
        this(new HashMap<>());
    }

    /**
     * Create a new Transcript directly injecting the grade history.
     * @param history the directly injected section grade history
     */
    protected Transcript(Map<Section, Grade> history) {
        this.history = history;
    }

    /**
     * Get the grade the student received in a particular section of a course.
     * @param section the section to get the grade for
     * @return the {@link Grade} the student received.
     * @throws IllegalArgumentException if student has not grade for the section
     */
    public Grade getGrade(Section section) {
        if (!history.containsKey(section)) {
            throw new IllegalArgumentException("Transcript doesn't contain section: " + section);
        }
        return history.get(section);
    }

    /**
     * Gets the best grade a student has for a course. Used to check {@link Prerequisite}
     * @param course the course to get the grade for.
     * @return An {@link Optional} of a {@link Grade}. Returns {@link Optional#empty()} if the student hasn't taken the
     * course.
     */
    public Optional<Grade> getBestGrade(Course course) {
        return history.keySet().stream()
                .filter(section -> course.equals(section.getCourse()))
                .map(history::get)
                .max(Comparator.comparing(Grade::getPrerequisiteScore));
    }

    /**
     * Adds a section/grade entry to the student's transcript. This method is also used to update an existing grade
     * in this case a grade needs to be changed.
     * @param section the {@link Section} the student is receiving a grade for.
     * @param grade the {@link Grade} the student received
     */
    public void add(Section section, Grade grade) {
        history.put(section, grade);
    }

    /**
     * Get the list of Sections the student has taken
     * @return an unmodifiable {@link Set} of the {@link Section Sections} the student has credit for.
     */
    public Set<Section> getSections() {
        return Collections.unmodifiableSet(history.keySet());
    }

    public double getGPA() {
        var gradedCredits = 0;
        var creditPoints = 0.0;
        for (Section section: getSections()) {
            var grade = getGrade(section);
            var optionalPoints = grade.getGradePoints();
            if (optionalPoints.isPresent()) {
                var course = section.getCourse();
                var creditHours = course.getCreditHours();
                gradedCredits += creditHours;
                creditPoints += creditHours * optionalPoints.get();
            }
        }
        return creditPoints / gradedCredits;
    }

    public boolean isOnProbation() {
        return getGPA() < PROBATION_GPA_THRESHOLD;
    }
}
