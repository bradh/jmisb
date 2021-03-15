package org.jmisb.api.klv.st1902;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.BerDecoder;
import org.jmisb.api.klv.BerField;

/**
 * MIMD Tuple.
 *
 * <p>The Tuple Type is typically a short sequence of Unsigned Integers (UInts) for use within the
 * MIMD Model’s addressing method (for Directed Association) along with other uses. The Tuple Type
 * is the same as a one-dimensional array of UInt, but because of its short length and the
 * restriction to one dimension, it is a separate type to enable more efficient transmutations.
 */
public class Tuple implements IMimdMetadataValue {

    private final List<Integer> values = new ArrayList<>();
    /**
     * Create a Tuple from value.
     *
     * @param value the array of (unsigned) integer values for the tuple
     */
    public Tuple(int[] value) {
        this.values.addAll(Arrays.stream(value).boxed().collect(Collectors.toList()));
    }

    /**
     * Create a Tuple from encoded bytes.
     *
     * @param data the byte array to parse the Tuple from.
     * @throws KlvParseException if the parsing fails
     */
    public Tuple(byte[] data) throws KlvParseException {
        for (int offset = 0; offset < data.length; ) {
            BerField tupleField = BerDecoder.decode(data, offset, true);
            offset += tupleField.getLength();
            values.add(tupleField.getValue());
        }
    }

    /**
     * Parse a Tuple out of a byte array.
     *
     * @param data the byte array to parse the Tuple from.
     * @return Tuple equivalent to the byte array
     * @throws KlvParseException if the parsing fails
     */
    public static Tuple fromBytes(byte[] data) throws KlvParseException {
        return new Tuple(data);
    }

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        this.values.forEach(
                (value) -> {
                    arrayBuilder.appendAsOID(value);
                });
        return arrayBuilder.toBytes();
    }

    /**
     * Get the values for this Tuple.
     *
     * @return the values as an (unsigned) integer array.
     */
    public int[] getValues() {
        return this.values.stream().mapToInt(i -> i).toArray();
    }

    @Override
    public String getDisplayName() {
        return "Tuple";
    }

    @Override
    public String getDisplayableValue() {
        return "("
                + this.values.stream().map(Object::toString).collect(Collectors.joining(", "))
                + ")";
    }
}
