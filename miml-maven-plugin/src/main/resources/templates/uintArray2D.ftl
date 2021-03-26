<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of a 2D array of unsigned integer values.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue {
    private final long[][] uintArray;

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
     * @param value the unsigned integer values to initialise this ${nameSentenceCase} with.
     */
    public ${namespacedName}(long[][] value) throws KlvParseException{
<#if arrayDimensionSize(0)??>
        if (value.length != ${arrayDimensionSize(0)}) {
            throw new KlvParseException("Required number of ${namespacedName} rows is ${arrayDimensionSize(0)}");
        }
</#if>
<#if arrayDimensionSize(1)??>
        if (value[0].length != ${arrayDimensionSize(1)}) {
            throw new KlvParseException("Required number of ${namespacedName} columns is ${arrayDimensionSize(1)}");
        }
</#if>
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] < ${minValue}) {
                    throw new KlvParseException("Minimum value for ${namespacedName} elements is ${minValue}");
                }
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            for (int j = 0; j < value[i].length; ++j) {
                if (value[i][j] > ${maxValue}) {
                    throw new KlvParseException("Maximum value for ${namespacedName} elements is ${maxValue}");
                }
            }
        }
</#if>
        this.uintArray = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        MDAPDecoder decoder = new MDAPDecoder();
        this.uintArray = decoder.decodeUInt2D(bytes, 0);
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
            UnsignedIntegerEncodingEncoder encoder = new UnsignedIntegerEncodingEncoder();
            return encoder.encode(this.uintArray);
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
     * @return the value as a 2D (unsigned) integer array
     */
    public long[][] getValue() {
        return this.uintArray.clone();
    }
}
