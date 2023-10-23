package sde.virginia.edu.hw4;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * A list of {@link Section sections} offered in a given {@link Semester}.
 * @see Section
 * @see Semester
 */
public class Catalog {
    /**
     * The semester of this Catalog
     */
    private final Semester semester;
    /**
     * The sections in this catalog
     */
    private final Set<Section> sections;

    /**
     * Constructor
     * @param semester - the {@link Semester}
     */
    public Catalog(Semester semester) {
        this(semester, new HashSet<>());
    }

    /**
     * Constructor
     * @param semester
     * @param sections
     * @throws IllegalArgumentException if either input is null
     */
    protected Catalog(Semester semester, Set<Section> sections) {
        if (semester == null || sections == null) {
            throw new IllegalArgumentException();
        }
        this.semester = semester;
        this.sections = sections;
    }

    /**
     * Get the {@link Semester}
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Get the Sections in this semester
     * @return an immutable copy of the {@link Set} of {@link Section}s in this catalog.
     */
    public Set<Section> getSections() {
        return Collections.unmodifiableSet(sections);
    }

    /**
     * Adds section to the Catalog
     * @param section the section to be added
     * @return true if the section is added, but false if the section was already in the catalog
     * @throws IllegalArgumentException if the section is not in the same {@link Semester} as the catalog
     */
    public boolean add(Section section) {
        if (section.getSemester() != semester) {
            throw new IllegalArgumentException("This section's semester: " + section.getSemester() + " is different " +
                    "from the CourseCatalog's semester: " + this.semester);
        }
        return sections.add(section);
    }

    /**
     * Removes a section from the catalog
     * @param section the {@link Section} to be removed
     * @return true if the section was present and was removed. False if the section was not present.
     */
    public boolean remove(Section section) {
        return sections.remove(section);
    }

    /**
     * Returns true if the section is in the Catalog
     * @param section the {@link Section} to look for
     * @return true of the section is in the catalog
     */
    public boolean contains(Section section) {
        return sections.contains(section);
    }

    /**
     * Returns a section with the matching courseRegistrationNumber
     * @return an Optional of the matching Section. The optional is empty if no section with the CRN exists.
     */
    public Optional<Section> getSectionByCRN(int courseRegistrationNumber) {
        for (Section section : sections) {
            if (section.getCourseRegistrationNumber() == courseRegistrationNumber) {
                return Optional.of(section);
            }
        }
        return Optional.empty();
    }
}
