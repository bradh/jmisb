package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for CompressionRatio (ST 1108 Tag 8). */
public class CompressionRatioTest {

    @Test
    public void testConstructFromValue() {
        CompressionRatio uut = new CompressionRatio(25.2);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Compression Ratio");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getCompressionRatio(), 25.20000, 0.00001);
    }

    @Test
    public void testConstructFromValueFloat() {
        CompressionRatio uut = new CompressionRatio(25.2f);
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Compression Ratio");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getCompressionRatio(), 25.20000, 0.00001);
    }

    @Test
    public void testConstructFromEncodedBytes4() {
        CompressionRatio uut =
                new CompressionRatio(
                        new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(
                uut.getBytes(), new byte[] {(byte) 0x41, (byte) 0xc9, (byte) 0x99, (byte) 0x9a});
        assertEquals(uut.getDisplayName(), "Compression Ratio");
        assertEquals(uut.getDisplayableValue(), "25.20");
        assertEquals(uut.getCompressionRatio(), 25.200000, 0.00001);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IInterpretabilityQualityMetadataValue value =
                InterpretabilityQualityLocalSet.createValue(
                        InterpretabilityQualityMetadataKey.CompressionRatio,
                        new byte[] {(byte) 0x41, (byte) 0x19, (byte) 0x99, (byte) 0x9a});
        assertTrue(value instanceof CompressionRatio);
        CompressionRatio bitrate = (CompressionRatio) value;
        assertEquals(
                bitrate.getBytes(),
                new byte[] {(byte) 0x41, (byte) 0x19, (byte) 0x99, (byte) 0x9a});
        assertEquals(bitrate.getDisplayName(), "Compression Ratio");
        assertEquals(bitrate.getDisplayableValue(), "9.60");
        assertEquals(bitrate.getCompressionRatio(), 9.60000, 0.000001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new CompressionRatio(-1.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new CompressionRatio(new byte[] {0x01, 0x02, 0x03});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new CompressionRatio(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new CompressionRatio(new byte[] {0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x8, 0x09});
    }
}
