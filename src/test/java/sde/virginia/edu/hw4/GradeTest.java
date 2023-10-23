package sde.virginia.edu.hw4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    @Test
    void getGradePoints() {
        assertEquals(3.7, Grade.A_MINUS.getGradePoints().orElseThrow());
    }

    @Test
    void getGradePoints_noPoints() {
        assertTrue(Grade.W.getGradePoints().isEmpty());
    }

    @Test
    void getPrerequisiteScore() {
        assertEquals(1, Grade.D_MINUS.getPrerequisiteScore());
    }

    @Test
    void isWorthCredit_true() {
        assertTrue(Grade.D_MINUS.isWorthCredit());
    }

    @Test
    void isWorthCredit_false() {
        assertFalse(Grade.F.isWorthCredit());
    }

    @Test
    void meetsMinimumRequirement_higher() {
        assertTrue(Grade.B_PLUS.greaterThanOrEqualTo(Grade.B));
    }

    @Test
    void meetsMinimumRequirement_equal() {
        assertTrue(Grade.B.greaterThanOrEqualTo(Grade.B));
    }

    @Test
    void meetsMinimumRequirement_lower() {
        assertFalse(Grade.B_MINUS.greaterThanOrEqualTo(Grade.B));
    }

    @Test
    void testToString_plus() {
        assertEquals("C+", Grade.C_PLUS.toString());
    }

    @Test
    void testToString_minus() {
        assertEquals("C-", Grade.C_MINUS.toString());
    }
}