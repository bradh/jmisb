package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1108.st1108_3.InterpretabilityQualityLocalSet;
import org.jmisb.api.klv.st1108.st1108_3.InterpretabilityQualityLocalSetTest;
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
                    0x07,
                    0x01,
                    0x01,
                    0x04,
                    0x0B,
                    0x02,
                    (byte) 0xf5,
                    (byte) 0xca
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        IMisbMessage message = factory.create(bytes);
        assertNotNull(message);
        assertTrue(message instanceof InterpretabilityQualityLocalSet);
        InterpretabilityQualityLocalSet localSet = (InterpretabilityQualityLocalSet) message;
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        InterpretabilityQualityLocalSetTest.checkAssessmentPoint(localSet);

        assertEquals(localSet.frameMessage(false), bytes);
    }
}
