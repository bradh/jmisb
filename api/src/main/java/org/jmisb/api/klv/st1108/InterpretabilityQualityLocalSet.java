package org.jmisb.api.klv.st1108;

import static org.jmisb.api.klv.KlvConstants.InterpretabilityQualityLocalSetUl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.InvalidDataHandler;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.CrcCcitt;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interpretability and Quality Local Set.
 *
 * <p>The Interpretability and Quality Local Set is the parent local set for ST 1108. Note that the
 * Metric Local Set value within this Interpretability and Quality Local Set can repeat.
 */
public class InterpretabilityQualityLocalSet implements IMisbMessage {

    private static final int CRC16_LENGTH = 2;
    private static final int ST1108_3_VERSION = 3;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(InterpretabilityQualityLocalSet.class);

    /**
     * Create a {@link IInterpretabilityQualityMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type (not CRC-16-CCITT)
     * @param fieldBytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    static IInterpretabilityQualityMetadataValue createValue(
            InterpretabilityQualityMetadataKey tag, byte[] fieldBytes) throws KlvParseException {
        switch (tag) {
            case AssessmentPoint:
                return AssessmentPoint.fromBytes(fieldBytes);
            case MetricPeriodPack:
                return new MetricPeriodPack(fieldBytes);
            case WindowCornersPack:
                return new WindowCornersPack(fieldBytes);
            case MetricLocalSets:
                // TODO
                throw new UnsupportedOperationException("MetricLocalSets");
            case CompressionType:
                return CompressionType.fromBytes(fieldBytes);
            case CompressionRatio:
                return new CompressionRatio(fieldBytes);
            case CompressionProfile:
                return CompressionProfile.fromBytes(fieldBytes);
            case CompressionLevel:
                return new CompressionLevel(fieldBytes);
            case StreamBitrate:
                return new StreamBitrate(fieldBytes);
            case DocumentVersion:
                return new DocumentVersion(fieldBytes);
            default:
                return null;
        }
    }

    /** Map containing all elements in the message. */
    private final SortedMap<
                    InterpretabilityQualityMetadataKey, IInterpretabilityQualityMetadataValue>
            map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public InterpretabilityQualityLocalSet(
            Map<InterpretabilityQualityMetadataKey, IInterpretabilityQualityMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build an Interpretability and Quality Metadata Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public InterpretabilityQualityLocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        int version = getVersion(fields);
        switch (version) {
            case ST1108_3_VERSION:
                parseAsST1108_3(fields, bytes);
                break;
            default:
                LOGGER.warn("Unsupported/unknown ST 1108 version");
        }
    }

    private int getVersion(List<LdsField> fields) {
        // TODO: work out heuristics for this, open question into MISB.
        return ST1108_3_VERSION;
    }

    private void parseAsST1108_3(List<LdsField> fields, final byte[] bytes)
            throws KlvParseException {
        for (LdsField field : fields) {
            InterpretabilityQualityMetadataKey key =
                    InterpretabilityQualityMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info(
                            "Unknown Interpretability and Quality Metadata tag: {}",
                            field.getTag());
                    break;
                case CRC16CCITT:
                    if (!CrcCcitt.verify(bytes, field.getData())) {
                        InvalidDataHandler handler = InvalidDataHandler.getInstance();
                        handler.handleInvalidChecksum(LOGGER, "Bad checksum");
                    }
                    break;
                default:
                    IInterpretabilityQualityMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        if (isNested) {
            throw new IllegalArgumentException(
                    "Interpretability and Quality Local Set cannot be nested");
        }
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (InterpretabilityQualityMetadataKey tag : map.keySet()) {
            if (tag.equals(InterpretabilityQualityMetadataKey.CRC16CCITT)) {
                // This will get added, at the end
                continue;
            }
            arrayBuilder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            arrayBuilder.appendAsBerLength(valueBytes.length);
            arrayBuilder.append(valueBytes);
        }
        arrayBuilder.appendAsOID(InterpretabilityQualityMetadataKey.CRC16CCITT.getIdentifier());
        arrayBuilder.appendAsBerLength(CRC16_LENGTH);
        arrayBuilder.prependLengthPlus(2);
        arrayBuilder.prepend(getUniversalLabel());
        CrcCcitt crc = new CrcCcitt();
        crc.addData(arrayBuilder.toBytes());
        arrayBuilder.append(crc.getCrc());

        return arrayBuilder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public IInterpretabilityQualityMetadataValue getField(IKlvKey key) {
        return map.get((InterpretabilityQualityMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return InterpretabilityQualityLocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST 1108 Interpretability and Quality";
    }
}
