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
public class LegacyInterpretabilityQualityLocalSetTest extends LoggerChecks {
    LegacyInterpretabilityQualityLocalSetTest() {
        super(LegacyInterpretabilityQualityLocalSet.class);
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
        LegacyInterpretabilityQualityLocalSet.fromST1108_2Fields(fields, bytes);
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
                0x2e,
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
                0x0c,
                0x02,
                (byte) 0x00,
                (byte) 0x7e,
                0x0e,
                0x01,
                (byte) 0x30
            };

    @Test
    public void parseTagsMany() throws KlvParseException {

        List<LdsField> fields = getFields(manyTagsBytes);
        LegacyInterpretabilityQualityLocalSet localSet =
                LegacyInterpretabilityQualityLocalSet.fromST1108_2Fields(fields, manyTagsBytes);
        assertNotNull(localSet);
        assertEquals(localSet.displayHeader(), "ST 1108 Legacy Interpretability and Quality");
        assertEquals(localSet.getUniversalLabel(), KlvConstants.InterpretabilityQualityLocalSetUl);
        assertEquals(localSet.getIdentifiers().size(), 10);
        checkManyValues(localSet);
        assertEquals(localSet.frameMessage(false), manyTagsBytes);
    }

    private void checkManyValues(LegacyInterpretabilityQualityLocalSet localSet) {
        checkMostRecentFrameTime(localSet);
        checkInterpretability(localSet);
        checkVideoQuality(localSet);
        checkMethod(localSet);
        checkPSNRCoefficientId(localSet);
        checkQualityCoefficientId(localSet);
        checkRatingDuration(localSet);
        checkMIQPakInsertionTime(localSet);
        checkChipEdgeIntensity(localSet);
        checkChipPSNR(localSet);
    }

    private void checkMostRecentFrameTime(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.MostRecentFrameTime));
        IKlvValue value = localSet.getField(LegacyMetadataKey.MostRecentFrameTime);
        assertTrue(value instanceof MostRecentFrameTime);
        MostRecentFrameTime uut = (MostRecentFrameTime) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Most Recent Frame Time");
        assertEquals(uut.getDisplayableValue(), "9");
        assertEquals(uut.getTime().getMicroseconds(), 9);
    }

    private void checkInterpretability(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.VideoInterpretability));
        IKlvValue value = localSet.getField(LegacyMetadataKey.VideoInterpretability);
        assertTrue(value instanceof VideoInterpretability);
        VideoInterpretability uut = (VideoInterpretability) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Video Interpretability");
        assertEquals(uut.getDisplayableValue(), "6");
        assertEquals(uut.getInterpretability(), 6);
    }

    private void checkVideoQuality(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.VideoQuality));
        IKlvValue value = localSet.getField(LegacyMetadataKey.VideoQuality);
        assertTrue(value instanceof VideoQuality);
        VideoQuality uut = (VideoQuality) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Video Quality");
        assertEquals(uut.getDisplayableValue(), "96");
        assertEquals(uut.getQuality(), 96);
    }

    private void checkMethod(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers()
                        .contains(LegacyMetadataKey.InterpretabilityQualityMethod));
        IKlvValue value = localSet.getField(LegacyMetadataKey.InterpretabilityQualityMethod);
        assertTrue(value instanceof QualityMethod);
        QualityMethod uut = (QualityMethod) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Interpretability Quality Method");
        assertEquals(uut.getDisplayableValue(), "0 (Manual)");
        assertEquals(uut.getMethodId(), 0);
    }

    private void checkPSNRCoefficientId(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.PSNRCoefficientIdentifier));
        IKlvValue value = localSet.getField(LegacyMetadataKey.PSNRCoefficientIdentifier);
        assertTrue(value instanceof PSNRCoefficientIdentifier);
        PSNRCoefficientIdentifier uut = (PSNRCoefficientIdentifier) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "PSNR Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "2");
        assertEquals(uut.getIdentifier(), 2);
    }

    private void checkQualityCoefficientId(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(
                localSet.getIdentifiers().contains(LegacyMetadataKey.QualityCoefficientIdentifier));
        IKlvValue value = localSet.getField(LegacyMetadataKey.QualityCoefficientIdentifier);
        assertTrue(value instanceof QualityCoefficientIdentifier);
        QualityCoefficientIdentifier uut = (QualityCoefficientIdentifier) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Quality Coefficient Identifier");
        assertEquals(uut.getDisplayableValue(), "4");
        assertEquals(uut.getIdentifier(), 4);
    }

    private void checkRatingDuration(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.RatingDuration));
        IKlvValue value = localSet.getField(LegacyMetadataKey.RatingDuration);
        assertTrue(value instanceof RatingDuration);
        RatingDuration uut = (RatingDuration) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Rating Duration");
        assertEquals(uut.getDisplayableValue(), "640 frames");
        assertEquals(uut.getDuration(), 640);
    }

    private void checkMIQPakInsertionTime(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.MIQPakInsertionTime));
        IKlvValue value = localSet.getField(LegacyMetadataKey.MIQPakInsertionTime);
        assertTrue(value instanceof MIQPakInsertionTime);
        MIQPakInsertionTime uut = (MIQPakInsertionTime) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Insertion Time");
        assertEquals(uut.getDisplayableValue(), "32");
        assertEquals(uut.getTime().getMicroseconds(), 32);
    }

    private void checkChipEdgeIntensity(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.ChipEdgeIntensity));
        IKlvValue value = localSet.getField(LegacyMetadataKey.ChipEdgeIntensity);
        assertTrue(value instanceof ChipEdgeIntensity);
        ChipEdgeIntensity uut = (ChipEdgeIntensity) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip Edge Intensity");
        assertEquals(uut.getDisplayableValue(), "126");
        assertEquals(uut.getIntensity(), 126);
    }

    private void checkChipPSNR(LegacyInterpretabilityQualityLocalSet localSet) {
        assertTrue(localSet.getIdentifiers().contains(LegacyMetadataKey.ChipPSNR));
        IKlvValue value = localSet.getField(LegacyMetadataKey.ChipPSNR);
        assertTrue(value instanceof ChipPSNR);
        ChipPSNR uut = (ChipPSNR) value;
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "Chip PSNR");
        assertEquals(uut.getDisplayableValue(), "48 dB");
        assertEquals(uut.getPSNR(), 48);
    }
}
