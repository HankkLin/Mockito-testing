package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.beans.Transient;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentTest {

    @Mock
    private Transcript transcript;
    @Mock
    private Schedule schedule;
    @Mock
    private Set<Section> mockSectionSet;
    @Mock
    private Section mockSection;

    private Student student;



    @BeforeEach
    void setUp() {
        student = new Student(123456789, "abc2def", "John", "Doe", 1, schedule, transcript);
    }

    @Test
    void getId() {
        assertEquals(123456789, student.getId());
    }

    @Test
    void getComputingId() {
        assertEquals("abc2def", student.getComputingId());
    }

    @Test
    void getFirstName() {
        assertEquals("John", student.getFirstName());
    }

    @Test
    void setFirstName() {
        student.setFirstName("Jack");
        assertEquals("Jack", student.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("Doe", student.getLastName());
    }

    @Test
    void setLastName() {
        student.setLastName("Dough");
        assertEquals("Dough", student.getLastName());
    }

    @Test
    void getYear() {
        assertEquals(1, student.getYear());
    }

    @Test
    void setYear() {
        student.setYear(2);
        assertEquals(2, student.getYear());
    }

    @Test
    void getEnrolledSections() {
        when(schedule.getEnrolledSections()).thenReturn(mockSectionSet);
        assertEquals(mockSectionSet, student.getEnrolledSections());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void addEnrolledSection(boolean input) {
        when(schedule.addEnrolledSection(mockSection)).thenReturn(input);

        assertEquals(input, student.addEnrolledSection(mockSection));

        verify(schedule).addEnrolledSection(mockSection);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void removeEnrolledSection(boolean input) {
        when(schedule.removeEnrolledSection(mockSection)).thenReturn(input);

        assertEquals(input, student.removeEnrolledSection(mockSection));

        verify(schedule).removeEnrolledSection(mockSection);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isEnrolledInSection(boolean input) {
        when(schedule.isEnrolledInSection(mockSection)).thenReturn(input);

        assertEquals(input, student.isEnrolledInSection(mockSection));

        verify(schedule).isEnrolledInSection(mockSection);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isEnrolledInCourse(boolean input) {
        var mockCourse = mock(Course.class);
        when(schedule.isEnrolledInCourse(mockCourse)).thenReturn(input);

        assertEquals(input, student.isEnrolledInCourse(mockCourse));

        verify(schedule).isEnrolledInCourse(mockCourse);
    }

    @Test
    void getWaitListedSections() {
        when(schedule.getWaitListedSections()).thenReturn(mockSectionSet);
        assertEquals(mockSectionSet, student.getWaitListedSections());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void addWaitListedSection(boolean input) {
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void removeWaitListedSection(boolean input) {
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isWaitListedInSection(boolean input) {
        when(schedule.isWaitListedInSection(mockSection)).thenReturn(input);

        assertEquals(input, student.isWaitListedInSection(mockSection));

        verify(schedule).isWaitListedInSection(mockSection);
    }

    @Test
    void getGrade() {
        when(transcript.getGrade(mockSection)).thenReturn(Grade.B);
        assertEquals(Grade.B, student.getGrade(mockSection));
    }

    @Test
    void getBestGrade_present() {
        var mockCourse = mock(Course.class);
        when(transcript.getBestGrade(mockCourse)).thenReturn(Optional.of(Grade.C));

        var bestGrade = student.getBestGrade(mockCourse);
        assertTrue(bestGrade.isPresent());
        assertEquals(Grade.C, bestGrade.get());
    }

    @Test
    void getBestGrade_absent() {
        var mockCourse = mock(Course.class);
        when(transcript.getBestGrade(mockCourse)).thenReturn(Optional.empty());

        assertEquals(Optional.empty(), student.getBestGrade(mockCourse));
    }

    @Test
    void addGrade() {
        student.addGrade(mockSection, Grade.C);

        verify(transcript).add(mockSection, Grade.C);
    }

    @Test
    void getTranscriptSections() {
        when(transcript.getSections()).thenReturn(mockSectionSet);

        assertEquals(mockSectionSet, student.getTranscriptSections());
    }

    @Test
    void getGPA() {
        when(transcript.getGPA()).thenReturn(3.7);
        assertEquals(3.7, student.getGPA());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void isOnProbation(boolean input) {
        when(transcript.isOnProbation()).thenReturn(input);
        assertEquals(input, student.isOnProbation());
    }

    @Test
    void getCreditLimit_normal() {
        when(transcript.isOnProbation()).thenReturn(false);
        assertEquals(Schedule.DEFAULT_CREDIT_LIMIT, student.getCreditLimit());
    }

    @Test
    void getCreditLimit_probation() {
        when(transcript.isOnProbation()).thenReturn(true);
        assertEquals(Schedule.PROBATION_CREDIT_LIMIT, student.getCreditLimit());
    }

}