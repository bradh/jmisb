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
                    0x35,
                    0x01,
                    0x01,
                    0x04,
                    0x02,
                    0x0C,
                    (byte) 0x00,
                    (byte) 0x03,
                    (byte) 0x82,
                    (byte) 0x44,
                    (byte) 0x30,
                    (byte) 0xF6,
                    (byte) 0xCE,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x0D,
                    (byte) 0xE0,
                    (byte) 0x0A,
                    0x03,
                    0x06,
                    0x01,
                    0x02,
                    (byte) 0x84,
                    0x04,
                    (byte) 0x86,
                    0x07,
                    0x05,
                    0x01,
                    0x02,
                    0x06,
                    0x01,
                    0x07,
                    0x07,
                    0x03,
                    0x34,
                    0x2E,
                    0x31,
                    0x08,
                    0x04,
                    (byte) 0x41,
                    (byte) 0xc9,
                    (byte) 0x99,
                    (byte) 0x9a,
                    0x09,
                    0x02,
                    0x04,
                    0x01,
                    0x0A,
                    0x01,
                    0x03,
                    0x0B,
                    0x02,
                    (byte) 0xdc,
                    (byte) 0xbc
                };
        InterpretabilityQualityLocalSetFactory factory =
                new InterpretabilityQualityLocalSetFactory();
        InterpretabilityQualityLocalSet localSet = factory.create(bytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 9);
        checkAssessmentPoint(localSet);
        checkMetricPeriodPack(localSet);
        checkWindowCornersPack(localSet);

        checkCompressionType(localSet);
        checkCompressionProfile(localSet);
        checkCompressionLevel(localSet);
        checkCompressionRatio(localSet);
        checkStreamBitrate(localSet);
        checkDocumentVersion(localSet);
        assertEquals(localSet.frameMessage(false), bytes);
    }

    private void checkMetricPeriodPack(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.MetricPeriodPack));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.MetricPeriodPack)
                        instanceof MetricPeriodPack);
        MetricPeriodPack value =
                (MetricPeriodPack)
                        localSet.getField(InterpretabilityQualityMetadataKey.MetricPeriodPack);
        assertNotNull(value);
        assertEquals(value.getStartTime().getMicroseconds(), 987654321000000L);
        assertEquals(value.getTimeOffset(), 909322);
    }

    private void checkWindowCornersPack(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.WindowCornersPack));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.WindowCornersPack)
                        instanceof WindowCornersPack);
        WindowCornersPack value =
                (WindowCornersPack)
                        localSet.getField(InterpretabilityQualityMetadataKey.WindowCornersPack);
        assertNotNull(value);
        assertEquals(value.getStartingRow(), 1);

        assertEquals(value.getStartingColumn(), 2);

        assertEquals(value.getEndingRow(), 516);

        assertEquals(value.getEndingColumn(), 775);
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

    private void checkCompressionLevel(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.CompressionLevel));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.CompressionLevel)
                        instanceof CompressionLevel);
        CompressionLevel value =
                (CompressionLevel)
                        localSet.getField(InterpretabilityQualityMetadataKey.CompressionLevel);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Compression Level");
        assertEquals(value.getDisplayableValue(), "4.1");
        assertEquals(value.getCompressionLevel(), "4.1");
    }

    private void checkCompressionRatio(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.CompressionRatio));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.CompressionRatio)
                        instanceof CompressionRatio);
        CompressionRatio value =
                (CompressionRatio)
                        localSet.getField(InterpretabilityQualityMetadataKey.CompressionRatio);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Compression Ratio");
        assertEquals(value.getDisplayableValue(), "25.20");
        assertEquals(value.getCompressionRatio(), 25.2, 0.001);
    }

    private void checkStreamBitrate(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.StreamBitrate));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.StreamBitrate)
                        instanceof StreamBitrate);
        StreamBitrate value =
                (StreamBitrate) localSet.getField(InterpretabilityQualityMetadataKey.StreamBitrate);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Stream Bitrate");
        assertEquals(value.getDisplayableValue(), "1.025 Mbits/sec");
        assertEquals(value.getBitrate(), 1025);
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
