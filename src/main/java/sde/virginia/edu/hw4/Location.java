package sde.virginia.edu.hw4;

/**
 * Represents a location (typically classroom) for a class
 * @param building the name of the building the class is in
 * @param room the specific room in the building the class is located. Represented as a {@link String} because some
 *             rooms contain letters (i.e., 301A, 301B, etc.)
 * @param roomCapacity the fire-code capacity of the room. No class in this room can have a larger enrollment cap.
 */
public record Location(String building, String room, int roomCapacity) {
    public Location {
        if (building == null || room == null || roomCapacity < 0) {
            throw new IllegalArgumentException("Invalid location");
        }
    }
}
