<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
<#if minValue?? && maxValue??>
import org.jmisb.api.klv.st1303.ElementProcessedEncoder;
<#else>
import org.jmisb.api.klv.st1303.NaturalFormatEncoder;
</#if>
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1902.ArrayItemKey;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of an array of floating point values.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue, INestedKlvValue  {
    private final double[] doubleArray;

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
     * @param value the floating point values to initialise this ${nameSentenceCase} with.
     */
    public ${namespacedName}(double[] value) throws KlvParseException{
<#if arrayDimensionSize(0)??>
        if (value.length != ${arrayDimensionSize(0)}) {
            throw new KlvParseException("Required number of ${namespacedName} elements is ${arrayDimensionSize(0)}");
        }
</#if>
<#if minValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] < ${minValue}) {
                throw new KlvParseException("Minimum value for ${namespacedName} elements is ${minValue}");
            }
        }
</#if>
<#if maxValue??>
        for (int i = 0; i < value.length; ++i) {
            if (value[i] > ${maxValue}) {
                throw new KlvParseException("Maximum value for ${namespacedName} elements is ${maxValue}");
            }
        }
</#if>
        this.doubleArray = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException { 
        MDAPDecoder decoder = new MDAPDecoder();
        this.doubleArray = decoder.decodeFloatingPoint1D(bytes, 0);
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
    <#if resolution??>
            ElementProcessedEncoder encoder = new ElementProcessedEncoder(${minValue}, ${maxValue}, (double)${resolution});
    <#elseif minValue?? && maxValue??>
            ElementProcessedEncoder encoder = new ElementProcessedEncoder(${minValue}, ${maxValue}, Float.BYTES});
    <#else>
            NaturalFormatEncoder encoder = new NaturalFormatEncoder();
    </#if>
            return encoder.encode(this.doubleArray);
        } catch (KlvParseException ex) {
            return new byte[0];
        }
    }

    @Override
    public String getDisplayableValue() {
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as a double array
     */
    public double[] getValue() {
        return this.doubleArray.clone();
    }

   @Override
    public IKlvValue getField(IKlvKey tag) {
        ArrayItemKey key = (ArrayItemKey) tag;
        double value = this.doubleArray[key.getIdentifier()];
        IKlvValue field = new IKlvValue() {
            @Override
            public String getDisplayableValue() {
                return String.format("%f", value);
            }

            @Override
            public String getDisplayName() {
                return String.format("%d", key.getIdentifier());
            }
        };
        return field;
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        SortedSet<ArrayItemKey> arrayIdentifiers = new TreeSet<>();
        for (int i = 0; i < doubleArray.length; ++i) {
            arrayIdentifiers.add(new ArrayItemKey(i));
        }
        return arrayIdentifiers;
    }
}
