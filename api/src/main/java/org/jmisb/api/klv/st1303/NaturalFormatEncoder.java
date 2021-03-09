package org.jmisb.api.klv.st1303;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.core.klv.PrimitiveConverter;

/**
 * Multi-Dimensional Array Pack (MDAP) Natural Format Encoder.
 *
 * <p>This encodes the provided array as a sequence of primitive values.
 */
public class NaturalFormatEncoder {

    /**
     * Constructor.
     *
     * <p>MDAP Natural Format encoding does not require parameters.
     */
    public NaturalFormatEncoder() {}

    /**
     * Encode a two dimensional (unsigned) integer array to a Multi-Dimensional Array Pack using
     * Unsigned Integer Encoding.
     *
     * @param data the array of arrays of ({@code boolean}) values.
     * @return the encoded byte array including the MISB ST1303 header and array data.
     * @throws KlvParseException if the encoding fails, such as for invalid array dimensions.
     */
    public byte[] encode(double[] data) throws KlvParseException {
        if (data.length < 1) {
            throw new KlvParseException("MDAP encoding requires each dimension to be at least 1");
        }
        // Possibly this could be more efficient, but its hard to tell what accuracy we need.
        ArrayBuilder builder =
                new ArrayBuilder()
                        // Number of dimensions
                        .appendAsOID(1)
                        // dim_1
                        .appendAsOID(data.length)
                        // E_bytes value
                        .appendAsOID(Double.BYTES)
                        // array processing algorithm (APA)
                        // note: no array processing algorithm support (APAS)
                        .appendAsOID(ArrayProcessingAlgorithm.NaturalFormat.getCode());
        for (int i = 0; i < data.length; i++) {
            builder.append(PrimitiveConverter.float64ToBytes(data[i]));
        }
        return builder.toBytes();
    }
}
