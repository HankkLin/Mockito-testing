package sde.virginia.edu.hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleTest {
    @Mock
    Section sdeSection, algorithmsSection;

    @Mock
    Set<Section> enrolledSections, waitListedSections;

    Schedule schedule;

    @Test
    void getEnrolledSections() {
        enrolledSections = new HashSet<>(Set.of(sdeSection, algorithmsSection));
        schedule = new Schedule(enrolledSections, waitListedSections);

        var outputEnrolledSections = schedule.getEnrolledSections();
        assertEquals(2, outputEnrolledSections.size());
        assertTrue(outputEnrolledSections.contains(sdeSection));
        assertTrue(outputEnrolledSections.contains(algorithmsSection));
    }

    @Test
    void addToEnrolled_true() {
        when(enrolledSections.add(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.addEnrolledSection(sdeSection));

        verify(enrolledSections).add(sdeSection);
    }

    @Test
    void addToEnrolled_false() {
        when(enrolledSections.add(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.addEnrolledSection(sdeSection));

        verify(enrolledSections).add(sdeSection);
    }

    @Test
    void removeFromEnrolled_true() {
        when(enrolledSections.remove(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.removeEnrolledSection(sdeSection));

        verify(enrolledSections).remove(sdeSection);
    }

    @Test
    void removeFromEnrolled_false() {
        when(enrolledSections.remove(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.removeEnrolledSection(sdeSection));

        verify(enrolledSections).remove(sdeSection);
    }

    @Test
    void isEnrolledInSection_true() {
        when(enrolledSections.contains(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.isEnrolledInSection(sdeSection));
    }

    @Test
    void isEnrolledInSection_false() {
        when(enrolledSections.contains(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.isEnrolledInSection(sdeSection));
    }

    @Test
    void isEnrolledInCourse_true() {
        var sdeCourse = mock(Course.class);
        var algorithmsCourse = mock(Course.class);
        when(sdeSection.getCourse()).thenReturn(sdeCourse);
        lenient().when(algorithmsSection.getCourse()).thenReturn(algorithmsCourse);
        enrolledSections = Set.of(sdeSection, algorithmsSection);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.isEnrolledInCourse(sdeCourse));
    }

    @Test
    void isEnrolledInCourse_false() {
        var sdeCourse = mock(Course.class);
        var algorithmsCourse = mock(Course.class);
        when(sdeSection.getCourse()).thenReturn(sdeCourse);
        enrolledSections = Set.of(sdeSection);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.isEnrolledInCourse(algorithmsCourse));
    }

    @Test
    void getWaitListedSections() {
        waitListedSections = new HashSet<>(Set.of(sdeSection, algorithmsSection));
        schedule = new Schedule(enrolledSections, waitListedSections);

        var outputEnrolledSections = schedule.getWaitListedSections();
        assertEquals(2, outputEnrolledSections.size());
        assertTrue(outputEnrolledSections.contains(sdeSection));
        assertTrue(outputEnrolledSections.contains(algorithmsSection));
    }

    @Test
    void addToWaitListed_true() {
        when(waitListedSections.add(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.addWaitListedSection(sdeSection));

        verify(waitListedSections).add(sdeSection);
    }

    @Test
    void addToWaitListed_false() {
        when(waitListedSections.add(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.addWaitListedSection(sdeSection));

        verify(waitListedSections).add(sdeSection);
    }

    @Test
    void removeFromWaitListed_true() {
        when(waitListedSections.remove(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.removeWaitListedSection(sdeSection));

        verify(waitListedSections).remove(sdeSection);
    }

    @Test
    void removeFromWaitListed_false() {
        when(waitListedSections.remove(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.removeWaitListedSection(sdeSection));

        verify(waitListedSections).remove(sdeSection);
    }

    @Test
    void isWaitListedInSection_true() {
        when(waitListedSections.contains(sdeSection)).thenReturn(true);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertTrue(schedule.isWaitListedInSection(sdeSection));
    }

    @Test
    void isWaitListedInSection_false() {
        when(waitListedSections.contains(sdeSection)).thenReturn(false);
        schedule = new Schedule(enrolledSections, waitListedSections);

        assertFalse(schedule.isWaitListedInSection(sdeSection));
    }
}