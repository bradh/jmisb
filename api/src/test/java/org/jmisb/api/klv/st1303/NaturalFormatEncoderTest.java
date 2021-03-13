package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for BooleanArrayEncoder. */
public class NaturalFormatEncoderTest {

    public NaturalFormatEncoderTest() {}

    @Test(expectedExceptions = KlvParseException.class)
    public void check1DRealBadLength() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new double[0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[1][0]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRows() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[0][1]);
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void check2DIntBadRowsAndColumns() throws KlvParseException {
        NaturalFormatEncoder encoder = new NaturalFormatEncoder();
        assertNotNull(encoder);
        encoder.encode(new long[0][0]);
    }
}
