package sde.virginia.edu.hw4;

/**
 * Represents a course, such as "CS 3140 - Software Development Essentials". Note that this describes the course
 * as a whole, not an individual offering. Individual offerings are described in {@link Section}.
 *
 * @see Section Section: an offering of a Course
 * @see Prerequisite Prerequisite: a collection of required minimum grades in specific courses need to take a Course
 */
public class Course {
    /**
     * A unique numeric identifier for a course
     */
    private final int id;
    /**
     * The course mnemonic, such as "CS", "APMA", "STS", etc.
     */
    private final String mnemonic;
    /**
     * The course number, such as 3140. Note this is stored as a {@link String} because, rarely, a class includes a
     * letter in the course "number".
     */
    private final String courseNumber; //there are like two grad level courses with letters in them
    /**
     * The course title, such as "Software Development Essentials"
     */
    private final String title;
    /**
     * The course {@link Prerequisite prerequisites}. That is, the courses required to complete this course.
     * @see Prerequisite
     */
    private final Prerequisite prerequisite;

    /**
     * The number of credit hours the course is worth.
     */
    private final int creditHours;

    /**
     * Creates a course with no existing prerequisites
     * @param id the course's id
     * @param mnemonic the course mnemonic (i.e., "CS") as a {@link String}
     * @param courseNumber the course number (i.e., "3140") as a {@link String}
     * @param title the course's name (i.e., "Software Development Essentials") as a {@link String}
     */
    public Course(int id, String mnemonic, String courseNumber, String title, int creditHours) {
        this(id, mnemonic, courseNumber, title, creditHours, new Prerequisite());
    }

    /**
     * Creates a course
     * @param id the course's id
     * @param mnemonic the course mnemonic (i.e., "CS") as a {@link String}
     * @param courseNumber the course number (i.e., "3140") as a {@link String}
     * @param title the course's name (i.e., "Software Development Essentials") as a {@link String}
     * @param prerequisite a {@link Prerequisite} that includes the required courses (and their minimum grades)
     *                    to take this course
     */
    protected Course(int id, String mnemonic, String courseNumber, String title, int creditHours, Prerequisite prerequisite) {
        if (mnemonic == null || courseNumber == null || title == null || prerequisite == null || id < 0 || creditHours < 0) {
            throw new IllegalArgumentException("Invalid Course arguments");
        }
        this.id = id;
        this.mnemonic = mnemonic;
        this.courseNumber = courseNumber;
        this.title = title;
        this.creditHours = creditHours;
        this.prerequisite = prerequisite;
    }

    /**
     * Get the course id
     * @return the id as an int
     */
    public int getId() {
        return id;
    }

    /**
     * Get the course mnemonic (i.e., "CS", "APMA", etc.)
     * @return the course mnemonic as a {@link String}
     */
    public String getMnemonic() {
        return mnemonic;
    }

    /**
     * Get the course number (i.e., "3140")
     * @return the course number as a {@link String}
     */
    public String getCourseNumber() {
        return courseNumber;
    }

    /**
     * Get the course title (i.e., "Software Development Essentials")
     * @return the course title as a {@link String}
     */
    public String getTitle() {
        return title;
    }

    /**
     * Return the number of credit hours the course is worth (including lab if present)
     * @return int credit hours
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * Get the course prerequisites {@link Prerequisite}
     * @return the course {@link Prerequisite prerequisites}
     * @see Prerequisite
     */
    public Prerequisite getPrerequisite() {
        return prerequisite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        if (!getMnemonic().equals(course.getMnemonic())) return false;
        return getCourseNumber().equals(course.getCourseNumber());
    }

    @Override
    public int hashCode() {
        int result = getMnemonic().hashCode();
        result = 31 * result + getCourseNumber().hashCode();
        return result;
    }
}
