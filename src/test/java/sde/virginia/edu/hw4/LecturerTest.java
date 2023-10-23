package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LecturerTest {
    public Lecturer lecturer;

    @BeforeEach
    public void setup() {
        lecturer = new Lecturer(1234, "pm8fc", "Paul", "McBurney");
    }
    @Test
    void getId() {
        assertEquals(1234, lecturer.getId());
    }

    @Test
    void getComputingId() {
        assertEquals("pm8fc", lecturer.getComputingId());
    }

    @Test
    void getFirstName() {
        assertEquals("Paul", lecturer.getFirstName());
    }

    @Test
    void setFirstName() {
        lecturer.setFirstName("Will");
        assertEquals("Will", lecturer.getFirstName());
    }

    @Test
    void getLastName() {
        assertEquals("McBurney", lecturer.getLastName());
    }

    @Test
    void setLastName() {
        lecturer.setLastName("THE MOST HUMBLE MAN IN THE ENTIRE WORLD!");
        assertEquals("THE MOST HUMBLE MAN IN THE ENTIRE WORLD!", lecturer.getLastName());
    }
}