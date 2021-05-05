package org.jmisb.elevation.dted;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for SecurityClassification enum. */
public class SecurityClassificationTest {

    public SecurityClassificationTest() {}

    @Test
    public void checkSecret() {
        // Classified for test purposes only. All data is unclassified, releasable.
        SecurityClassification classification = SecurityClassification.Secret;
        assertEquals(classification.getDisplayName(), "Secret");
        assertEquals(classification.getUHLEncoding(), "S  ");
        assertEquals(classification.getDSIEncoding(), "S");
    }

    @Test
    public void checkUnknownFromUHL() {
        SecurityClassification classification =
                SecurityClassification.getEnumValueFromUHLEncoding("xxx");
        assertEquals(classification, SecurityClassification.Unknown);
        assertEquals(classification.getDisplayName(), "Unknown");
    }

    @Test
    public void checkUnknownFromDSI() {
        SecurityClassification classification =
                SecurityClassification.getEnumValueFromDSIEncoding("x");
        assertEquals(classification, SecurityClassification.Unknown);
        assertEquals(classification.getDisplayName(), "Unknown");
    }
}
