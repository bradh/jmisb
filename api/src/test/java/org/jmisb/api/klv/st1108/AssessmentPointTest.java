package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for AssessmentPoint. */
public class AssessmentPointTest {

    public AssessmentPointTest() {}

    @Test
    public void sensor() {
        AssessmentPoint uut = AssessmentPoint.Sensor;
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "Sensor");
        assertEquals(uut.getBytes(), new byte[] {0x01});
    }

    @Test
    public void sensorEncoder() {
        AssessmentPoint uut = AssessmentPoint.SensorEncoder;
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "Sensor Encoder");
        assertEquals(uut.getBytes(), new byte[] {0x02});
    }

    @Test
    public void gcsReceived() {
        AssessmentPoint uut = AssessmentPoint.GCSReceived;
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "GCS Received");
        assertEquals(uut.getBytes(), new byte[] {0x03});
    }

    @Test
    public void gcsTransmit() {
        AssessmentPoint uut = AssessmentPoint.GCSTransmit;
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "GCS Transmit");
        assertEquals(uut.getBytes(), new byte[] {0x04});
    }

    @Test
    public void libraryArchive() throws KlvParseException {
        AssessmentPoint uut = AssessmentPoint.fromBytes(new byte[] {0x05});
        assertEquals(uut, AssessmentPoint.LibraryArchive);
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "Library/Archive");
        assertEquals(uut.getBytes(), new byte[] {0x05});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badLength() throws KlvParseException {
        AssessmentPoint.fromBytes(new byte[] {0x00, 0x01});
    }

    @Test
    public void undefined() throws KlvParseException {
        AssessmentPoint uut = AssessmentPoint.fromBytes(new byte[] {0x00});
        assertEquals(uut, AssessmentPoint.Undefined);
        assertEquals(uut.getDisplayName(), "Assessment Point");
        assertEquals(uut.getDisplayableValue(), "Unknown(0)");
    }
}
