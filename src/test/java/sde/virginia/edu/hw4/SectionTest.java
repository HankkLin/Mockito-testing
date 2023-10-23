package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SectionTest {
    private Section section;
    @Mock
    private Course course;

    private Semester semester;

    private Location location;

    private TimeSlot timeSlot;
    @Mock
    private Lecturer lecturer;
    @Mock
    private Set<Student> enrolledStudents;
    @Mock
    private List<Student> waitListedStudents;

    @BeforeEach
    public void setup() {
        semester = new Semester(Term.FALL, 2023);
        location = new Location("Nau Hall", "101", 245);
        timeSlot = new TimeSlot(TimeSlot.TUESDAY_THURSDAY, 12, 30, 13, 45);

        section = new Section(12345, 1, course, semester, location, timeSlot, lecturer,
                245, 199, enrolledStudents, waitListedStudents, EnrollmentStatus.OPEN);
    }




    @Test
    void getCourseRegistrationNumber() {
        assertEquals(12345, section.getCourseRegistrationNumber());
    }

    @Test
    void getSectionNumber() {
        assertEquals(1, section.getSectionNumber());
    }

    @Test
    void getCourse() {
        assertEquals(course, section.getCourse());
    }

    @Test
    void getSemester() {
        assertEquals(semester, section.getSemester());
    }

    @Test
    void getLocation() {
        assertEquals(location, section.getLocation());
    }

    @Test
    void getTimeSlot() {
        assertEquals(timeSlot, section.getTimeSlot());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void overlapsWith(boolean input) {
        var mockTimeSlot = mock(TimeSlot.class);
        when(mockTimeSlot.overlapsWith(timeSlot)).thenReturn(input);

        assertEquals(input, section.overlapsWith(mockTimeSlot));
    }

    @Test
    void getLecturer() {
        assertEquals(lecturer, section.getLecturer());
    }

    @Test
    void isEnrollmentFull_true() {
        when(enrolledStudents.size()).thenReturn(section.getEnrollmentCapacity());
        assertTrue(section.isEnrollmentFull());
    }

    @Test
    void isEnrollmentFull_true_overCapacity() {
        when(enrolledStudents.size()).thenReturn(section.getEnrollmentCapacity()+1);
        assertTrue(section.isEnrollmentFull());
    }

    @Test
    void isEnrollmentFull_false() {
        when(enrolledStudents.size()).thenReturn(section.getEnrollmentCapacity()-1);
        assertFalse(section.isEnrollmentFull());
    }

    @Test
    void getEnrollmentCapacity() {
        assertEquals(245, section.getEnrollmentCapacity());
    }

    @Test
    void setEnrollmentCapacity() {
        section.setEnrollmentCapacity(200);
        assertEquals(200, section.getEnrollmentCapacity());
    }

    @Test
    void setEnrollmentCapacity_exception_negative() {
        assertThrows(IllegalArgumentException.class, () ->
                section.setEnrollmentCapacity(-1));
    }

    @Test
    void setEnrollmentCapacity_exception_fireCode() {
        assertThrows(IllegalArgumentException.class, () ->
                section.setEnrollmentCapacity(246));
    }

    @Test
    void getEnrollmentSize() {
        when(enrolledStudents.size()).thenReturn(100);
        assertEquals(100, section.getEnrollmentSize());
    }

    @Test
    void addStudentToEnrollment_equivalence() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(100);
        when(enrolledStudents.contains(student)).thenReturn(false);

        section.addStudentToEnrollment(student);
        verify(enrolledStudents).add(student);
    }

    @Test
    void addStudentToEnrollment_exception_Closed() {
        var student = mock(Student.class);
        section.setEnrollmentStatus(EnrollmentStatus.CLOSED);

        assertThrows(IllegalStateException.class, () -> section.addStudentToEnrollment(student));
        verify(enrolledStudents, never()).add(student);
    }

    @Test
    void addStudentToEnrollment_exception_Full() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(245);

        assertThrows(IllegalStateException.class, () -> section.addStudentToEnrollment(student));
        verify(enrolledStudents, never()).add(student);
    }

    @Test
    void addStudentToEnrollment_exception_AlreadyEnrolled() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(244);
        when(enrolledStudents.contains(student)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> section.addStudentToEnrollment(student));
        verify(enrolledStudents, never()).add(student);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isStudentEnrolled(boolean input) {
        var student = mock(Student.class);
        when(enrolledStudents.contains(student)).thenReturn(input);

        assertEquals(input, section.isStudentEnrolled(student));
    }

    @Test
    void removeStudentFromEnrolled() {
        var student = mock(Student.class);
        when(enrolledStudents.contains(student)).thenReturn(true);

        section.removeStudentFromEnrolled(student);

        verify(enrolledStudents).remove(student);
    }

    @Test
    void removeStudentFromEnrolled_exception() {
        var student = mock(Student.class);
        when(enrolledStudents.contains(student)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> section.removeStudentFromEnrolled(student));

        verify(enrolledStudents, never()).remove(student);
    }

    @Test
    void getWaitListCapacity() {
        assertEquals(199, section.getWaitListCapacity());
    }

    @Test
    void getWaitListSize() {
        when(waitListedStudents.size()).thenReturn(99);
        assertEquals(99, section.getWaitListSize());
    }

    @Test
    void isWaitListFull_true() {
        when(waitListedStudents.size()).thenReturn(section.getWaitListCapacity());
        assertTrue(section.isWaitListFull());
    }

    @Test
    void isWaitListFull_true_overCapacity() {
        when(waitListedStudents.size()).thenReturn(section.getWaitListCapacity() + 1);
        assertTrue(section.isWaitListFull());
    }

    @Test
    void isWaitListFull_false() {
        when(waitListedStudents.size()).thenReturn(section.getWaitListCapacity() - 1);
        assertFalse(section.isWaitListFull());
    }

    @Test
    void setWaitListCapacity() {
        section.setWaitListCapacity(50);
        assertEquals(50, section.getWaitListCapacity());
    }

    @Test
    void setWaitListCapacity_exception() {
        assertThrows(IllegalArgumentException.class, () -> section.setWaitListCapacity(-1));
    }

    @Test
    void getFirstStudentOnWaitList() {
        var student = mock(Student.class);
        when(waitListedStudents.isEmpty()).thenReturn(false);
        when(waitListedStudents.get(0)).thenReturn(student);

        assertEquals(student, section.getFirstStudentOnWaitList());
    }

    @Test
    void getFirstStudentOnWaitList_exception() {
        var student = mock(Student.class);
        when(waitListedStudents.isEmpty()).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> section.getFirstStudentOnWaitList());
    }

    @Test
    void addStudentToWaitList_equivalence() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(245);
        when(waitListedStudents.size()).thenReturn(198);
        when(enrolledStudents.contains(student)).thenReturn(false);
        when(waitListedStudents.contains(student)).thenReturn(false);

        section.addStudentToWaitList(student);

        verify(waitListedStudents).add(student);
    }

    @Test
    void addStudentToWaitList_enrollmentClosed() {
        section.setEnrollmentStatus(EnrollmentStatus.CLOSED);
        var student = mock(Student.class);

        assertThrows(IllegalStateException.class, () -> section.addStudentToWaitList(student));

        verify(waitListedStudents, never()).add(student);
    }

    @Test
    void addStudentToWaitList_enrollmentNotFull() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(244);

        assertThrows(IllegalStateException.class, () -> section.addStudentToWaitList(student));

        verify(waitListedStudents, never()).add(student);
    }

    @Test
    void addStudentToWaitList_waitListFull() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(245);
        when(enrolledStudents.size()).thenReturn(199);

        assertThrows(IllegalStateException.class, () -> section.addStudentToWaitList(student));

        verify(waitListedStudents, never()).add(student);
    }

    @Test
    void addStudentToWaitList_alreadyEnrolled() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(245);
        when(waitListedStudents.size()).thenReturn(198);
        when(enrolledStudents.contains(student)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> section.addStudentToWaitList(student));

        verify(waitListedStudents, never()).add(student);
    }

    @Test
    void addStudentToWaitList_alreadyWaitListed() {
        var student = mock(Student.class);
        when(enrolledStudents.size()).thenReturn(245);
        when(waitListedStudents.size()).thenReturn(198);
        when(enrolledStudents.contains(student)).thenReturn(false);
        when(waitListedStudents.contains(student)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> section.addStudentToWaitList(student));

        verify(waitListedStudents, never()).add(student);
    }



    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isStudentWaitListed(boolean input) {
        var student = mock(Student.class);
        when(waitListedStudents.contains(student)).thenReturn(input);

        assertEquals(input, section.isStudentWaitListed(student));
    }

    @Test
    void removeStudentFromWaitList() {
        var student = mock(Student.class);
        when(waitListedStudents.contains(student)).thenReturn(true);

        section.removeStudentFromWaitList(student);

        verify(waitListedStudents).remove(student);
    }

    @Test
    void removeStudentFromWaitList_Exception() {
        var student = mock(Student.class);
        when(waitListedStudents.contains(student)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> section.removeStudentFromWaitList(student));

        verify(waitListedStudents, never()).remove(student);
    }

    @Test
    void isEnrollmentOpen_true() {
        assertTrue(section.isEnrollmentOpen());
    }

    @Test
    void isEnrollmentOpen_false() {
        section.setEnrollmentStatus(EnrollmentStatus.CLOSED);
        assertFalse(section.isEnrollmentOpen());
    }

    @Test
    void getEnrollmentStatus() {
        assertEquals(EnrollmentStatus.OPEN, section.getEnrollmentStatus());
    }
}