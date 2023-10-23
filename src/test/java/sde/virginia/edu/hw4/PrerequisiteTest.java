package sde.virginia.edu.hw4;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PrerequisiteTest {
    @Mock
    private Course dsa1, dmt1;
    @Mock
    Map<Course, Grade> requiredCourses;

    private Prerequisite prerequisite;

    @Test
    void add() {
        prerequisite = new Prerequisite(requiredCourses);

        prerequisite.add(dsa1, Grade.C_MINUS);

        verify(requiredCourses).put(dsa1, Grade.C_MINUS);
    }

    @Test
    void getMinimumGrade() {
        when(requiredCourses.containsKey(dsa1)).thenReturn(true);
        when(requiredCourses.get(dsa1)).thenReturn(Grade.C_MINUS);
        prerequisite = new Prerequisite(requiredCourses);

        assertEquals(Grade.C_MINUS, prerequisite.getMinimumGrade(dsa1));
    }

    @Test
    void getMinimumGrade_Exception() {
        when(requiredCourses.containsKey(dsa1)).thenReturn(false);
        prerequisite = new Prerequisite(requiredCourses);

        assertThrows(IllegalArgumentException.class, () -> prerequisite.getMinimumGrade(dsa1));
    }

    @Test
    void remove() {
        when(requiredCourses.containsKey(dsa1)).thenReturn(true);
        prerequisite = new Prerequisite(requiredCourses);

        prerequisite.remove(dsa1);

        verify(requiredCourses).remove(dsa1);
    }

    @Test
    void remove_Exception() {
        when(requiredCourses.containsKey(dsa1)).thenReturn(false);
        prerequisite = new Prerequisite(requiredCourses);

        assertThrows(IllegalArgumentException.class, () -> prerequisite.remove(dsa1));

        verify(requiredCourses, never()).remove(dsa1);
    }

    @Test
    void getPrerequisiteCourses() {
        requiredCourses = Map.of(dmt1, Grade.C_MINUS, dsa1, Grade.C_MINUS);
        prerequisite = new Prerequisite(requiredCourses);

        var courseSet = prerequisite.getPrerequisiteCourses();

        assertEquals(2, courseSet.size());
        assertTrue(courseSet.contains(dsa1));
        assertTrue(courseSet.contains(dmt1));
    }

    @Test
    void isMetBy_true() {
        var student = mock(Student.class);
        when(student.getBestGrade(dsa1)).thenReturn(Optional.of(Grade.A));
        when(student.getBestGrade(dmt1)).thenReturn(Optional.of(Grade.C_MINUS));

        requiredCourses = Map.of(dsa1, Grade.C_MINUS, dmt1, Grade.C_MINUS);
        prerequisite = new Prerequisite(requiredCourses);

        assertTrue(prerequisite.isSatisfiedBy(student));
    }

    @Test
    void isMetBy_false_lowGrade() {
        var student = mock(Student.class);
        when(student.getBestGrade(dsa1)).thenReturn(Optional.of(Grade.D_PLUS));
        lenient().when(student.getBestGrade(dmt1)).thenReturn(Optional.of(Grade.A));

        requiredCourses = Map.of(dsa1, Grade.C_MINUS, dmt1, Grade.C_MINUS);
        prerequisite = new Prerequisite(requiredCourses);

        assertFalse(prerequisite.isSatisfiedBy(student));
    }

    @Test
    void isMetBy_false_missingClass() {
        var student = mock(Student.class);
        when(student.getBestGrade(dmt1)).thenReturn(Optional.empty());
        lenient().when(student.getBestGrade(dsa1)).thenReturn(Optional.of(Grade.A));

        requiredCourses = Map.of(dsa1, Grade.C_MINUS, dmt1, Grade.C_MINUS);
        prerequisite = new Prerequisite(requiredCourses);

        assertFalse(prerequisite.isSatisfiedBy(student));
    }
}