package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class RegistrationServiceTest {

    private RegistrationService registrationService;
    @Mock
    Student student, firstWaitListStudent;
    @Mock
    Section section, enrolledSection1, enrolledSection2;
    @Mock
    Set<Section> studentEnrolledSections;
    @Mock
    Prerequisite prerequisite;
    @Mock
    Course course;
    @BeforeEach
    void setUp(){
        registrationService = new RegistrationService();
        student = mock(Student.class);
        firstWaitListStudent = mock(Student.class);
        section = mock(Section.class);
        enrolledSection1 = mock(Section.class);
        enrolledSection2 = mock(Section.class);
        course = mock(Course.class);
        prerequisite = mock(Prerequisite.class);
        studentEnrolledSections = new HashSet<>(Set.of(enrolledSection1,enrolledSection2));

        when(student.getEnrolledSections()).thenReturn(studentEnrolledSections);
        when(student.getCreditLimit()).thenReturn(17);
        when(section.getCourse()).thenReturn(course);
        when(enrolledSection1.getCourse()).thenReturn(course);
        when(enrolledSection2.getCourse()).thenReturn(course);
        when(course.getPrerequisite()).thenReturn(prerequisite);
    }
    @Test
    void RegistrationResult_FAILED_ALREADY_IN_COURSE(){
        when(student.isEnrolledInSection(section)).thenReturn(true);
        assertEquals(RegistrationService.RegistrationResult.FAILED_ALREADY_IN_COURSE,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_ENROLLMENT_CLOSED(){
        when(student.isEnrolledInSection(section)).thenReturn(false);

        when(section.isEnrollmentOpen()).thenReturn(false);
        assertEquals(RegistrationService.RegistrationResult.FAILED_ENROLLMENT_CLOSED,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_SECTION_FULL(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);

        when(section.isEnrollmentFull()).thenReturn(true);
        when(section.isWaitListFull()).thenReturn(true);
        assertEquals(RegistrationService.RegistrationResult.FAILED_SECTION_FULL,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_SCHEDULE_CONFLICT_1(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
        when(section.isWaitListFull()).thenReturn(false);

        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(true);
        assertEquals(RegistrationService.RegistrationResult.FAILED_SCHEDULE_CONFLICT,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_SCHEDULE_CONFLICT_2(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
        when(section.isWaitListFull()).thenReturn(false);
        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(false);

        when(enrolledSection2.overlapsWith(section.getTimeSlot())).thenReturn(true);
        assertEquals(RegistrationService.RegistrationResult.FAILED_SCHEDULE_CONFLICT,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_PREREQUISITE_NOT_MET(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
        when(section.isWaitListFull()).thenReturn(false);
        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(enrolledSection2.overlapsWith(section.getTimeSlot())).thenReturn(false);

        when(prerequisite.isSatisfiedBy(student)).thenReturn(false);
        assertEquals(RegistrationService.RegistrationResult.FAILED_PREREQUISITE_NOT_MET,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_FAILED_CREDIT_LIMIT_VIOLATION(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
        when(section.isWaitListFull()).thenReturn(false);
        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(enrolledSection2.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);

        when(course.getCreditHours()).thenReturn(42);
        //any number large enough that will exceed the credit cap can replace 42
        assertEquals(RegistrationService.RegistrationResult.FAILED_CREDIT_LIMIT_VIOLATION,registrationService.register(student, section));
    }
    @Test
    void RegistrationResult_SUCCESS_WAIT_LISTED(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);

        when(section.isEnrollmentFull()).thenReturn(true);

        when(section.isWaitListFull()).thenReturn(false);
        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(enrolledSection2.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);
        when(course.getCreditHours()).thenReturn(0);

        assertEquals(RegistrationService.RegistrationResult.SUCCESS_WAIT_LISTED,registrationService.register(student, section));
        verify(student).addWaitListedSection(section);
    }
    @Test
    void RegistrationResult_SUCCESS_ENROLLED(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.isEnrollmentFull()).thenReturn(false);
        when(section.isWaitListFull()).thenReturn(false);
        when(enrolledSection1.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(enrolledSection2.overlapsWith(section.getTimeSlot())).thenReturn(false);
        when(prerequisite.isSatisfiedBy(student)).thenReturn(true);
        when(course.getCreditHours()).thenReturn(0);

        assertEquals(RegistrationService.RegistrationResult.SUCCESS_ENROLLED,registrationService.register(student, section));
        verify(student).addEnrolledSection(section);
    }

    @Test
    void DROP_ENROLLMENT_FALSE(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(false);
        assertFalse(registrationService.drop(student,section));
    }
    @Test
    void DROP_ENROLLMENT_WAITLIST(){
        when(student.isEnrolledInSection(section)).thenReturn(false);
        when(student.isWaitListedInSection(section)).thenReturn(true);

        assertTrue(registrationService.drop(student,section));
        verify(student).removeWaitListedSection(section);
        verify(section).removeStudentFromWaitList(student);
    }
    @Test
    void DROP_ENROLLMENT_ENROLLMENT_CLOSE(){
        when(student.isEnrolledInSection(section)).thenReturn(true);
        when(section.isEnrollmentOpen()).thenReturn(false);

        assertTrue(registrationService.drop(student,section));
        verify(student).removeEnrolledSection(section);
        verify(student).addGrade(section, Grade.DROP);
        verify(section).removeStudentFromEnrolled(student);
    }
    @Test
    void DROP_ENROLLMENT_ENROLLMENT_OPEN(){
        when(student.isEnrolledInSection(section)).thenReturn(true);
        when(section.isEnrollmentOpen()).thenReturn(true);
        when(section.getFirstStudentOnWaitList()).thenReturn(firstWaitListStudent);

        assertTrue(registrationService.drop(student,section));
        verify(student).removeEnrolledSection(section);
        verify(student).addGrade(section, Grade.DROP);
        verify(section).removeStudentFromEnrolled(student);

        verify(section).addStudentToEnrollment(firstWaitListStudent);
        verify(section).removeStudentFromWaitList(firstWaitListStudent);
        verify(firstWaitListStudent).removeWaitListedSection(section);
        verify(firstWaitListStudent).addEnrolledSection(section);
    }

}
