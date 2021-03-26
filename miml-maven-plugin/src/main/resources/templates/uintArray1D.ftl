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
import org.jmisb.api.klv.st1303.MDAPDecoder;
import org.jmisb.api.klv.st1303.UnsignedIntegerEncodingEncoder;
import org.jmisb.api.klv.st1902.ArrayItemKey;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;

/**
<#if parentName == "Base">
 * MIMD ${parentName} ${name} attribute.
<#else>
 * MIMD {@link ${parentName}} ${name} attribute.
</#if>
 *
 * <p>This is a specialisation of an array of unsigned integer values.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue, INestedKlvValue {
    private final long[] uintArray;

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
    public ${namespacedName}(long[] value) throws KlvParseException{
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
        this.uintArray = decoder.decodeUInt1D(bytes, 0);
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
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as a (unsigned) integer array
     */
    public long[] getValue() {
        return this.uintArray.clone();
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        ArrayItemKey key = (ArrayItemKey) tag;
        long value = this.uintArray[key.getIdentifier()];
        IKlvValue field = new IKlvValue() {
            @Override
            public String getDisplayableValue() {
                return String.format("0x%02x", value);
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
        for (int i = 0; i < uintArray.length; ++i) {
            arrayIdentifiers.add(new ArrayItemKey(i));
        }
        return arrayIdentifiers;
    }
}
