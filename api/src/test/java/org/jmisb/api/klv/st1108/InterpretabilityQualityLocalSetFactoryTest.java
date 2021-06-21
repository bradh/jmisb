package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.testng.annotations.Test;

/** Tests for the ST 1108 Interpretability and Quality Local Set Factory. */
public class InterpretabilityQualityLocalSetFactoryTest extends LoggerChecks {
    InterpretabilityQualityLocalSetFactoryTest() {
        super(InterpretabilityQualityLocalSet.class);
    }

    @Test
    public void parseTagSimple() throws KlvParseException {
        final byte[] bytes =
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
                    0x05,
                    0x01,
                    0x01,
                    0x04,
                    0x0B,
                    0x02,
                    (byte) 0x7E,
                    (byte) 0x8A
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        InterpretabilityQualityLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);

        assertEquals(
                localSet.frameMessage(false),
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
                    0x05,
                    0x01,
                    0x01,
                    0x04,
                    0x0B,
                    0x02,
                    (byte) 0x7E,
                    (byte) 0x8A
                });
    }
}
