package sde.virginia.edu.hw4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {
    private Location location;
    @BeforeEach
    public void setup() {
        location = new Location("Nau Hall", "101", 245);
    }
    @Test
    void building() {
        assertEquals("Nau Hall", location.building());
    }

    @Test
    void room() {
        assertEquals("101", location.room());
    }

    @Test
    void roomCapacity() {
        assertEquals(245, location.roomCapacity());
    }

    @Test
    void badConstructor_roomCapacity() {
        assertThrows(IllegalArgumentException.class, () ->
                new Location("Rice", "404", -1));
    }
}