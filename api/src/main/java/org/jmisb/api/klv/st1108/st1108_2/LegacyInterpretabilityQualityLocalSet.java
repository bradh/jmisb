package org.jmisb.api.klv.st1108.st1108_2;

import static org.jmisb.api.klv.KlvConstants.InterpretabilityQualityLocalSetUl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interpretability and Quality Local Set.
 *
 * <p>The Interpretability and Quality Local Set is the parent local set for ST 1108. Note that the
 * Metric Local Set value within this Interpretability and Quality Local Set can repeat.
 */
public class LegacyInterpretabilityQualityLocalSet implements IMisbMessage {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(LegacyInterpretabilityQualityLocalSet.class);

    /** Map containing all elements in the message. */
    private final SortedMap<LegacyMetadataKey, IInterpretabilityQualityMetadataValue> map =
            new TreeMap<>();

    private LegacyInterpretabilityQualityLocalSet() {};

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public LegacyInterpretabilityQualityLocalSet(
            Map<LegacyMetadataKey, IInterpretabilityQualityMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build an Interpretability and Quality Metadata Local Set from extracted fields.
     *
     * @param fields the fields
     * @param bytes the bytes used
     * @return local set corresponding to the provided fields
     * @throws KlvParseException if parsing fails
     */
    public static LegacyInterpretabilityQualityLocalSet fromST1108_2Fields(
            List<LdsField> fields, final byte[] bytes) throws KlvParseException {
        LegacyInterpretabilityQualityLocalSet localSet =
                new LegacyInterpretabilityQualityLocalSet();
        for (LdsField field : fields) {
            localSet.processField(field, bytes);
        }
        return localSet;
    }

    private void processField(LdsField field, final byte[] bytes)
            throws KlvParseException, AssertionError {
        LegacyMetadataKey key = LegacyMetadataKey.getKey(field.getTag());
        switch (key) {
            case MostRecentFrameTime:
                break;
            case VideoInterpretability:
                map.put(
                        LegacyMetadataKey.VideoInterpretability,
                        new VideoInterpretability(field.getData()));
                break;
            case VideoQuality:
                map.put(LegacyMetadataKey.VideoQuality, new VideoQuality(field.getData()));
                break;
            case InterpretabilityQualityMethod:
                map.put(
                        LegacyMetadataKey.InterpretabilityQualityMethod,
                        new QualityMethod(field.getData()));
                break;
            case PSNRCoefficientIdentifier:
                map.put(
                        LegacyMetadataKey.PSNRCoefficientIdentifier,
                        new PSNRCoefficientIdentifier(field.getData()));
                break;
            case QualityCoefficientIdentifier:
                map.put(
                        LegacyMetadataKey.QualityCoefficientIdentifier,
                        new QualityCoefficientIdentifier(field.getData()));
                break;
            case RatingDuration:
                map.put(LegacyMetadataKey.RatingDuration, new RatingDuration(field.getData()));
                break;
            case MIQPakInsertionTime:
                break;
            case ChipLocationSizeBitDepth:
                break;
            case ChipYvaluesUncompressed:
                break;
            case ChipYvaluesPNG:
                break;
            case ChipEdgeIntensity:
                map.put(
                        LegacyMetadataKey.ChipEdgeIntensity,
                        new ChipEdgeIntensity(field.getData()));
                break;
            case ChipFrequencyRatio:
                break;
            case ChipPSNR:
                map.put(LegacyMetadataKey.ChipPSNR, new ChipPSNR(field.getData()));
                break;
            default:
                LOGGER.info(
                        "Unknown Legacy Interpretability and Quality Metadata tag: {}",
                        field.getTag());
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException(
                    "Interpretability and Quality Local Set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (LegacyMetadataKey tag : map.keySet()) {
            getField(tag).appendBytesToBuilder(arrayBuilder);
        }
        arrayBuilder.prependLength();
        arrayBuilder.prepend(getUniversalLabel());

        return arrayBuilder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IInterpretabilityQualityMetadataValue getField(IKlvKey key) {
        return map.get((LegacyMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return InterpretabilityQualityLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1108 Legacy Interpretability and Quality";
    }
}
