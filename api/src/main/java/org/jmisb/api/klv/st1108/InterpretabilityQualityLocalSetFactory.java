package org.jmisb.api.klv.st1108;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IMisbMessageFactory;

/**
 * Factory method for InterpretabilityQualityLocalSet.
 *
 * <p>This is used to link the ST 1108 Interpretability and Quality Metadata Local Set handling into
 * the wider jMISB implementation, and is not usually required directly.
 */
public class InterpretabilityQualityLocalSetFactory implements IMisbMessageFactory {

    @Override
    public InterpretabilityQualityLocalSet create(byte[] bytes) throws KlvParseException {
        return new InterpretabilityQualityLocalSet(bytes);
    }
}
