// Generated file - changes will be lost on rebuild
// Template: uintClass.ftl
package org.jmisb.api.klv.st1907;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * MIMD {@link CorrespondenceGroupMask} mask attribute.
 *
 * <p>This is a specialisation of a boolean array.
 *
 * <p>See ST1907 for more information on this data type.
 */
public class CorrespondenceGroupMask_Mask implements IMimdMetadataValue {
    private final long uintValue;

    /**
     * Construct from value.
     *
     * <p>The value is in units of None.
     *
     * @param value the unsigned integer value to initialise this
     *     CorrespondenceGroupMask_UpperLeftCol with.
     */
    public CorrespondenceGroupMask_Mask(long value) {
        if (value < 0) {
            throw new IllegalArgumentException(
                    "Minimum value for CorrespondenceGroupMask_UpperLeftCol is 0");
        }
        this.uintValue = value;
    }

    /**
     * Create CorrespondenceGroupMask_UpperLeftCol from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public CorrespondenceGroupMask_Mask(byte[] bytes) throws KlvParseException {
        try {
            this.uintValue = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create CorrespondenceGroupMask_UpperLeftCol from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @return new CorrespondenceGroupMask_UpperLeftCol corresponding to the encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public static CorrespondenceGroupMask_Mask fromBytes(byte[] bytes) throws KlvParseException {
        return new CorrespondenceGroupMask_Mask(bytes);
    }

    @Override
    public String getDisplayName() {
        return "Mask";
    }

    @Override
    public byte[] getBytes() {
        return PrimitiveConverter.uintToVariableBytes(uintValue);
    }

    @Override
    public String getDisplayableValue() {
        return String.format("%d", this.uintValue);
    }

    /**
     * Get the value of this CorrespondenceGroupMask_UpperLeftCol.
     *
     * @return The value as an unsigned long, in units of None.
     */
    public long getValue() {
        return this.uintValue;
    }
}
