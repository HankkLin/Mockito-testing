package sde.virginia.edu.hw4;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TimeSlotTest {

    @Test
    public void constructorTest() {
        var timeSlot = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);

        assertEquals(TimeSlot.MONDAY_WEDNESDAY_FRIDAY, timeSlot.days());
        assertEquals(12, timeSlot.startTimeHour());
        assertEquals(0, timeSlot.startTimeMinute());
        assertEquals(12, timeSlot.endTimeHour());
        assertEquals(50, timeSlot.endTimeMinute());
    }

    @Test
    public void invalidConstructor_earlierEndTime() {
        assertThrows(IllegalArgumentException.class, () ->
                new TimeSlot(TimeSlot.TUESDAY_THURSDAY, 12, 0, 11, 59));
    }

    @Test
    public void isOverlap_noTimeOverlap() {
        var timeSlotA = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);
        var timeSlotB = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                13, 0, 13, 50);
        assertFalse(timeSlotA.overlapsWith(timeSlotB));
    }

    @Test
    public void isOverlap_timeOverlap_differentDays() {
        var timeSlotA = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);
        var timeSlotB = new TimeSlot(TimeSlot.TUESDAY_THURSDAY,
                12, 0, 12, 50);
        assertFalse(timeSlotA.overlapsWith(timeSlotB));
    }

    @Test
    public void isOverlap_sameTimeSlot() {
        var timeSlotA = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);
        assertTrue(timeSlotA.overlapsWith(timeSlotA));
    }
    @Test
    public void isOverlap_oneMinuteOverlap() {
        var timeSlotA = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);
        var timeSlotB = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                11, 30, 12, 01);
        assertTrue(timeSlotA.overlapsWith(timeSlotB));
    }

    @Test
    public void isOverlap_oneMinuteOverlap_mirrored() {
        var timeSlotA = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                12, 0, 12, 50);
        var timeSlotB = new TimeSlot(TimeSlot.MONDAY_WEDNESDAY_FRIDAY,
                11, 30, 12, 01);
        assertTrue(timeSlotB.overlapsWith(timeSlotA));
    }
}