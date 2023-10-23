package sde.virginia.edu.hw4;

/**
 * Represents whether a {@link Section} is open to enrollment.
 */
public enum EnrollmentStatus {
    /**
     * The course can add students to enrollment or wait list
     */
    OPEN,

    /**
     * The course cannot add any more students. Additionally, no students can move from the wait list
     * to the enrolled list.
     */
    CLOSED;
}
