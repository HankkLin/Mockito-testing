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

@ExtendWith(MockitoExtension.class)
class CatalogTest {

    private Catalog catalog;

    private Semester semester;
    @Mock
    Set<Section> sections;
    @Mock
    Section sectionSde, sectionAlgorithms;

    @BeforeEach
    public void setup() {
        semester = new Semester(Term.FALL, 2023);
        catalog = new Catalog(semester, sections);
    }

    @Test
    void getSemester() {
        assertEquals(new Semester(Term.FALL, 2023), catalog.getSemester());
    }

    @Test
    void getSections() {
        sections = new HashSet<>(Set.of(sectionSde, sectionAlgorithms));
        catalog = new Catalog(semester, sections);
        var returnedSet = catalog.getSections();

        assertEquals(returnedSet.size(), 2);
        assertTrue(returnedSet.contains(sectionSde));
        assertTrue(returnedSet.contains(sectionAlgorithms));
    }

    @Test
    void add_true() {
        when(sectionSde.getSemester()).thenReturn(semester);

        catalog.add(sectionSde);

        verify(sections).add(sectionSde);
    }

    @Test
    void add_false() {
        sections = new HashSet<>(Set.of(sectionSde));
        when(sectionSde.getSemester()).thenReturn(semester);

        assertFalse(catalog.add(sectionSde));

        assertEquals(1, sections.size());
    }

    @Test
    void add_exception() {
        when(sectionSde.getSemester()).thenReturn(new Semester(Term.SPRING, 2023));

        assertThrows(IllegalArgumentException.class, () -> catalog.add(sectionSde));

        verify(sections, never()).add(sectionSde);
    }

    @Test
    void remove() {
        when(sections.remove(sectionSde)).thenReturn(true);

        assertTrue(catalog.remove(sectionSde));

        verify(sections).remove(sectionSde);
    }

    @Test
    void contains() {
        when(sections.contains(sectionSde)).thenReturn(true);

        assertTrue(catalog.contains(sectionSde));
    }
}