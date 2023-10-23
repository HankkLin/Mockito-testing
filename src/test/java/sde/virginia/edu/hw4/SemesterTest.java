package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SemesterTest {
    Semester semester;

    @BeforeEach
    public void setup() {
        semester = new Semester(Term.FALL, 2023);
    }

    @Test
    void term() {
        assertEquals(Term.FALL, semester.term());
    }

    @Test
    void year() {
        assertEquals(2023, semester.year());
    }
}