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
 * <p>This is a specialisation of a signed integer.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final long intValue;

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
     * @param value the signed integer value to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid (e.g. out of range).
     */
    public ${namespacedName}(long value) throws KlvParseException {
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
        this.intValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the byte array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
            this.intValue = org.jmisb.core.klv.PrimitiveConverter.variableBytesToInt64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${namespacedName} corresponding to the encoded byte array.
     * @throws KlvParseException if the byte array could not be parsed
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
        return org.jmisb.core.klv.PrimitiveConverter.int64ToVariableBytes(intValue);
    }

    @Override
    public String getDisplayableValue() {
        <#if units??>
        return String.format("%d ${units}", this.intValue);
        <#else>
        return String.format("%d", this.intValue);
        </#if>
    }

    /**
     * Get the value of this ${namespacedName}.
     *
<#if units?has_content>
     * @return The value as a signed long, in units of ${units}.
<#else>
     * @return The value as a signed long.
</#if>
     */
    public long getValue() {
        return this.intValue;
    }
}
