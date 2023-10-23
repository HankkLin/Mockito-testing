package sde.virginia.edu.hw4;

import java.util.Map;

public class FinalGradesService {
    /**
     * Uploads final grades to each student's transcript. Be aware that this method can also be used to <i>change</i> an
     * existing grade. It is <b>not required</b> for a professor to upload all final grades at the same time, so not all
     * students enrolled in the section must be present in the finalGrades {@link Map}
     *
     * @param section the section the final grades are being uploaded for.
     * @param finalGrades a {@link Map} of {@link Student}s to their final {@link Grade} in the class.
     * @throws IllegalArgumentException if any student in finalGrades is not enrolled in the class. In this situation,
     * no post-conditions should occur (that is, no grades uploaded and no schedule changes). Be aware that students
     * on the wait list cannot receive grades in the Section.
     *
     * @see Section#isStudentEnrolled(Student)
     * @see Student#addGrade(Section, Grade)
     */
    public void uploadFinalGrades(Section section, Map<Student, Grade> finalGrades) {
        //TODO: implement and test
    }
}
