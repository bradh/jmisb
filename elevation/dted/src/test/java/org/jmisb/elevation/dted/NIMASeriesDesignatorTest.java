package org.jmisb.elevation.dted;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for NIMASeriesDesignator. */
public class NIMASeriesDesignatorTest {

    public NIMASeriesDesignatorTest() {}

    @Test
    public void dted0() {
        NIMASeriesDesignator d0 = NIMASeriesDesignator.DTED0;
        assertEquals(d0.getDisplayName(), "DTED Level 0");
    }

    @Test
    public void dted1() {
        NIMASeriesDesignator d1 = NIMASeriesDesignator.DTED1;
        assertEquals(d1.getDisplayName(), "DTED Level 1");
    }

    @Test
    public void dted2() {
        NIMASeriesDesignator d2 = NIMASeriesDesignator.DTED2;
        assertEquals(d2.getDisplayName(), "DTED Level 2");
    }

    @Test
    public void dted1FromCode() {
        NIMASeriesDesignator d1 = NIMASeriesDesignator.getEnumValue("DTED1");
        assertEquals(d1, NIMASeriesDesignator.DTED1);
        assertEquals(d1.getDisplayName(), "DTED Level 1");
    }

    @Test
    public void unknown() {
        NIMASeriesDesignator d1 = NIMASeriesDesignator.getEnumValue("abcd1");
        assertEquals(d1, NIMASeriesDesignator.Unknown);
        assertEquals(d1.getDisplayName(), "Unknown");
    }
}
