package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseTest {
    private Course course;

    @Mock
    private Prerequisite prerequisite;
    @BeforeEach
    public void setUp() {
        course = new Course(1, "CS", "3140",
                "Software Development Essentials", 3, prerequisite);
    }

    @Test
    void getId() {
        assertEquals(1, course.getId());
    }

    @Test
    void getMnemonic() {
        assertEquals("CS", course.getMnemonic());
    }

    @Test
    void getCourseNumber() {
        assertEquals("3140", course.getCourseNumber());
    }

    @Test
    void getTitle() {
        assertEquals("Software Development Essentials", course.getTitle());
    }

    @Test
    void getCreditHours() {
        assertEquals(3, course.getCreditHours());
    }

    @Test
    void getPrerequisite() {
        assertEquals(prerequisite, course.getPrerequisite());
    }
}