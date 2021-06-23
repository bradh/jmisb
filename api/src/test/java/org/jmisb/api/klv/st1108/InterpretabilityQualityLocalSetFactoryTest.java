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
        InterpretabilityQualityLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 1);
        checkAssessmentPoint(localSet);

        assertEquals(localSet.frameMessage(false), bytes);
    }

    private void checkAssessmentPoint(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.AssessmentPoint));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.AssessmentPoint)
                        instanceof AssessmentPoint);
        AssessmentPoint value =
                (AssessmentPoint)
                        localSet.getField(InterpretabilityQualityMetadataKey.AssessmentPoint);
        assertNotNull(value);
        assertEquals(value, AssessmentPoint.GCSTransmit);
    }

    @Test
    public void parseTagsMany() throws KlvParseException {
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
                    0x10,
                    0x01,
                    0x01,
                    0x04,
                    0x05,
                    0x01,
                    0x02,
                    0x06,
                    0x01,
                    0x07,
                    0x0A,
                    0x01,
                    0x03,
                    0x0B,
                    0x02,
                    (byte) 0x8e,
                    (byte) 0x5e
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        InterpretabilityQualityLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 4);
        checkAssessmentPoint(localSet);

        checkCompressionType(localSet);
        checkCompressionProfile(localSet);

        checkDocumentVersion(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    private void checkCompressionType(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.CompressionType));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.CompressionType)
                        instanceof CompressionType);
        CompressionType value =
                (CompressionType)
                        localSet.getField(InterpretabilityQualityMetadataKey.CompressionType);
        assertNotNull(value);
        assertEquals(value, CompressionType.H264);
    }

    private void checkCompressionProfile(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.CompressionProfile));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.CompressionProfile)
                        instanceof CompressionProfile);
        CompressionProfile value =
                (CompressionProfile)
                        localSet.getField(InterpretabilityQualityMetadataKey.CompressionProfile);
        assertNotNull(value);
        assertEquals(value, CompressionProfile.High_4_2_2);
    }

    private void checkDocumentVersion(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.DocumentVersion));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.DocumentVersion)
                        instanceof DocumentVersion);
        DocumentVersion value =
                (DocumentVersion)
                        localSet.getField(InterpretabilityQualityMetadataKey.DocumentVersion);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Document Version");
        assertEquals(value.getDisplayableValue(), "ST 1108.3");
        assertEquals(value.getVersion(), 3);
    }
}