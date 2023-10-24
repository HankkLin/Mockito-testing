package sde.virginia.edu.hw4;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents the requirements need to take a {@link Course}.
 * @see Course
 * @see Grade
 */
public class Prerequisite {
    /**
     * The set of required courses and their minimum grades to take another course
     */
    private final Map<Course, Grade> requiredCourses;

    /**
     * Creates a new empty Prerequisite with no required courses
     */
    public Prerequisite() {
        this(new HashMap<Course, Grade>());
    }

    /**
     * Creates a non-empty Prerequisite with directly set minimum course/grade requirements
     * @param requiredCourses a {@link Map} of {@link Course Courses} to {@link Grade minimum required grades}
     */
    public Prerequisite(Map<Course, Grade> requiredCourses) {
        this.requiredCourses = requiredCourses;
    }

    /**
     * Adds a new prerequisite requirements. This method can also be used to modify an existing prerequisite.
     * @param course the {@link Course} being added as a requirement, or whose requirement is being modified
     * @param minimumGrade the minimum {@link Grade} required to meet the prerequisite.
     */
    public void add(Course course, Grade minimumGrade) {
        requiredCourses.put(course, minimumGrade);
    }

    /**
     * Gets the minimum Grade required for a course
     * @param course the {@link Course} to get the minimum grade for.
     * @return the minimum {@link Grade} that meets the pre-requisite.
     */
    public Grade getMinimumGrade(Course course) {
        if (!requiredCourses.containsKey(course)) {
            throw new IllegalArgumentException("Prerequisites doesn't include course: " + course);
        }
        return requiredCourses.get(course);
    }

    /**
     * Remove the prerequisite for a specific course
     * @param course the {@link Course} to remove the prerequisite for.
     * @throws IllegalArgumentException if the course is not already part of this prerequisite.
     */
    public void remove(Course course) {
        if (!requiredCourses.containsKey(course)) {
            throw new IllegalArgumentException("Course: " + course + " is not in the prerequisites: " + this);
        }
        requiredCourses.remove(course);
    }

    /**
     * Get the prerequisite courses
     * @return an unmodifiable {@link Set} of the {@link Course Courses} that are part of this prerequisite.
     */
    public Set<Course> getPrerequisiteCourses() {
        return Collections.unmodifiableSet(requiredCourses.keySet());
    }

    /**
     * Checks if a student meets this pre-requisite
     * @param student the student to check
     * @return true if the student meets all the prerequisite conditions. False otherwise.
     */
    public boolean isSatisfiedBy(Student student) {
        //TODO: update method to account for student's currently taking the course
        for (Course course: requiredCourses.keySet()) {
            if(!(student.isEnrolledInCourse(course))){
                var minimumGrade = requiredCourses.get(course);
                var optionalTranscriptGrade = student.getBestGrade(course);
                System.out.println(optionalTranscriptGrade);
                if (optionalTranscriptGrade.isEmpty()) {
                    return false;
                }
                var transcriptGrade = optionalTranscriptGrade.get();
                if (!transcriptGrade.greaterThanOrEqualTo(minimumGrade)) {
                    return false;
                }
            }
        }
        return true;
    }
}
