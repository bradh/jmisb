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
     * @param value ${typeDescription} to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid (e.g. too long).
     */
    public ${namespacedName}(${primitiveType} value) throws KlvParseException {
<#if maxLength??>
        if (value.length() > ${maxLength}) {
            throw new KlvParseException("${namespacedName} maximum length is ${maxLength} characters");
        }
</#if>
        this.implementingValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array.
     */
    public ${namespacedName}(byte[] bytes) {
        this.implementingValue = new String(bytes, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Create ${namespacedName} from encoded bytes with offset.
     *
     * @param bytes Encoded byte array.
     * @param offset the offset into the byte array to start extracting the value
     * @param length the number of bytes to read from the array (starting with the offset)
     */
    public ${namespacedName}(byte[] bytes, int offset, int length) {
        this.implementingValue = new String(bytes, offset, length, java.nio.charset.StandardCharsets.UTF_8);
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${namespacedName} corresponding to the encoded byte array.
     */
    public static ${namespacedName} fromBytes(byte[] bytes) {
        return new ${namespacedName}(bytes);
    }

    @Override
    public String getDisplayName() {
        return "${nameSentenceCase}";
    }

    @Override
    public byte[] getBytes(){
<#if typeName=="Real">
    <#if minValue?? && maxValue??>
        <#if resolution??>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, (double)${resolution});
        <#else>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, Float.BYTES);
        </#if>
        return encoder.encode(implementingValue);
    <#else>
        // TODO: consider a version that allows selection of length 4 or 8 bytes.
        return org.jmisb.core.klv.PrimitiveConverter.float64ToBytes(implementingValue);
    </#if>
<#elseif typeName=="Integer">
        return org.jmisb.core.klv.PrimitiveConverter.int64ToVariableBytes(implementingValue);
<#elseif typeName=="String">
        return this.implementingValue.getBytes(java.nio.charset.StandardCharsets.UTF_8);
<#elseif typeName=="UInt">
        return org.jmisb.core.klv.PrimitiveConverter.uintToVariableBytes(implementingValue);
</#if>
    }

    @Override
    public String getDisplayableValue() {
<#if units?has_content>
        return String.format("${displayFormatter} ${escapedUnits}", this.implementingValue);
<#else>
        return String.format("${displayFormatter}", this.implementingValue);
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
