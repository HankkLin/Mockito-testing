package sde.virginia.edu.hw4;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TranscriptTest {

    @Mock
    HashMap<Section, Grade> history;
    @Mock
    Section mockSection, sectionAlgorithmsFail, sectionAlgorithmsPass, sectionSde, sectionDsa1;
    @Mock
    Course algorithms, sde, dsa1;



    @Test
    void getGrade() {
        when(history.containsKey(mockSection)).thenReturn(true);
        when(history.get(mockSection)).thenReturn(Grade.A);
        var transcript = new Transcript(history);

        assertEquals(Grade.A, transcript.getGrade(mockSection));
    }

    @Test
    void getGrade_notPresent() {
        when(history.containsKey(mockSection)).thenReturn(false);
        var transcript = new Transcript(history);

        assertThrows(IllegalArgumentException.class, () -> transcript.getGrade(mockSection));
    }

    @Test
    void add() {
        var transcript = new Transcript(history);

        transcript.add(mockSection, Grade.C);

        verify(history).put(mockSection, Grade.C);
    }

    @Test
    void getSections() {
        history = new HashMap<>(Map.of(mockSection, Grade.B_PLUS));
        var transcript = new Transcript(history);

        var sectionSet = transcript.getSections();

        assertEquals(1, sectionSet.size());
        assertTrue(sectionSet.contains(mockSection));
    }

    @Test
    void getBestGrade() {
        //setup section history
        history = new HashMap<>(Map.of(
                sectionAlgorithmsFail, Grade.F,
                sectionAlgorithmsPass, Grade.B_PLUS,
                sectionSde, Grade.A
        ));
        //get course for each mock section
        when(sectionAlgorithmsFail.getCourse()).thenReturn(algorithms);
        when(sectionAlgorithmsPass.getCourse()).thenReturn(algorithms);
        when(sectionSde.getCourse()).thenReturn(sde);
        var transcript = new Transcript(history);

        var bestGradeOptional = transcript.getBestGrade(algorithms);

        assertTrue(bestGradeOptional.isPresent());
        assertEquals(Grade.B_PLUS, bestGradeOptional.get());
    }

    @Test
    void getBestGrade_notPresent() {
        history = new HashMap<>(Map.of(
                sectionAlgorithmsFail, Grade.F,
                sectionAlgorithmsPass, Grade.B_PLUS
        ));
        //get course for each mock section
        when(sectionAlgorithmsFail.getCourse()).thenReturn(algorithms);
        when(sectionAlgorithmsPass.getCourse()).thenReturn(algorithms);
        var transcript = new Transcript(history);

        assertEquals(Optional.empty(), transcript.getBestGrade(sde));
    }

    @Test
    void getGPA() {
        history = new HashMap<>(Map.of(
                sectionAlgorithmsFail, Grade.F, // 4 credit hours, 0.0
                sectionAlgorithmsPass, Grade.C, // 4 credit hours, 2.0
                sectionSde, Grade.A,            // 3 credit hours, 4.0
                sectionDsa1, Grade.COVID_CR     // doesn't affect GPA
        ));
        //connect sections to courses
        when(sectionAlgorithmsFail.getCourse()).thenReturn(algorithms);
        when(sectionAlgorithmsPass.getCourse()).thenReturn(algorithms);
        when(sectionSde.getCourse()).thenReturn(sde);
        //connect courses to credit hours
        when(algorithms.getCreditHours()).thenReturn(4);
        when(sde.getCreditHours()).thenReturn(3);

        var transcript = new Transcript(history);
        var expectedGradePoints = (4 * 0.0) + (4 * 2.0) + (3 * 4.0);
        var expectedGradeCredits = 4 + 4 + 3;
        var expectedGPA = expectedGradePoints / expectedGradeCredits;
        assertEquals(expectedGPA, transcript.getGPA());
    }
}