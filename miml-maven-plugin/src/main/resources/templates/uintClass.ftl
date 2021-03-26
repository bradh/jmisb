<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of ${typeDescription}.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final ${primitiveType} implementingValue;

    /**
     * Construct from value.
     *
<#if minValue?? && maxValue??>
     * <p>The value must be in the range [${minValue}, ${maxValue}].
     *
<#elseif minValue??>
     * <p>The minimum value is ${minValue}.
     *
</#if>
<#if units?has_content>
     * <p>The value is in units of ${units}.
     * 
</#if>
     * @param value ${typeDescription} value to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid (e.g. out of range).
     */
    public ${namespacedName}(${primitiveType} value) throws KlvParseException {
<#if minValue??>
        if (value < ${minValue}) {
            throw new KlvParseException("Minimum value for ${namespacedName} is ${minValue}");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new KlvParseException("Maximum value for ${namespacedName} is ${maxValue}");
        }
</#if>
        this.implementingValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed.
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * This version allows parsing of a specific number of bytes from a given offset.
     *
     * @param bytes Encoded byte array
     * @param offset the offset into the byte array to start decoding
     * @param length the number of bytes to decode (1 to 8)
     * @throws KlvParseException if the byte array could not be parsed
     */
    public ${namespacedName}(byte[] bytes, int offset, int length) throws KlvParseException {
        try {
            this.implementingValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToUint64(bytes, offset, length);
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
    public byte[] getBytes(){
        return org.jmisb.core.klv.PrimitiveConverter.uintToVariableBytes(implementingValue);
    }

    @Override
    public String getDisplayableValue() {
<#if units?has_content>
        return String.format("%d ${escapedUnits}", this.implementingValue);
<#else>
        return String.format("%d", this.implementingValue);
</#if>
    }

    /**
     * Get the value of this ${namespacedName}.
     *
<#if units?has_content>
     * @return The value as ${typeDescription}, in units of ${units}.
<#else>
     * @return The value as ${typeDescription}.
</#if>
     */
    public ${primitiveType} getValue() {
        return this.implementingValue;
    }
}
