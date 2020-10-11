package org.jmisb.api.klv.st1206;

import static org.jmisb.api.klv.KlvConstants.SARMILocalSetUl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SARMILocalSet implements IMisbMessage {

    private static final Logger LOGGER = LoggerFactory.getLogger(SARMILocalSet.class);

    /**
     * Create a {@link ISARMIMetadataValue} instance from encoded bytes.
     *
     * @param tag The tag defining the value type
     * @param bytes Encoded bytes
     * @return The new instance
     * @throws KlvParseException if the byte array could not be parsed.
     */
    static ISARMIMetadataValue createValue(SARMIMetadataKey tag, byte[] bytes)
            throws KlvParseException {
        switch (tag) {
            case GrazingAngle:
                return new GrazingAngle(bytes);
            case GroundPlaneSquintAngle:
                return new GroundPlaneSquintAngle(bytes);
            case LookDirection:
                break;
            case ImagePlane:
                break;
            case RangeResolution:
                break;
            case CrossRangeResolution:
                break;
            case RangeImagePlanePixelSize:
                break;
            case CrossRangeImagePlanePixelSize:
                break;
            case ImageRows:
                break;
            case ImageColumns:
                break;
            case RangeDirectionAngleRelativeToTrueNorth:
                return new RangeDirectionAngleRelativeToTrueNorth(bytes);
            case TrueNorthDirectionRelativeToTopImageEdge:
                return new TrueNorthDirectionRelativeToTopImageEdge(bytes);
            case RangeLayoverAngleRelativeToTrueNorth:
                return new RangeLayoverAngleRelativeToTrueNorth(bytes);
            case GroundApertureAngularExtent:
                return new GroundApertureAngularExtent(bytes);
            case ApertureDuration:
                break;
            case GroundTrackAngle:
                return new GroundTrackAngle(bytes);
            case MinimumDetectableVelocity:
                break;
            case TruePulseRepetitionFrequency:
                break;
            case PulseRepetitionFrequencyScaleFactor:
                break;
            case TransmitRFCenterFrequency:
                break;
            case TransmitRFBandwidth:
                break;
            case RadarCrossSectionScaleFactorPolynomial:
                break;
            case ReferenceFramePrecisionTimeStamp:
                break;
            case ReferenceFrameGrazingAngle:
                return new ReferenceFrameGrazingAngle(bytes);
            case ReferenceFrameGroundPlaneSquintAngle:
                return new ReferenceFrameGroundPlaneSquintAngle(bytes);
            case ReferenceFrameRangeDirectionAngleRelativeToTrueNorth:
                return new ReferenceFrameRangeDirectionAngleRelativeToTrueNorth(bytes);
            case ReferenceFrameRangeLayoverAngleRelativeToTrueNorth:
                return new ReferenceFrameRangeLayoverAngleRelativeToTrueNorth(bytes);
            case DocumentVersion:
                break;
            default:
                LOGGER.info("Unknown SAR Motion Imagery Metadata tag: {}", tag);
        }
        return null;
    }

    /** Map containing all elements in the message. */
    private final SortedMap<SARMIMetadataKey, ISARMIMetadataValue> map = new TreeMap<>();

    /**
     * Create the local set from the given key/value pairs.
     *
     * @param values Tag/value pairs to be included in the local set
     */
    public SARMILocalSet(Map<SARMIMetadataKey, ISARMIMetadataValue> values) {
        map.putAll(values);
    }

    /**
     * Build a SAR Motion Imagery Local Set from encoded bytes.
     *
     * @param bytes the bytes to build from
     * @throws KlvParseException if parsing fails
     */
    public SARMILocalSet(final byte[] bytes) throws KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        for (LdsField field : fields) {
            SARMIMetadataKey key = SARMIMetadataKey.getKey(field.getTag());
            switch (key) {
                case Undefined:
                    LOGGER.info("Unknown SAR Motion Imagery Metadata tag: {}", field.getTag());
                    break;
                default:
                    ISARMIMetadataValue value = createValue(key, field.getData());
                    map.put(key, value);
            }
        }
    }

    @Override
    public byte[] frameMessage(boolean isNested) {
        ArrayBuilder builder = new ArrayBuilder();
        for (SARMIMetadataKey tag : map.keySet()) {
            builder.appendAsOID(tag.getIdentifier());
            byte[] valueBytes = getField(tag).getBytes();
            builder.appendAsBerLength(valueBytes.length);
            builder.append(valueBytes);
        }
        if (!isNested) {
            builder.prependLength();
            builder.prepend(SARMILocalSetUl);
        }
        return builder.toBytes();
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public ISARMIMetadataValue getField(IKlvKey key) {
        return map.get((SARMIMetadataKey) key);
    }

    @Override
    public UniversalLabel getUniversalLabel() {
        return SARMILocalSetUl;
    }

    @Override
    public String displayHeader() {
        return "ST1206 SAR Motion Imagery";
    }
}
