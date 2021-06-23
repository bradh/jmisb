package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST 1108 Interpretability and Quality Local Set. */
public class InterpretabilityQualityLocalSetTest extends LoggerChecks {
    InterpretabilityQualityLocalSetTest() {
        super(InterpretabilityQualityLocalSet.class);
    }

    @Test
    public void fromUnknownTag() throws KlvParseException {
        verifyNoLoggerMessages();
        new InterpretabilityQualityLocalSet(
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x07,
                    0x7F,
                    0x01,
                    0x00,
                    0x0B,
                    0x02,
                    (byte) 0xfb,
                    (byte) 0x24
                });
        verifySingleLoggerMessage("Unknown Interpretability and Quality Metadata tag: 127");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badChecksum() throws KlvParseException {
        verifyNoLoggerMessages();
        new InterpretabilityQualityLocalSet(
                new byte[] {
                    0x06,
                    0x0E,
                    0x2B,
                    0x34,
                    0x02,
                    0x03,
                    0x01,
                    0x01,
                    0x0E,
                    0x01,
                    0x03,
                    0x03,
                    0x1C,
                    0x00,
                    0x00,
                    0x00,
                    0x04,
                    0x0B,
                    0x02,
                    (byte) 0xfb,
                    (byte) 0x24
                });
        verifySingleLoggerMessage("Bad checksum");
    }
}
