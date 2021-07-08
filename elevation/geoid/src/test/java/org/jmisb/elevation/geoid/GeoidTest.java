package org.jmisb.elevation.geoid;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for Geoid. */
public class GeoidTest {

    public GeoidTest() {}

    @Test
    public void checkBaseRow90() {
        assertEquals(Geoid.getBaseRow(90.0), 0);
    }

    @Test
    public void checkBaseRowOffset90() {
        assertEquals(Geoid.getOffsetFromBaseRow(90.0, 0), 0);
    }

    @Test
    public void checkBaseRowJustBelow90() {
        assertEquals(Geoid.getBaseRow(89.999), 0);
    }

    @Test
    public void checkBaseRowOffsetJustBelow90() {
        assertEquals(Geoid.getOffsetFromBaseRow(89.999, 0), 0.001, 0.0000000001);
    }

    @Test
    public void checkBaseRowJustIntoSecondRow() {
        assertEquals(Geoid.getBaseRow(89.749999), 1);
    }

    @Test
    public void checkBaseRowOffsetJustIntoSecondRow() {
        assertEquals(Geoid.getOffsetFromBaseRow(89.7499, 1), 0.0001, 0.00000001);
    }

    @Test
    public void checkBaseRowAlmostIntoThirdRow() {
        assertEquals(Geoid.getBaseRow(89.5000001), 1);
    }

    @Test
    public void checkBaseRowOffsetAlmostIntoThirdRow() {
        assertEquals(Geoid.getOffsetFromBaseRow(89.5001, 1), 0.2499, 0.000000001);
    }

    @Test
    public void checkBaseRowJustIntoThirdRow() {
        assertEquals(Geoid.getBaseRow(89.49999999), 2);
    }

    @Test
    public void checkBaseRowOffsetJustIntoThirdRow() {
        assertEquals(Geoid.getOffsetFromBaseRow(89.499, 2), 0.001, 0.0000000001);
    }
}
