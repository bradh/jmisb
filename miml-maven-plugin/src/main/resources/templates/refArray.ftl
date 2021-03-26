<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st1902.ArrayItemKey;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import org.jmisb.api.klv.st1902.MimdId;

/**
 * MIMD {@link ${parentName}} ${name} attribute.
 *
 * <p>This is a specialisation of an array of REF values.
 *
 * See ${document} for more information on this data type.
 */
public class ${namespacedName} implements IMimdMetadataValue, INestedKlvValue {
    private final MimdId[] refArray;

    /**
     * Construct from value.
     *
     * @param value the MimdId values to initialise this ${nameSentenceCase} with.
     */
    public ${namespacedName}(MimdId[] value) throws KlvParseException {
        this.refArray = value.clone();
    }

    /**
     * Create ${namespacedName} from encoded bytes.
     *
     * @param bytes Encoded byte array
     * @throws KlvParseException if the array could not be parsed
     */
    public ${namespacedName}(byte[] bytes) throws KlvParseException {
        try {
            List<MimdId> values = new ArrayList<>();
            for (int i = 0; i < bytes.length; ++i) {
                BerField serialField = BerDecoder.decode(bytes, i, true);
                i += serialField.getLength();
                BerField groupField = BerDecoder.decode(bytes, i, true);
                i += groupField.getLength();
                MimdId value = new MimdId(serialField.getValue(), groupField.getValue());
                values.add(value);
            }
            this.refArray = values.toArray(new MimdId[0]);
        } catch (IllegalArgumentException ex) {
            throw new KlvParseException(ex.getMessage());
        }
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
        ArrayBuilder builder = new ArrayBuilder();
        for (MimdId ref: this.refArray) {
            builder.appendAsOID(ref.getSerialNumber());
            builder.appendAsOID(ref.getGroupIdentifier());
        }
        return builder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[${nameSentenceCase} Array]";
    }

    /**
     * Get the value of this ${namespacedName}.
     *
     * @return the value as an array of MimdId.
     */
    public MimdId[] getValue() {
        return this.refArray.clone();
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        ArrayItemKey key = (ArrayItemKey) tag;
        MimdId value = this.refArray[key.getIdentifier()];
        IKlvValue field = new IKlvValue() {
            @Override
            public String getDisplayableValue() {
                return String.format("(%d, %d)", value.getSerialNumber(), value.getGroupIdentifier());
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
        for (int i = 0; i < refArray.length; ++i) {
            arrayIdentifiers.add(new ArrayItemKey(i));
        }
        return arrayIdentifiers;
    }
}
