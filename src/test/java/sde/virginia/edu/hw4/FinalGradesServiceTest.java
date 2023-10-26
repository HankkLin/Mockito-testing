package sde.virginia.edu.hw4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
public class FinalGradesServiceTest {

    @Mock
    private Section section;
    @Mock
    private Student student1, student2;
    private Student student3 = new Student(1,"33","Han","Lin",2025);
    private Grade grade3 = Grade.A;
    @Mock
    private Grade grade1, grade2;
    @Mock
    private Map<Student, Grade> finalGradeMockMap;
    @BeforeEach
    public void setUp(){
        student1 = mock(Student.class);
        student2 = mock(Student.class);
        grade1 = (Grade.A);
        grade2 = (Grade.A);
    }
    @Test
    public void testUploadFinalGrades() {
        FinalGradesService finalGradesService = new FinalGradesService();
        Map<Student, Grade> finalGrades = new HashMap<>();
        finalGrades.put(student1,grade1);
        finalGrades.put(student2,grade2);

//        finalGradeMockMap = new HashMap<>(Map.of(student1,grade1,student2,grade2));
        finalGradesService.uploadFinalGrades(section, finalGrades);

        verify(student1, times(1)).addGrade(section, grade1);
        verify(student2, times(1)).addGrade(section, grade2);
    }
}
