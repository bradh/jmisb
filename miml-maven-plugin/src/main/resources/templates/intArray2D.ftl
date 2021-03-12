<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1303.NaturalFormatEncoder;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
<#if !(minValue?? && maxValue??)>
import org.jmisb.core.klv.PrimitiveConverter;
</#if>

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of a 2D array of signed integer values.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final long[][] intArray;

    /**
     * Construct from value.
     *
<#if maxValue??>
     * Each array value must be in the range [${minValue}, ${maxValue}].
     *
<#elseif minValue??>
     * The minimum value for each element in the array is ${minValue}.
     *
</#if>
     * @param value the signed integer values to initialise this ${nameSentenceCase} with.
     */
    public ${namespacedName}(long[][] value) throws IllegalArgumentException{
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] < ${minValue}) {
                    throw new IllegalArgumentException("Minimum value for ${namespacedName} elements is ${minValue}");
                }
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] > ${maxValue}) {
                    throw new IllegalArgumentException("Maximum value for ${namespacedName} elements is ${maxValue}");
                }
            }
        }
</#if>
        this.intArray = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.intArray = decoder.decodeInt2D(bytes, 0);
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @return new ${nameSentenceCase} corresponding to the encoded byte array.
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
        try {
            NaturalFormatEncoder encoder = new NaturalFormatEncoder();
            return encoder.encode(this.intArray);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public String getDisplayableValue() {
        // TODO: see if we can return something useful here
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as a 2D signed integer array
     */
    public long[][] getValue() {
        return this.intArray.clone();
    }
}
