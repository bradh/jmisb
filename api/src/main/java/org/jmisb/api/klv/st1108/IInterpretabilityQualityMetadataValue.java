package org.jmisb.api.klv.st1108;

import org.jmisb.api.klv.IKlvValue;

public interface IInterpretabilityQualityMetadataValue extends IKlvValue {
    /**
     * Get the encoded bytes.
     *
     * @return The encoded byte array
     */
    byte[] getBytes();
}
