package sde.virginia.edu.hw4;

import java.util.Optional;

/**
 * Represents a Grade a student received in a class.
 */
public enum Grade {
    A_PLUS(4.0, 12, true),
    A(4.0, 11, false),
    A_MINUS(3.7, 10, false),
    B_PLUS(3.3, 9, false),
    B(3.0, 8, false),
    B_MINUS(2.7, 7, false),
    C_PLUS(2.3, 6, false),
    C(2.0, 5, false),
    C_MINUS(1.7, 4, false),
    D_PLUS(1.3, 3, false),
    D(1.0, 2, false),
    D_MINUS(0.7, 1, true),
    F(0.0, -1, false),
    W(Double.NaN, -1, false),
    COVID_CR(Double.NaN, 5, true),
    COVID_GC(Double.NaN, 0, true),
    COVID_NC(Double.NaN, -1, false),
    CREDIT(Double.NaN, 0, false),
    NO_CREDIT(Double.NaN, -1, false),
    DROP(Double.NaN, -1, false),;

    /**
     * The number of gradePoints the grade is worth. Set to {@link Double#NaN} if the grade doesn't affect GPA.
     */
    private final double gradePoints;
    /**
     * A comparison score as related to prerequisites. Specifically, a grade is "as good or better" than another
     * grade with relation to prerequisites if the comparison score is greater than or equal to another.
     */
    private final int prerequisiteScore;
    /**
     * Whether the grade is worth graduation credit. If false, this grade does not give graduation credit.
     */
    private final boolean worthCredit;

    /**
     * Constructor
     * @param gradePoints the number of grade points the grade is worth. Set to Double.NaN if grade doesn't affect GPA
     * @param prerequisiteScore the prerequisite score to determine if a prerequisite is met
     * @param graduationCredit whether the grade can count towards graduation credit
     */
    private Grade(double gradePoints, int prerequisiteScore, boolean graduationCredit) {
        this.gradePoints = gradePoints;
        this.prerequisiteScore = prerequisiteScore;
        this.worthCredit = graduationCredit;
    }

    /**
     * Get an Optional of the grade points the grade is worth.
     * @return An {@link Optional} of the gpa. Returns {@link Optional#empty()} if the grade doesn't affect GPA.
     */
    public Optional<Double> getGradePoints() {
        if (Double.isNaN(gradePoints)) {
            return Optional.empty();
        }
        return Optional.of(gradePoints);
    }

    /**
     * Get prerequisite score
     */
    public int getPrerequisiteScore() {
        return prerequisiteScore;
    }

    /**
     * Returns whether the grade is worth graduation credit
     * @return true if the grade is worth graduation credit. False otherwise.
     */
    public boolean isWorthCredit() {
        return worthCredit;
    }

    /**
     * Returns whether this grade is sufficient for some minimum required grade.
     * @param minimumRequirement the minimum required grade
     * @return true if this grade meets the minimum requirement
     */
    public boolean greaterThanOrEqualTo(Grade minimumRequirement) {
        return (this.prerequisiteScore - minimumRequirement.prerequisiteScore) >= 0;
    }

    @Override
    public String toString() {
        var name = this.name();
        name = name.replace("_PLUS", "+");
        name = name.replace("_MINUS", "-");
        return name;
    }
}
