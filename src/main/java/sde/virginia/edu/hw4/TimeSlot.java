package sde.virginia.edu.hw4;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a time slot for a class, such as MWF 14:00-14:50 (Monday, Wednesday, and Friday starting at 14:00 [2p.m.])
 */
public class TimeSlot {

    public static final Set<DayOfWeek> MONDAY_WEDNESDAY_FRIDAY =
            Set.of(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY);
    public static final Set<DayOfWeek> TUESDAY_THURSDAY =
            Set.of(DayOfWeek.TUESDAY, DayOfWeek.THURSDAY);
    private final Set<DayOfWeek> days;
    private final int startTimeHour;
    private final int startTimeMinute;
    private final int endTimeHour;
    private final int endTimeMinute;


    public TimeSlot(Set<DayOfWeek> days, int startTimeHour, int startTimeMinute, int endTimeHour, int endTimeMinute) {
        if (days == null) {
            throw new IllegalArgumentException("Cannot make a TimeSlot with null days");
        }
        if (days.isEmpty()) {
            throw new IllegalArgumentException("Cannot make a TimeSlot with no days");
        }
        var startTimeInMinutes = getTimeInMinutes(startTimeHour, startTimeMinute);
        var endTimeInMinutes = getTimeInMinutes(endTimeHour, endTimeMinute);
        if (endTimeInMinutes < startTimeInMinutes) {
            throw new IllegalArgumentException("Invalid TimeSlot: End Time earlier than Start Time");
        }
        this.days = days;
        this.startTimeHour = startTimeHour;
        this.startTimeMinute = startTimeMinute;
        this.endTimeHour = endTimeHour;
        this.endTimeMinute = endTimeMinute;
    }

    public Set<DayOfWeek> days() {
        return Collections.unmodifiableSet(days);
    }

    public boolean overlapsWith(TimeSlot other) {
        var thisDays = new HashSet<DayOfWeek>(this.days);
        var otherDays = new HashSet<DayOfWeek>(other.days);
        thisDays.retainAll(otherDays);
        if (thisDays.isEmpty()) {
            return false;
        }
        var thisStartTime = getTimeInMinutes(startTimeHour, startTimeMinute);
        var thisEndTime = getTimeInMinutes(endTimeHour, endTimeMinute);
        var otherStartTime = getTimeInMinutes(other.startTimeHour, other.startTimeMinute);
        var otherEndTime = getTimeInMinutes(other.endTimeHour, other.endTimeMinute);

        return (otherStartTime <= thisStartTime && thisStartTime < otherEndTime) ||
                (thisStartTime <= otherStartTime && otherStartTime < thisEndTime);
    }

    private static int getTimeInMinutes(int hour, int minutes) {
        return 60 * hour + minutes;
    }

    public int startTimeHour() {
        return startTimeHour;
    }

    public int startTimeMinute() {
        return startTimeMinute;
    }

    public int endTimeHour() {
        return endTimeHour;
    }

    public int endTimeMinute() {
        return endTimeMinute;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (TimeSlot) obj;
        return Objects.equals(this.days, that.days) &&
                this.startTimeHour == that.startTimeHour &&
                this.startTimeMinute == that.startTimeMinute &&
                this.endTimeHour == that.endTimeHour &&
                this.endTimeMinute == that.endTimeMinute;
    }

    @Override
    public int hashCode() {
        return Objects.hash(days, startTimeHour, startTimeMinute, endTimeHour, endTimeMinute);
    }

    @Override
    public String toString() {
        return "TimeSlot[" +
                "days=" + days + ", " +
                "startTimeHour=" + startTimeHour + ", " +
                "startTimeMinute=" + startTimeMinute + ", " +
                "endTimeHour=" + endTimeHour + ", " +
                "endTimeMinute=" + endTimeMinute + ']';
    }

}
