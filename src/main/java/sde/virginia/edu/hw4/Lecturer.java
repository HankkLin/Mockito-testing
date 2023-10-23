package sde.virginia.edu.hw4;

/**
 * Represents a lecturer, or professor.
 */
public class Lecturer {
    /**
     * A unique numeric identifier
     */
    private final int id;
    /**
     * A unique String of the form abc2def - add "@virginia.edu" to get the email address
     */
    private final String computingId;
    /**
     * The lecturer's first name
     */
    private String firstName;
    /**
     * The lecturer's surname
     */
    private String lastName;

    /**
     * Creates a lecturer
     * @param id a unique integer id for each professor
     * @param computingId a unique {@link String} id of the form abc2def
     * @param firstName a first name as a {@link String}
     * @param lastName a last name as a {@link String}
     */
    public Lecturer(int id, String computingId, String firstName, String lastName) {
        if (computingId == null) {
            throw new IllegalArgumentException("computingID cannot be null");
        }
        this.id = id;
        this.computingId = computingId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the id
     * @return an int id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the computing id
     * @return a {@link String} computing id
     */
    public String getComputingId() {
        return computingId;
    }

    /**
     * Gets the first name
     * @return the first name as a {@link String}
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the lecturer's first name
     * @param firstName the new first name as a {@link String}
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the lecturer's last name
     * @return the last name as a {@link String}
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the lecturer's last name
     * @param lastName the new last name as a {@link String}
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lecturer lecturer = (Lecturer) o;

        if (id != lecturer.id) return false;
        return computingId.equals(lecturer.computingId);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + computingId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Lecturer{" +
                "id=" + id +
                ", computingId='" + computingId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
