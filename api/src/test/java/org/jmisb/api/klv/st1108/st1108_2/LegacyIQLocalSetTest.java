package org.jmisb.api.klv.st1108.st1108_2;

import static org.testng.Assert.*;

import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.KlvConstants;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.UniversalLabel;
import org.testng.annotations.Test;

/** Tests for the (legacy) ST 1108.2 Interpretability and Quality Local Set. */
public class LegacyIQLocalSetTest extends LoggerChecks {
    LegacyIQLocalSetTest() {
        super(LegacyIQLocalSet.class);
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
                    0x06, 0x0E, 0x2B, 0x34, 0x02, 0x03, 0x01, 0x01, 0x0E, 0x01, 0x03, 0x03, 0x1C,
                    0x00, 0x00, 0x00, 0x03, 0x7F, 0x01, 0x00,
                };
        List<LdsField> fields = getFields(bytes);
        verifyNoLoggerMessages();
        LegacyIQLocalSet.fromST1108_2Fields(fields, bytes);
        verifySingleLoggerMessage("Unknown Legacy Interpretability and Quality Metadata tag: 127");
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
                0x3C,
                0x01,
                0x08,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x09,
                0x02,
                0x01,
                (byte) 0x06,
                0x03,
                0x01,
                (byte) 0x60,
                0x04,
                0x01,
                (byte) 0x00,
                0x05,
                0x01,
                (byte) 0x02,
                0x06,
                0x01,
                (byte) 0x04,
                0x07,
                0x02,
                (byte) 0x02,
                (byte) 0x80,
                0x08,
                0x08,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x20,
                0x09,
                0x08,
                (byte) 0x00,
                (byte) 0x57,
                (byte) 0x01,
                (byte) 0x5a,
                (byte) 0x00,
                (byte) 0x20,
                (byte) 0x00,
                (byte) 0x08,
                0x0c,
                0x02,
                (byte) 0x00,
                (byte) 0x7e,
                0x0d,
                0x02,
                (byte) 0x00,
                (byte) 0x01,
                0x0e,
                0x01,
                (byte) 0x30
            };

    @Test
    public void parseTagsMany() throws KlvParseException {

        List<LdsField> fields = getFields(manyTagsBytes);
        LegacyIQLocalSet localSet = LegacyIQLocalSet.fromST1108_2Fields(fields, manyTagsBytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Legacy Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 12);
        checkManyValues(localSet);
        assertEquals(localSet.frameMessage(false), manyTagsBytes);
    }

    private void checkManyValues(LegacyIQLocalSet localSet) {
        checkMostRecentFrameTime(localSet);
        checkInterpretability(localSet);
        checkVideoQuality(localSet);
        checkMethod(localSet);
        checkPSNRCoefficientId(localSet);
        checkQualityCoefficientId(localSet);
        checkRatingDuration(localSet);
        checkMIQPakInsertionTime(localSet);
        checkChipLocationSizeBitDepth(localSet);

        checkChipEdgeIntensity(localSet);
        checkChipFrequencyRatio(localSet);
        checkChipPSNR(localSet);
    }

    private void checkMostRecentFrameTime(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.MostRecentFrameTime));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.MostRecentFrameTime);
        assertTrue(value instanceof MostRecentFrameTime);
        MostRecentFrameTime uut = (MostRecentFrameTime) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Most Recent Frame Time");
        assertEquals(uut.getDisplayableValue(), "9");
        assertEquals(uut.getTime().getMicroseconds(), 9);
    }

    private void checkInterpretability(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.VideoInterpretability));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.VideoInterpretability);
        assertTrue(value instanceof VideoInterpretability);
        VideoInterpretability uut = (VideoInterpretability) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "6");
        assertEquals(uut.getInterpretability(), 6);
    }

    private void checkVideoQuality(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.VideoQuality));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.VideoQuality);
        assertTrue(value instanceof VideoQuality);
        VideoQuality uut = (VideoQuality) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "96");
        assertEquals(uut.getQuality(), 96);
    }

    private void checkMethod(LegacyIQLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(LegacyIQMetadataKey.InterpretabilityQualityMethod));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.InterpretabilityQualityMethod);
        assertTrue(value instanceof QualityMethod);
        QualityMethod uut = (QualityMethod) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Interpretability Quality Method");
        assertEquals(uut.getDisplayableValue(), "0 (Manual)");
        assertEquals(uut.getMethodId(), 0);
    }

    private void checkPSNRCoefficientId(LegacyIQLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(LegacyIQMetadataKey.PSNRCoefficientIdentifier));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.PSNRCoefficientIdentifier);
        assertTrue(value instanceof PSNRCoefficientIdentifier);
        PSNRCoefficientIdentifier uut = (PSNRCoefficientIdentifier) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getIdentifier(), 2);
    }

    private void checkQualityCoefficientId(LegacyIQLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(LegacyIQMetadataKey.QualityCoefficientIdentifier));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.QualityCoefficientIdentifier);
        assertTrue(value instanceof QualityCoefficientIdentifier);
        QualityCoefficientIdentifier uut = (QualityCoefficientIdentifier) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "4");
        assertEquals(uut.getIdentifier(), 4);
    }

    private void checkRatingDuration(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.RatingDuration));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.RatingDuration);
        assertTrue(value instanceof RatingDuration);
        RatingDuration uut = (RatingDuration) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "640 frames");
        assertEquals(uut.getDuration(), 640);
    }

    private void checkMIQPakInsertionTime(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.MIQPakInsertionTime));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.MIQPakInsertionTime);
        assertTrue(value instanceof MIQPakInsertionTime);
        MIQPakInsertionTime uut = (MIQPakInsertionTime) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Insertion Time");
        assertEquals(uut.getDisplayableValue(), "32");
        assertEquals(uut.getTime().getMicroseconds(), 32);
    }

    private void checkChipLocationSizeBitDepth(LegacyIQLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(LegacyIQMetadataKey.ChipLocationSizeBitDepth));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.ChipLocationSizeBitDepth);
        assertTrue(value instanceof ChipLocationSizeBitDepth);
        ChipLocationSizeBitDepth uut = (ChipLocationSizeBitDepth) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip Location, Size & Bit Depth");
        assertEquals(uut.getDisplayableValue(), "[87, 346], 32x32, 8");
        assertEquals(uut.getColumnIndex(), 87);
        assertEquals(uut.getRowIndex(), 346);
        assertEquals(uut.getChipLength(), 32);
        assertEquals(uut.getBitDepth(), 8);
    }

    private void checkChipEdgeIntensity(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.ChipEdgeIntensity));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.ChipEdgeIntensity);
        assertTrue(value instanceof ChipEdgeIntensity);
        ChipEdgeIntensity uut = (ChipEdgeIntensity) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "126");
        assertEquals(uut.getIntensity(), 126);
    }

    private void checkChipFrequencyRatio(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.ChipFrequencyRatio));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.ChipFrequencyRatio);
        assertTrue(value instanceof ChipFrequencyRatio);
        ChipFrequencyRatio uut = (ChipFrequencyRatio) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip Frequency Ratio");
        assertEquals(uut.getDisplayableValue(), "1");
        assertEquals(uut.getRatio(), 1);
    }

    private void checkChipPSNR(LegacyIQLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyIQMetadataKey.ChipPSNR));
        IKlvValue value = localSet.getField(LegacyIQMetadataKey.ChipPSNR);
        assertTrue(value instanceof ChipPSNR);
        ChipPSNR uut = (ChipPSNR) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "48 dB");
        assertEquals(uut.getPSNR(), 48);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badNesting() throws KlvParseException {
        List<LdsField> fields = getFields(manyTagsBytes);
        LegacyIQLocalSet localSet = LegacyIQLocalSet.fromST1108_2Fields(fields, manyTagsBytes);
        localSet.frameMessage(true);
    }
}
