<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.BooleanArrayEncoder;
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
 * MIMD {@link ${parentName}} ${name} attribute.
 *
 * <p>This is a specialisation of a boolean array.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final boolean[][] value;

    /**
     * Construct from value.
     *
     * @param value the boolean array to initialise this ${namespacedName} with.
     */
    public ${namespacedName}(boolean[][] value) {
        this.value = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
            MDAPDecoder decoder = new MDAPDecoder();
            this.value = decoder.decodeBoolean2D(bytes, 0);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @return new ${namespacedName} corresponding to the encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public static ${namespacedName} fromBytes(byte[] bytes) throws KlvParseException {
        return new ${namespacedName}(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes() {
        try {
            BooleanArrayEncoder encoder = new BooleanArrayEncoder();
            return encoder.encode(value);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public String getDisplayableValue() {
        return "[Boolean 2D array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return The value as an 2D Boolean array.
     */
    public boolean[][] getValue() {
        return this.value.clone();
    }
}
