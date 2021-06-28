package org.jmisb.api.klv.st1108.st1108_3;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.jmisb.api.klv.st1108.st1108_3.metric.IMetricLocalSetValue;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricImplementer;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricLocalSet;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricLocalSetKey;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricLocalSets;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricName;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricParameters;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricTime;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricValue;
import org.jmisb.api.klv.st1108.st1108_3.metric.MetricVersion;
import org.testng.annotations.Test;

/** Tests for the ST 1108 Interpretability and Quality Local Set. */
public class InterpretabilityQualityLocalSetTest extends LoggerChecks {
    InterpretabilityQualityLocalSetTest() {
        super(InterpretabilityQualityLocalSet.class);
    }

    private static List<LdsField> getFields(final byte[] bytes)
            throws IllegalArgumentException, KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        return fields;
    }

    @Test
    public void fromUnknownTag() throws KlvParseException {
        byte[] bytes =
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
                };
        List<LdsField> fields = getFields(bytes);
        verifyNoLoggerMessages();
        InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, bytes);
        verifySingleLoggerMessage("Unknown Interpretability and Quality Metadata tag: 127");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void badChecksum() throws KlvParseException {
        byte[] bytes =
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
                };
        List<LdsField> fields = getFields(bytes);
        verifyNoLoggerMessages();
        InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, bytes);
        verifySingleLoggerMessage("Bad checksum");
    }

    public static void checkAssessmentPoint(InterpretabilityQualityLocalSet localSet) {
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

    private final byte[] manyTagsBytes =
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
                0x6E,
                0x01,
                0x01,
                (byte) 0x04,
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
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x84,
                (byte) 0x04,
                (byte) 0x86,
                (byte) 0x07,
                0x04,
                0x37,
                0x01,
                0x03,
                (byte) 0x52,
                (byte) 0x45,
                (byte) 0x52,
                0x02,
                0x04,
                (byte) 0x31,
                (byte) 0x2E,
                (byte) 0x33,
                (byte) 0x38,
                0x03,
                0x0F,
                (byte) 0x4D,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x4F,
                (byte) 0x72,
                (byte) 0x67,
                (byte) 0x1e,
                (byte) 0x4D,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x67,
                (byte) 0x72,
                (byte) 0x6F,
                (byte) 0x75,
                (byte) 0x70,
                0x04,
                0x09,
                (byte) 0xC3,
                (byte) 0xBC,
                (byte) 0x2C,
                (byte) 0x41,
                (byte) 0x42,
                (byte) 0x3A,
                (byte) 0x33,
                (byte) 0x32,
                (byte) 0x34,
                0x05,
                0x08,
                (byte) 0x00,
                (byte) 0x05,
                (byte) 0xc5,
                (byte) 0x8f,
                (byte) 0x08,
                (byte) 0x32,
                (byte) 0x58,
                (byte) 0x00,
                0x06,
                0x04,
                (byte) 0x3e,
                (byte) 0xae,
                (byte) 0x14,
                (byte) 0x7b,
                0x05,
                0x01,
                (byte) 0x02,
                0x06,
                0x01,
                (byte) 0x07,
                0x07,
                0x03,
                (byte) 0x34,
                (byte) 0x2E,
                (byte) 0x31,
                0x08,
                0x04,
                (byte) 0x41,
                (byte) 0xc9,
                (byte) 0x99,
                (byte) 0x9a,
                0x09,
                0x02,
                (byte) 0x04,
                (byte) 0x01,
                0x0A,
                0x01,
                (byte) 0x03,
                0x0B,
                0x02,
                (byte) 0xa4,
                (byte) 0x2d
            };

    private final byte[] twoMetricBytes =
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
                0x76,
                0x01,
                0x01,
                (byte) 0x04,
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
                (byte) 0x01,
                (byte) 0x02,
                (byte) 0x84,
                (byte) 0x04,
                (byte) 0x86,
                (byte) 0x07,
                0x04,
                0x37,
                0x01,
                0x03,
                (byte) 0x52,
                (byte) 0x45,
                (byte) 0x52,
                0x02,
                0x04,
                (byte) 0x31,
                (byte) 0x2E,
                (byte) 0x33,
                (byte) 0x38,
                0x03,
                0x0F,
                (byte) 0x4D,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x4F,
                (byte) 0x72,
                (byte) 0x67,
                (byte) 0x1e,
                (byte) 0x4D,
                (byte) 0x79,
                (byte) 0x20,
                (byte) 0x67,
                (byte) 0x72,
                (byte) 0x6F,
                (byte) 0x75,
                (byte) 0x70,
                0x04,
                0x09,
                (byte) 0xC3,
                (byte) 0xBC,
                (byte) 0x2C,
                (byte) 0x41,
                (byte) 0x42,
                (byte) 0x3A,
                (byte) 0x33,
                (byte) 0x32,
                (byte) 0x34,
                0x05,
                0x08,
                (byte) 0x00,
                (byte) 0x05,
                (byte) 0xc5,
                (byte) 0x8f,
                (byte) 0x08,
                (byte) 0x32,
                (byte) 0x58,
                (byte) 0x00,
                0x06,
                0x04,
                (byte) 0x3e,
                (byte) 0xae,
                (byte) 0x14,
                (byte) 0x7b,
                0x04,
                0x06,
                0x01,
                0x04,
                (byte) 0x50,
                (byte) 0x53,
                (byte) 0x4e,
                (byte) 0x052,
                0x05,
                0x01,
                (byte) 0x02,
                0x06,
                0x01,
                (byte) 0x07,
                0x07,
                0x03,
                (byte) 0x34,
                (byte) 0x2E,
                (byte) 0x31,
                0x08,
                0x04,
                (byte) 0x41,
                (byte) 0xc9,
                (byte) 0x99,
                (byte) 0x9a,
                0x09,
                0x02,
                (byte) 0x04,
                (byte) 0x01,
                0x0A,
                0x01,
                (byte) 0x03,
                0x0B,
                0x02,
                (byte) 0x07,
                (byte) 0xe2
            };

    @Test
    public void parseTagsMany() throws KlvParseException {

        List<LdsField> fields = getFields(manyTagsBytes);
        InterpretabilityQualityLocalSet localSet =
                InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, manyTagsBytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 10);
        checkManyValues(localSet);
        assertEquals(localSet.frameMessage(false), manyTagsBytes);
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

    private void checkSingleMetricLocalSet(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.MetricLocalSets));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.MetricLocalSets)
                        instanceof MetricLocalSets);
        MetricLocalSets value =
                (MetricLocalSets)
                        localSet.getField(InterpretabilityQualityMetadataKey.MetricLocalSets);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Metrics");
        assertEquals(value.getDisplayableValue(), "[Metrics]");
        Set<? extends IKlvKey> identifiers = value.getIdentifiers();
        assertEquals(identifiers.size(), 1);
        IKlvKey key = identifiers.iterator().next();
        assertEquals(key.getIdentifier(), 0);
        IKlvValue firstField = value.getField(key);
        assertTrue(firstField instanceof MetricLocalSet);
        MetricLocalSet metricLocalSet = (MetricLocalSet) firstField;
        assertEquals(metricLocalSet.getIdentifiers().size(), 6);
        assertTrue(metricLocalSet.getIdentifiers().contains(MetricLocalSetKey.MetricName));
        assertEquals(
                metricLocalSet.getField(MetricLocalSetKey.MetricName).getDisplayableValue(), "RER");
    }

    private void checkTwoMetricLocalSet(InterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(InterpretabilityQualityMetadataKey.MetricLocalSets));
        assertTrue(
                localSet.getField(InterpretabilityQualityMetadataKey.MetricLocalSets)
                        instanceof MetricLocalSets);
        MetricLocalSets value =
                (MetricLocalSets)
                        localSet.getField(InterpretabilityQualityMetadataKey.MetricLocalSets);
        assertNotNull(value);
        assertEquals(value.getDisplayName(), "Metrics");
        assertEquals(value.getDisplayableValue(), "[Metrics]");
        Set<? extends IKlvKey> identifiers = value.getIdentifiers();
        assertEquals(identifiers.size(), 2);
        for (IKlvKey key : identifiers) {
            IKlvValue field = value.getField(key);
            assertTrue(field instanceof MetricLocalSet);
            MetricLocalSet metricLocalSet = (MetricLocalSet) field;
            assertTrue(metricLocalSet.getIdentifiers().contains(MetricLocalSetKey.MetricName));
            if (key.getIdentifier() == 0) {
                assertEquals(
                        metricLocalSet.getField(MetricLocalSetKey.MetricName).getDisplayableValue(),
                        "RER");
            } else if (key.getIdentifier() == 1) {
                assertEquals(
                        metricLocalSet.getField(MetricLocalSetKey.MetricName).getDisplayableValue(),
                        "PSNR");
            }
        }
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

    @Test
    public void fromValuesMany() throws KlvParseException {
        Map<InterpretabilityQualityMetadataKey, IInterpretabilityQualityMetadataValue> values =
                new TreeMap<>();
        values.put(InterpretabilityQualityMetadataKey.AssessmentPoint, AssessmentPoint.GCSTransmit);
        values.put(
                InterpretabilityQualityMetadataKey.MetricPeriodPack,
                new MetricPeriodPack(new ST0603TimeStamp(987654321000000L), 909322));
        values.put(
                InterpretabilityQualityMetadataKey.WindowCornersPack,
                new WindowCornersPack(1, 2, 516, 775));
        MetricLocalSet metric = buildMetric();
        MetricLocalSets metricLocalSets = new MetricLocalSets(metric);
        values.put(InterpretabilityQualityMetadataKey.MetricLocalSets, metricLocalSets);
        values.put(InterpretabilityQualityMetadataKey.CompressionType, CompressionType.H264);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionProfile,
                CompressionProfile.High_4_2_2);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionLevel, new CompressionLevel("4.1"));
        values.put(
                InterpretabilityQualityMetadataKey.CompressionRatio, new CompressionRatio(25.20));
        values.put(InterpretabilityQualityMetadataKey.StreamBitrate, new StreamBitrate(1025));
        values.put(InterpretabilityQualityMetadataKey.DocumentVersion, new DocumentVersion(3));
        InterpretabilityQualityLocalSet localSet = new InterpretabilityQualityLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 10);
        checkManyValues(localSet);
        assertEquals(localSet.frameMessage(false), manyTagsBytes);
    }

    @Test
    public void fromValuesManyWithCRC16Ignored() throws KlvParseException {
        Map<InterpretabilityQualityMetadataKey, IInterpretabilityQualityMetadataValue> values =
                new TreeMap<>();
        values.put(InterpretabilityQualityMetadataKey.AssessmentPoint, AssessmentPoint.GCSTransmit);
        values.put(
                InterpretabilityQualityMetadataKey.MetricPeriodPack,
                new MetricPeriodPack(new ST0603TimeStamp(987654321000000L), 909322));
        values.put(
                InterpretabilityQualityMetadataKey.WindowCornersPack,
                new WindowCornersPack(1, 2, 516, 775));
        MetricLocalSet metric = buildMetric();
        MetricLocalSets metricLocalSets = new MetricLocalSets(metric);
        values.put(InterpretabilityQualityMetadataKey.MetricLocalSets, metricLocalSets);
        values.put(InterpretabilityQualityMetadataKey.CompressionType, CompressionType.H264);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionProfile,
                CompressionProfile.High_4_2_2);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionLevel, new CompressionLevel("4.1"));
        values.put(
                InterpretabilityQualityMetadataKey.CompressionRatio, new CompressionRatio(25.20));
        values.put(InterpretabilityQualityMetadataKey.StreamBitrate, new StreamBitrate(1025));
        values.put(InterpretabilityQualityMetadataKey.DocumentVersion, new DocumentVersion(3));
        values.put(
                InterpretabilityQualityMetadataKey.CRC16CCITT,
                new IInterpretabilityQualityMetadataValue() {
                    @Override
                    public void appendBytesToBuilder(ArrayBuilder arrayBuilder) {
                        arrayBuilder.append(new byte[] {0x7F, 0x7F, 0x7F, 0x7F});
                    }

                    @Override
                    public String getDisplayName() {
                        return "Dummy Name";
                    }

                    @Override
                    public String getDisplayableValue() {
                        return "Dummy Value";
                    }
                });
        InterpretabilityQualityLocalSet localSet = new InterpretabilityQualityLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 11);
        checkManyValues(localSet);
        assertEquals(localSet.frameMessage(false), manyTagsBytes);
    }

    private void checkManyValues(InterpretabilityQualityLocalSet localSet) {
        checkAssessmentPoint(localSet);
        checkMetricPeriodPack(localSet);
        checkWindowCornersPack(localSet);
        checkSingleMetricLocalSet(localSet);
        checkCompressionType(localSet);
        checkCompressionProfile(localSet);
        checkCompressionLevel(localSet);
        checkCompressionRatio(localSet);
        checkStreamBitrate(localSet);
        checkDocumentVersion(localSet);
    }

    private MetricLocalSet buildMetric() {
        Map<MetricLocalSetKey, IMetricLocalSetValue> map = new HashMap<>();
        map.put(MetricLocalSetKey.MetricName, new MetricName("RER"));
        map.put(MetricLocalSetKey.MetricVersion, new MetricVersion("1.38"));
        map.put(MetricLocalSetKey.MetricImplementer, new MetricImplementer("My Org", "My group"));
        map.put(MetricLocalSetKey.MetricParameters, new MetricParameters("ü,AB:324"));
        map.put(
                MetricLocalSetKey.MetricTime,
                new MetricTime(new ST0603TimeStamp(1624592992065536L)));
        map.put(MetricLocalSetKey.MetricValue, new MetricValue(0.34));
        return MetricLocalSet.fromMap(map);
    }

    private MetricLocalSet buildSecondMetric() {
        Map<MetricLocalSetKey, IMetricLocalSetValue> map = new HashMap<>();
        map.put(MetricLocalSetKey.MetricName, new MetricName("PSNR"));
        return MetricLocalSet.fromMap(map);
    }

    @Test
    public void fromValuesTwoMetrics() throws KlvParseException {
        Map<InterpretabilityQualityMetadataKey, IInterpretabilityQualityMetadataValue> values =
                new TreeMap<>();
        values.put(InterpretabilityQualityMetadataKey.AssessmentPoint, AssessmentPoint.GCSTransmit);
        values.put(
                InterpretabilityQualityMetadataKey.MetricPeriodPack,
                new MetricPeriodPack(new ST0603TimeStamp(987654321000000L), 909322));
        values.put(
                InterpretabilityQualityMetadataKey.WindowCornersPack,
                new WindowCornersPack(1, 2, 516, 775));
        List<MetricLocalSet> metrics = new ArrayList<>();
        metrics.add(buildMetric());
        metrics.add(buildSecondMetric());
        MetricLocalSets metricLocalSets = new MetricLocalSets(metrics);
        values.put(InterpretabilityQualityMetadataKey.MetricLocalSets, metricLocalSets);
        values.put(InterpretabilityQualityMetadataKey.CompressionType, CompressionType.H264);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionProfile,
                CompressionProfile.High_4_2_2);
        values.put(
                InterpretabilityQualityMetadataKey.CompressionLevel, new CompressionLevel("4.1"));
        values.put(
                InterpretabilityQualityMetadataKey.CompressionRatio, new CompressionRatio(25.20));
        values.put(InterpretabilityQualityMetadataKey.StreamBitrate, new StreamBitrate(1025));
        values.put(InterpretabilityQualityMetadataKey.DocumentVersion, new DocumentVersion(3));
        InterpretabilityQualityLocalSet localSet = new InterpretabilityQualityLocalSet(values);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 10);
        checkAssessmentPoint(localSet);
        checkMetricPeriodPack(localSet);
        checkWindowCornersPack(localSet);
        checkTwoMetricLocalSet(localSet);
        checkCompressionType(localSet);
        checkCompressionProfile(localSet);
        checkCompressionLevel(localSet);
        checkCompressionRatio(localSet);
        checkStreamBitrate(localSet);
        checkDocumentVersion(localSet);
        assertEquals(localSet.frameMessage(false), twoMetricBytes);
    }

    @Test
    public void parseWithTwoMetrics() throws KlvParseException {
        List<LdsField> fields = getFields(twoMetricBytes);
        InterpretabilityQualityLocalSet localSet =
                InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, twoMetricBytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 10);
        checkAssessmentPoint(localSet);
        checkMetricPeriodPack(localSet);
        checkWindowCornersPack(localSet);
        checkTwoMetricLocalSet(localSet);
        checkCompressionType(localSet);
        checkCompressionProfile(localSet);
        checkCompressionLevel(localSet);
        checkCompressionRatio(localSet);
        checkStreamBitrate(localSet);
        checkDocumentVersion(localSet);
        assertEquals(localSet.frameMessage(false), twoMetricBytes);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badNesting() throws KlvParseException {
        List<LdsField> fields = getFields(twoMetricBytes);
        InterpretabilityQualityLocalSet localSet =
                InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, twoMetricBytes);
        assertEquals(localSet.getIdentifiers().size(), 10);
        localSet.frameMessage(true);
    }
}
