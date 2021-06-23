package org.jmisb.api.klv.st1108;

import java.util.HashMap;
import java.util.Map;
import org.jmisb.api.klv.IKlvKey;

/** ST 1108 tags - description and numbers. */
public enum InterpretabilityQualityMetadataKey implements IKlvKey {
    /**
     * Unknown key.
     *
     * <p>This should not be created. It is used to provide a mapping for "any other value".
     */
    Undefined(0),
    /**
     * Assessment Point.
     *
     * <p>Location in workflow metric evaluated specified as an enumeration.
     */
    AssessmentPoint(1),
    /**
     * Metric Period Pack.
     *
     * <p>Period within metric calculated.
     */
    MetricPeriodPack(2),
    // TODO: lots missing in here
    /**
     * Compression Type.
     *
     * <p>Video encoding / compression type, specified as an enumeration.
     */
    CompressionType(5),
    /**
     * Compression Profile.
     *
     * <p>Compression profile (settings) for video encoding, specified as an enumeration.
     */
    CompressionProfile(6),
    /**
     * Compression Level.
     *
     * <p>The compression level expressed as a String; ex: "5", "4.2", "5.1". "N/A" for
     * uncompressed.
     */
    CompressionLevel(7),
    /**
     * Compression Ratio.
     *
     * <p>Source-to-compressed size; ex: 25.2 means 25.2:1. Set to 1.0 for uncompressed.
     */
    CompressionRatio(8),
    /**
     * Stream Bit Rate.
     *
     * <p>Floating point bit rate, in kilobits per second.
     */
    StreamBitrate(9),
    /**
     * Document Version.
     *
     * <p>Version number of this standard.
     */
    DocumentVersion(10),
    /**
     * CRC-16-CCITT.
     *
     * <p>Two-byte checksum on Local Set.
     */
    CRC16CCITT(11);

    private int tag;

    private static final Map<Integer, InterpretabilityQualityMetadataKey> tagTable =
            new HashMap<>();

    static {
        for (InterpretabilityQualityMetadataKey key : values()) {
            tagTable.put(key.tag, key);
        }
    }

    InterpretabilityQualityMetadataKey(int tag) {
        this.tag = tag;
    }

    /**
     * Get the tag value associated with this enumeration value.
     *
     * @return integer tag value for the metadata key
     */
    @Override
    public int getIdentifier() {
        return tag;
    }

    /**
     * Look up the metadata key by tag identifier.
     *
     * @param tag the integer tag value to look up
     * @return corresponding metadata key, or Undefined if the key is not known / valid.
     */
    public static InterpretabilityQualityMetadataKey getKey(int tag) {
        return tagTable.getOrDefault(tag, Undefined);
    }
}