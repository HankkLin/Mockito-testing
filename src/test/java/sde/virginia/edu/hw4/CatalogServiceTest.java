package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatalogServiceTest {
    private CatalogService catalogService;
    @Mock
    Section section, sectionGoingToBeAdd, sectionGoingToBeRemove;
    @Mock
    Catalog catalog;
    @Mock
    Student student;
    Location location, newLocation;
    //can't mock location, don't know why
    @Mock
    Lecturer lecturer;
    @BeforeEach
    public void setUp(){
        catalogService = new CatalogService(catalog);
        location = new Location("Rice","101",10);
        newLocation = new Location("Rice","102",10);
    }
    @Test
    void AddSectionResult_FAILED_SECTION_ALREADY_EXISTS(){
        when(catalog.contains(sectionGoingToBeAdd)).thenReturn(true);
        assertEquals(CatalogService.AddSectionResult.FAILED_SECTION_ALREADY_EXISTS,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_FAILED_CRN_CONFLICT(){
        when(sectionGoingToBeAdd.getCourseRegistrationNumber()).thenReturn(1);
        when(catalog.getSectionByCRN(1)).thenReturn(Optional.of(section));
        assertEquals(CatalogService.AddSectionResult.FAILED_CRN_CONFLICT,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_FAILED_LOCATION_CONFLICT(){
        when(sectionGoingToBeAdd.overlapsWith(section.getTimeSlot())).thenReturn(true);
        when(catalog.getSections()).thenReturn(new HashSet<>(Set.of(section)));
        when(sectionGoingToBeAdd.getLocation()).thenReturn(location);
        when(section.getLocation()).thenReturn(location);

        assertEquals(CatalogService.AddSectionResult.FAILED_LOCATION_CONFLICT,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_FAILED_LECTURER_CONFLICT(){
        when(sectionGoingToBeAdd.overlapsWith(section.getTimeSlot())).thenReturn(true);
        when(catalog.getSections()).thenReturn(new HashSet<>(Set.of(section)));

        when(sectionGoingToBeAdd.getLocation()).thenReturn(newLocation);
        when(section.getLocation()).thenReturn(location);

        when(sectionGoingToBeAdd.getLecturer()).thenReturn(lecturer);
        when(section.getLecturer()).thenReturn(lecturer);

        assertEquals(CatalogService.AddSectionResult.FAILED_LECTURER_CONFLICT,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_FAILED_ENROLLMENT_NOT_EMPTY_ENROLL(){
        when(sectionGoingToBeAdd.getEnrollmentSize()).thenReturn(1);
        assertEquals(CatalogService.AddSectionResult.FAILED_ENROLLMENT_NOT_EMPTY,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_FAILED_ENROLLMENT_NOT_EMPTY_WAITLIST(){
        when(sectionGoingToBeAdd.getWaitListSize()).thenReturn(1);
        assertEquals(CatalogService.AddSectionResult.FAILED_ENROLLMENT_NOT_EMPTY,catalogService.add(sectionGoingToBeAdd));
        verify(catalog,times(0)).add(sectionGoingToBeAdd);
    }
    @Test
    void AddSectionResult_SUCCESSFUL(){
        assertEquals(CatalogService.AddSectionResult.SUCCESSFUL,catalogService.add(sectionGoingToBeAdd));
        verify(catalog).add(sectionGoingToBeAdd);
    }
    @Test
    void removeSection_sectionNotFound(){
        assertThrows(NoSuchElementException.class,()->catalogService.removeSection(sectionGoingToBeRemove));
        verify(catalog).remove(sectionGoingToBeRemove);
    }
    @Test
    void removeSection_studentEnroll(){
        when(catalog.remove(sectionGoingToBeRemove)).thenReturn(true);
        when(sectionGoingToBeRemove.getEnrolledStudents()).thenReturn(new HashSet<>(Set.of(student)));

        catalogService.removeSection(sectionGoingToBeRemove);
        for(Student enrollStudent: sectionGoingToBeRemove.getEnrolledStudents()){
            verify(enrollStudent).removeEnrolledSection(sectionGoingToBeRemove);
            verify(sectionGoingToBeRemove).removeStudentFromEnrolled(enrollStudent);
        }
    }
    @Test
    void removeSection_studentWaitList(){
        when(catalog.remove(sectionGoingToBeRemove)).thenReturn(true);
        when(sectionGoingToBeRemove.getWaitListedStudents()).thenReturn(Collections.singletonList(student));

        catalogService.removeSection(sectionGoingToBeRemove);
        for(Student waitListStudent: sectionGoingToBeRemove.getWaitListedStudents()){
            verify(waitListStudent).removeWaitListedSection(sectionGoingToBeRemove);
            verify(sectionGoingToBeRemove).removeStudentFromWaitList(waitListStudent);
        }
    }


}
