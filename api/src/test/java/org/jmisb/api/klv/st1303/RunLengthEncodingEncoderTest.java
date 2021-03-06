package org.jmisb.api.klv.st1303;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for RunLengthEncodingEncoder. */
public class RunLengthEncodingEncoderTest {

    public RunLengthEncodingEncoderTest() {}

    @Test
    public void check2D() throws KlvParseException {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        boolean[][] input =
                new boolean[][] {
                    {false, true, false, false},
                    {true, false, false, false},
                    {true, false, true, false},
                    {true, false, false, false},
                    {true, true, true, true}
                };
        byte[] encoded = encoder.encode(input);
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02,
                    (byte) 0x05,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x05,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x00,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x03
                });
        MDAPDecoder checkDecoder = new MDAPDecoder();
        boolean[][] decoded = checkDecoder.decodeBoolean2D(encoded, 0);
        assertEquals(decoded, input);
    }

    @Test
    public void checkSingleElement2DTrue() {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{true}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x01
                });
    }

    @Test
    public void checkSingleElement2DFalse() {
        RunLengthEncodingEncoder encoder = new RunLengthEncodingEncoder();
        byte[] encoded = encoder.encode(new boolean[][] {{false}});
        assertEquals(
                encoded,
                new byte[] {
                    (byte) 0x02, (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x05, (byte) 0x00
                });
    }
}
