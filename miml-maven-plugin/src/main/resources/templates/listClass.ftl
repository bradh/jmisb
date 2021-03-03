// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;

/**
 * MIMD LIST&lt;${name}&gt; implementation.
 *
 * See ${document} for more information on this data type.
 */
public class List_${name} implements IMimdMetadataValue, INestedKlvValue {
    private final Map<List_${name}Identifier, ${name}> listValues = new HashMap<>();
    private final String displayName;

    /**
     * Create a $LIST&lt;${name}&gt; from values.
     *
     * @param values the values to construct from
     * @param displayName the display name (label) to use for this list.
     */
    public List_${name}(Map<List_${name}Identifier, ${name}> values, String displayName) {
        listValues.putAll(values);
        this.displayName = displayName;
    }

    /**
     * Build a $LIST&lt;${name}&gt; from encoded bytes.
     *
     * @param data the bytes to build from
     * @param offset the offset into {@code bytes} to start parsing from
     * @param numBytes the number of bytes to parse
     * @param displayName the display name (label) to use for this list.
     * @throws KlvParseException if parsing fails
     */
    public List_${name}(byte[] data, int offset, int numBytes, String displayName) throws KlvParseException {
        this.displayName = displayName;
        int index = offset;
        int itemCount = 0;
        while (index < data.length - 1) {
            BerField lengthField = BerDecoder.decode(data, index, false);
            // TODO: handle lengthField == 0, which is a special case - ZLE.
            // Zero-Length-Element (ZLE) as a filler element to mark an element as unchanged since the last Packet
            index += lengthField.getLength();
            ${name} listItem = new ${name}(data, index, lengthField.getValue());
            listValues.put(new List_${name}Identifier(itemCount), listItem);
            index += lengthField.getValue();
            itemCount++;
        }
    }

    /**
     * Build a LIST&lt;${name}&gt; from encoded bytes.
     *
     * @param data the bytes to build from
     * @param displayName the display name (label) to use for this list.
     * @return new LIST_${name} corresponding to the encoded byte array.
     * @throws KlvParseException if parsing fails
     */
    public static List_${name} fromBytes(byte[] data, String displayName) throws KlvParseException {
        return new List_${name}(data, 0, data.length, displayName);
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        for (${name} value : listValues.values()) {
            byte[] listItemBytes = value.getBytes();
            arrayBuilder.appendAsBerLength(listItemBytes.length);
            arrayBuilder.append(listItemBytes);
        }
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "LIST[${name}]";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return listValues.get((List_${name}Identifier) tag);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return listValues.keySet();
    }
}
