package sde.virginia.edu.hw4;

public class RegistrationService {
    /**
     * Describes the result of {@link RegistrationService#register(Student, Section)}. Either the student was:
     * <ul>
     *     <li>Successfully enrolled in the section</li>
     *     <li>Successfully waited list in the section</li>
     *     <li>Could not be enrolled for some reason</li>
     * </ul>
     */
    public enum RegistrationResult {
        /**
         * The student was successfully enrolled into the section.
         */
        SUCCESS_ENROLLED,
        /**
         * The student was successfully added to the wait list for the section.
         */
        SUCCESS_WAIT_LISTED,
        /**
         * Unable to enroll student because the student is already either enrolled or wait listed in at least one
         * section of this course in the same semester as the one they are trying to regiser for.
         */
        FAILED_ALREADY_IN_COURSE,
        /**
         * Unable to enroll because the course is closed
         */
        FAILED_ENROLLMENT_CLOSED,

        /**
         * Unable to enroll because both the enrollment and wait list for the course are full
         */
        FAILED_SECTION_FULL,

        /**
         * Unable to enroll because the student is either enrolled or wait listed in a class with a time conflict
         */
        FAILED_SCHEDULE_CONFLICT,
        /**
         * Unable to enroll because the student does not meet the prerequisites for the class.
         */
        FAILED_PREREQUISITE_NOT_MET,
        /**
         * Unable to enroll because this would cause the student to exceed their credit limit (combining the credits
         * that they are enrolled AND waitlisted in).
         */
        FAILED_CREDIT_LIMIT_VIOLATION
    }

    /**
     * Attempts to enroll a {@link Student} in the a {@link Section}. If the student is successfully added to the
     * enrollment or the wait list for a given section, that student's {@link Schedule} should also be updated. However,
     * if the student fails to enroll or be wait listed in the section, no changes should occur.
     *
     * @param student the {@link Student} attempting to enroll in a section.
     * @param section the {@link Section} the student is attempting to enroll in
     * @return a {@link RegistrationResult} object indicating success or failure.
     * @see RegistrationResult
     * @see Section#isStudentEnrolled(Student)
     * @see Section#isStudentWaitListed(Student) 
     * @see Section#getEnrollmentStatus()
     * @see Section#isEnrollmentFull() 
     * @see Section#isWaitListFull() 
     * @see Student#getEnrolledSections()
     * @see Student#getWaitListedSections()
     * @see Section#overlapsWith(TimeSlot)
     * @see Prerequisite#isSatisfiedBy(Student)
     * @see Student#getCreditLimit() 
     */
    public RegistrationResult register(Student student, Section section) {
        if(student.isEnrolledInSection(section)){
            return RegistrationResult.FAILED_ALREADY_IN_COURSE;
        }
        if(!section.isEnrollmentOpen()){
            return RegistrationResult.FAILED_ENROLLMENT_CLOSED;
        }
        if(section.isEnrollmentFull()&&section.isWaitListFull()){
            return RegistrationResult.FAILED_SECTION_FULL;
        }
        for(Section enrolledSection: student.getEnrolledSections()){
            if(enrolledSection.overlapsWith(section.getTimeSlot())){
                return RegistrationResult.FAILED_SCHEDULE_CONFLICT;
            }
        }
        Prerequisite prerequisite = section.getCourse().getPrerequisite();
        if(!prerequisite.isSatisfiedBy(student)){
            return RegistrationResult.FAILED_PREREQUISITE_NOT_MET;
        }
        int totalCredits = 0;
        for(Section tempSection:student.getEnrolledSections()){
            totalCredits += tempSection.getCourse().getCreditHours();
        }
        if(totalCredits>=student.getCreditLimit()){
            return RegistrationResult.FAILED_CREDIT_LIMIT_VIOLATION;
        }
        if(section.isEnrollmentFull()){
            return RegistrationResult.SUCCESS_WAIT_LISTED;
        }
        return RegistrationResult.SUCCESS_ENROLLED;
    }

    /**
     * Drop a {@link Student} from either the enrollment or wait list for a given {@list Section}. A successful drop
     * should also be reflected in the {@link Student}'s {@link Schedule}. This will also be added to the student's
     * transcript, adding a Grade of {@link Grade#DROP} for this section to their transcript. (Note that this should be
     * done even if the student already has a grade.)
     *
     * If the student was enrolled (i.e., not waitlisted), and this frees up an empty seat in the class's enrollment,
     * AND the {@link Section}'s enrollment is still {@link EnrollmentStatus#OPEN open}, then the first student
     * on the wait list should be removed from the wait list and added to the section's enrollment automatically. That
     * student's schedule should also be updated to reflect the change. However, if enrollment for the section is
     * {@link EnrollmentStatus#CLOSED closed}, no students should be added to enrollment even if the course is under
     * enrollment capacity.
     *
     * @param student the {@link Student} attempting to drop from a section's enrollment or wait list
     * @param section the {@link Section} to drop the student from.
     * @return true if the student was dropped successfully, false if they could not be dropped because they were
     * neither enrolled nor wait listed in the section.
     * @see Section#isStudentEnrolled(Student)
     * @see Section#isStudentWaitListed(Student)
     * @see Section#getEnrollmentStatus()
     * @see Section#isEnrollmentFull()
     * @see Section#removeStudentFromEnrolled(Student)
     * @see Section#removeStudentFromWaitList(Student)
     * @see Section#getFirstStudentOnWaitList()
     * @see Student#removeEnrolledSection(Section)
     * @see Student#removeWaitListedSection(Section)
     */
    public boolean drop(Student student, Section section) {
        return false;
        //TODO: implement and test
    }
}
