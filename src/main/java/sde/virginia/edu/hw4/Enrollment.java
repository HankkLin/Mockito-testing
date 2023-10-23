package sde.virginia.edu.hw4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * //TODO: Extract the enrollment logic from {@link Section} into this class
 */

public class Enrollment {
    private int enrollmentCapacity;
    private int waitListCapacity;
    private Set<Student> enrolledStudents;
    private List<Student> waitListedStudents;
    private EnrollmentStatus enrollmentStatus;

    public Enrollment(int enrollmentCapacity, int waitListCapacity) {
        this(enrollmentCapacity, waitListCapacity, new HashSet<>(), new ArrayList<>(), EnrollmentStatus.OPEN);
    }

    protected Enrollment(int enrollmentCapacity, int waitListCapacity,
                      Set<Student> enrolledStudents, List<Student> waitListedStudents,
                      EnrollmentStatus enrollmentStatus) {
        if (enrollmentCapacity < 0 || waitListCapacity < 0 || enrolledStudents == null || waitListedStudents == null ||
        enrollmentStatus == null) {
            throw new IllegalArgumentException();
        }

        this.enrollmentCapacity = enrollmentCapacity;
        this.waitListCapacity = waitListCapacity;
        this.enrolledStudents = enrolledStudents;
        this.waitListedStudents = waitListedStudents;
        this.enrollmentStatus = enrollmentStatus;
    }
}
