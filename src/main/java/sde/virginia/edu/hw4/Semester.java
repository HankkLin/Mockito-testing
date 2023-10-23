package sde.virginia.edu.hw4;

/**
 * Represents a particular Semester, which is a combination of a {@link Term} and a year, i.e., SPRING 2024
 * @param term the {@link Term} of the semester
 * @param year the integer year of the semester
 */
public record Semester(Term term, int year){
    public Semester {
        if (year < 1950) {
            throw new IllegalArgumentException("Invalid year " + year + ": year must be > 1950");
        }
    }
}