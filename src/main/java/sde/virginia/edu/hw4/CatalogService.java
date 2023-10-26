package sde.virginia.edu.hw4;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CatalogService {
    private Catalog catalog;

    /**
     * Constructor
     * @param catalog
     */
    public CatalogService(Catalog catalog) {
        this.catalog = catalog;
    }

    /**
     * Describes the result of {@link Catalog#add(Section)}
     */
    public enum AddSectionResult {
        /**
         * The section was successfully added to the Catalog
         */
        SUCCESSFUL,
        /**
         * The second could not be added because it's semester doesn't match the catalogs
         */
        FAILED_SEMESTER_MISMATCH,
        /**
         * Section not added to catalog, as it already exists in the catalog.
         */
        FAILED_SECTION_ALREADY_EXISTS,
        /**
         * Section not added because the CRN is already in-use by another course.
         */
        FAILED_CRN_CONFLICT,
        /**
         * Section not added to catalog, as the TimeSlot of this section conflicts with the time slot of another
         * section in the same location.
         */
        FAILED_LOCATION_CONFLICT,
        /**
         * Section not added to catalog, the lecturer is already teaching another class that conflicts with this
         * section's timeslot.
         */
        FAILED_LECTURER_CONFLICT,
        /**
         * At the time the section is added to the catalog, it's enrollment and wait-list must be empty (that is,
         * no students registered ahead of time).
         */
        FAILED_ENROLLMENT_NOT_EMPTY
    }

    /**
     * Attempts to add a section to the course catalog, ensuring the section doesn't break any rules.
     * The rules are:<br>
     * <ol>
     *     <li>A section can only be added to the catalog once. No duplicate sections. If the section is already
     *     present, then return {@link AddSectionResult#FAILED_SECTION_ALREADY_EXISTS}</li>
     *     <li>CRNs in a given catalog must be unique. If the CRN is not unique, this returns
     *     {@link AddSectionResult#FAILED_CRN_CONFLICT}</li>
     *     <li>No two sections can use the same {@link Location} in overlapping {@link TimeSlot}s. If this rule would
     *     be violated, return {@link AddSectionResult#FAILED_LOCATION_CONFLICT}</li>
     *     <li>No lecturer can teach two sections with overlapping {@link TimeSlot}s. If this rule would be
     *     violated, return {@link AddSectionResult#FAILED_LECTURER_CONFLICT}</li>
     *     <li>At the time a course is added to the catalog, it must have no students already registred for it. That
     *     is, the enrollment and waitlist must be empty. If this is violated, return
     *     {@link AddSectionResult#FAILED_ENROLLMENT_NOT_EMPTY}</li>
     * </ol>
     * If any of the above rules are violated, the section <b>should not</b> be added to the catalog. However, if none
     * of the rules are violated, the section <b>should</b> be added to the catalog, and {@link AddSectionResult#SUCCESSFUL}
     * should be returned.
     * @param section the section to attempt to add to catalog
     * @return a {@link AddSectionResult} enum indicating success or the reason for failure
     * @see Section#getSemester()
     * @see Catalog#getSemester()
     * @see Catalog#contains(Section)
     * @see Catalog#getSectionByCRN(int) 
     * @see Location#equals(Object) 
     * @see Section#overlapsWith(TimeSlot)
     * @see Lecturer#equals(Object)
     * @see Section#getEnrollmentSize()
     * @see Section#getWaitListSize()
     */
    public AddSectionResult add(Section section) {
        if(catalog.contains(section)){
            return AddSectionResult.FAILED_SECTION_ALREADY_EXISTS;
        }
        if(!(catalog.getSectionByCRN(section.getCourseRegistrationNumber()).equals(Optional.empty()))){
            return AddSectionResult.FAILED_CRN_CONFLICT;
        }
        var sectionSet = catalog.getSections();
        for(Section existSection: sectionSet){
            if(section.overlapsWith(existSection.getTimeSlot())) {
                if (section.getLocation().equals(existSection.getLocation())) {
                    return AddSectionResult.FAILED_LOCATION_CONFLICT;
                }
                if (section.getLecturer().equals(existSection.getLecturer())) {
                    return AddSectionResult.FAILED_LECTURER_CONFLICT;
                }
            }
        }
        if(section.getEnrollmentSize()!=0 || section.getWaitListSize()!=0){
            return AddSectionResult.FAILED_ENROLLMENT_NOT_EMPTY;
        }
        catalog.add(section);
        return AddSectionResult.SUCCESSFUL;
    }


    /**
     * Remove a section from the course catalog. This should also remove the section from any of the enrolled/wait-list
     * Student's schedules.
     * @param section the section to be removed
     * @throws IllegalArgumentException if the section is not present in the catalog
     * @see Catalog#contains(Section)
     * @see Catalog#remove(Section)
     * @see Section#getEnrolledStudents()
     * @see Section#getWaitListedStudents()
     * @see Student#removeEnrolledSection(Section)
     * @see Student#removeWaitListedSection(Section)
     */
    public void removeSection(Section section) {

        //TODO: implement and test
        var sectionExist=catalog.remove(section);
        if(sectionExist) {
            for (Student student : section.getEnrolledStudents()) {
                student.removeEnrolledSection(section);
            }
            section.getEnrolledStudents().clear();
            for (Student student : section.getWaitListedStudents()) {
                student.removeWaitListedSection(section);
            }
        }
        else{
            throw new NoSuchElementException(section.toString()+ " is not in the catalog.");
        }
    }


    /**
     * Set all sections to closed enrollment. This method should be called at the add deadline each semester.
     * @see Section#setEnrollmentStatus(EnrollmentStatus)
     * @see EnrollmentStatus
     */
    public void closeAllSection() {
        //TODO: implement and test

    }
}
