<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
<#if minValue?? && maxValue??>
import org.jmisb.api.klv.st1201.FpEncoder;
</#if>
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
<#if !(minValue?? && maxValue??)>
import org.jmisb.core.klv.PrimitiveConverter;
</#if>

/**
 * MIMD {@link ${parentName}} ${name} attribute.
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
<#if maxValue??>
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
     */
    public ${namespacedName}(double value) throws IllegalArgumentException{
<#if minValue??>
        if (value < ${minValue}) {
            throw new IllegalArgumentException("Minimum value for ${namespacedName} is ${minValue}");
        }
</#if>
<#if maxValue??>
        if (value > ${maxValue}) {
            throw new IllegalArgumentException("Maximum value for ${namespacedName} is ${maxValue}");
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
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
<#if minValue?? && maxValue??>
            FpEncoder decoder = new FpEncoder(${minValue}, ${maxValue}, bytes.length);
            this.doubleValue = decoder.decode(bytes);
<#else>
            this.doubleValue = PrimitiveConverter.toFloat64(bytes);
</#if>
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
    }

    /**
     * Create ${namespacedName} from encoded bytes with offset.
     *
     * @param bytes Encoded byte array
     * @param offset the offset into the byte array to start decoding
     * @param length the number of bytes to decode
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes, int offset, int length) throws KlvParseException {
        try {
<#if minValue?? && maxValue??>
            FpEncoder decoder = new FpEncoder(${minValue}, ${maxValue}, length);
            this.doubleValue = decoder.decode(bytes, offset);
<#else>
            this.doubleValue = PrimitiveConverter.toFloat64(bytes, offset, length);
</#if>
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
<#if minValue?? && maxValue??>
    <#if resolution??>
        FpEncoder encoder = new FpEncoder(${minValue}, ${maxValue}, (double)${resolution});
    <#else>
        FpEncoder encoder = new FpEncoder(${minValue}, ${maxValue}, 4);
    </#if>
        return encoder.encode(doubleValue);
<#else>
        // TODO: consider a version that allows selection of length 4 or 8 bytes.
        return PrimitiveConverter.float64ToBytes(doubleValue);
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
     * @return the value as a double
     */
    public double getValue() {
        return this.doubleValue;
    }
}
