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
 * <p>This is a specialisation of a floating point value.
 *
 * <p>See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final double doubleValue;

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
     * @param value the floating point value to initialise this ${namespacedName} with.
     * @throws KlvParseException if the value is not valid (e.g. out of range).
     */
    public ${namespacedName}(double value) throws KlvParseException{
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
        this.doubleValue = value;
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
<#if minValue?? && maxValue??>
     * @param bytes Encoded byte array
<#else>
     * @param bytes Encoded byte array (4 or 8 bytes)
</#if>
     * @throws KlvParseException if the byte array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
<#if minValue?? && maxValue??>
            org.jmisb.api.klv.st1201.FpEncoder decoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, bytes.length);
            this.doubleValue = decoder.decode(bytes);
<#else>
            this.doubleValue = org.jmisb.core.klv.PrimitiveConverter.toFloat64(bytes);
</#if>
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
<#if minValue?? && maxValue??>
     * @param length the number of bytes to decode
<#else>
     * @param length the number of bytes to decode (4 or 8)
</#if>
     * @throws KlvParseException if the byte array could not be parsed
     */
    public ${namespacedName}(byte[] bytes, int offset, int length) throws KlvParseException {
        try {
<#if minValue?? && maxValue??>
            org.jmisb.api.klv.st1201.FpEncoder decoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, length);
            this.doubleValue = decoder.decode(bytes, offset);
<#else>
            this.doubleValue = org.jmisb.core.klv.PrimitiveConverter.toFloat64(bytes, offset, length);
</#if>
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return A ${namespacedName} corresponding to the encoded byte array.
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
<#if minValue?? && maxValue??>
    <#if resolution??>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, (double)${resolution});
    <#else>
        org.jmisb.api.klv.st1201.FpEncoder encoder = new org.jmisb.api.klv.st1201.FpEncoder(${minValue}, ${maxValue}, Float.BYTES);
    </#if>
        return encoder.encode(doubleValue);
<#else>
        // TODO: consider a version that allows selection of length 4 or 8 bytes.
        return org.jmisb.core.klv.PrimitiveConverter.float64ToBytes(doubleValue);
</#if>
    }

    @Override
    public String getDisplayableValue() {
<#if units?has_content>
        return String.format("%.3f ${escapedUnits}", this.doubleValue);
<#else>
        return String.format("%.3f", this.doubleValue);
</#if>
    }

    /**
     * Get the value of this ${namespacedName}.
     *
<#if units?has_content>
     * @return The value as a double, in units of ${units}.
<#else>
     * @return The value as a double.
</#if>
     */
    public double getValue() {
        return this.doubleValue;
    }
}
