package sde.virginia.edu.hw4;

import java.util.List;

/**
 * Represents a particular perm during the year at University of Virginia.
 * @see Semester
 */
public enum Term {
    FALL, JANUARY, SPRING, SUMMER_1, SUMMER_2, SUMMER_3;

    /**
     * Returns a list of the terms ordered chronologically within a single year.
     */
    public static final List<Term> ORDERED_TERMS = List.of(
            JANUARY, SPRING, SUMMER_1, SUMMER_2, SUMMER_3, FALL);
}
