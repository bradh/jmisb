package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for Stream Bitrate (ST 1108 Tag 9). */
public class StreamBitrateTest {

    @Test
    public void testConstructFromValue() {
        StreamBitrate uut = new StreamBitrate(3);
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x00, (byte) 0x03});
        assertEquals(uut.getDisplayName(), "Stream Bitrate");
        assertEquals(uut.getDisplayableValue(), "3 Kbits/sec");
        assertEquals(uut.getBitrate(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        StreamBitrate uut = new StreamBitrate(new byte[] {(byte) 0x03, (byte) 0xe7});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0x03, (byte) 0xe7});
        assertEquals(uut.getDisplayName(), "Stream Bitrate");
        assertEquals(uut.getDisplayableValue(), "999 Kbits/sec");
        assertEquals(uut.getBitrate(), 999);
    }

    @Test
    public void testConstructFromEncodedBytesMax() {
        StreamBitrate uut = new StreamBitrate(new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(uut.getBytes(), new byte[] {(byte) 0xFF, (byte) 0xFF});
        assertEquals(uut.getDisplayName(), "Stream Bitrate");
        assertEquals(uut.getDisplayableValue(), "65.535 Mbits/sec");
        assertEquals(uut.getBitrate(), 65535);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IInterpretabilityQualityMetadataValue value =
                InterpretabilityQualityLocalSet.createValue(
                        InterpretabilityQualityMetadataKey.StreamBitrate,
                        new byte[] {(byte) 0x08, (byte) 0xFF});
        assertTrue(value instanceof StreamBitrate);
        StreamBitrate bitrate = (StreamBitrate) value;
        assertEquals(bitrate.getBytes(), new byte[] {(byte) 0x08, (byte) 0xFF});
        assertEquals(bitrate.getDisplayName(), "Stream Bitrate");
        assertEquals(bitrate.getDisplayableValue(), "2.303 Mbits/sec");
        assertEquals(bitrate.getBitrate(), 2303);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new StreamBitrate(-1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new StreamBitrate(65536);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthShort() {
        new StreamBitrate(new byte[] {0x01});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLengthLong() {
        new StreamBitrate(new byte[] {0x01, 0x02, 0x03});
    }
}
