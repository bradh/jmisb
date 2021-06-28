package org.jmisb.api.klv.st1108;

import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IMisbMessage;
import org.jmisb.api.klv.IMisbMessageFactory;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.UniversalLabel;
import org.jmisb.api.klv.st1108.st1108_2.LegacyInterpretabilityQualityLocalSet;
import org.jmisb.api.klv.st1108.st1108_3.InterpretabilityQualityLocalSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory method for InterpretabilityQualityLocalSet.
 *
 * <p>This is used to link the ST 1108 Interpretability and Quality Metadata Local Set handling into
 * the wider jMISB implementation, and is not usually required directly.
 */
public class InterpretabilityQualityLocalSetFactory implements IMisbMessageFactory {
    private static final int ST1108_2_VERSION = 2;
    private static final int ST1108_3_VERSION = 3;
    private static final Logger LOGGER =
            LoggerFactory.getLogger(InterpretabilityQualityLocalSetFactory.class);

    @Override
    public IMisbMessage create(byte[] bytes) throws KlvParseException {
        List<LdsField> fields = getFields(bytes);
        int version = getVersion(fields);
        switch (version) {
            case ST1108_2_VERSION:
                return LegacyInterpretabilityQualityLocalSet.fromST1108_2Fields(fields, bytes);
            case ST1108_3_VERSION:
                return InterpretabilityQualityLocalSet.fromST1108_3Fields(fields, bytes);
            default:
                LOGGER.warn("Unsupported/unknown ST 1108 version");
                return null;
        }
    }

    private int getVersion(List<LdsField> fields) {
        // TODO: work out heuristics for this, open question into MISB.
        return ST1108_3_VERSION;
    }

    private static List<LdsField> getFields(final byte[] bytes)
            throws IllegalArgumentException, KlvParseException {
        int offset = UniversalLabel.LENGTH;
        BerField len = BerDecoder.decode(bytes, offset, false);
        offset += len.getLength();
        List<LdsField> fields = LdsParser.parseFields(bytes, offset, len.getValue());
        return fields;
    }
}
