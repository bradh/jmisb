<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * ${name} MIMD Unsigned Integer.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final long uintValue;

    /**
     * Construct from value.
     *
<#if minValue?? && maxValue??>
     * The value must be in the range [${minValue}, ${maxValue}].
<#elseif minValue??>
     * The value must be at least ${minValue}.
</#if>
     * @param value the unsigned integer value to initialise this ${namespacedName} with.
     */
    public ${namespacedName}(long value) {
<#if minValue??>
        if (value < ${minValue}) {
            throw new IllegalArgumentException("Minimum value for ${namespacedName} is ${minValue}");
        }
<#else>
        if (value < 0) {
            throw new IllegalArgumentException("Minimum value for ${namespacedName} is 0");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new IllegalArgumentException("Maximum value for ${namespacedName} is ${maxValue}");
        }
</#if>
        this.uintValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
            this.uintValue = PrimitiveConverter.variableBytesToUint64(bytes);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${namespacedName} corresponding to the encoded byte array.
     * @throws KlvParseException if the array could not be parsed
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
        return PrimitiveConverter.uintToVariableBytes(uintValue);
    }

    @Override
    public String getDisplayableValue() {
        <#if !(units == "None")>
        return String.format("%d ${units}", this.uintValue);
        <#else>
        return String.format("%d", this.uintValue);
        </#if>
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as an unsigned long
     */
    public long getValue() {
        return this.uintValue;
    }
}
