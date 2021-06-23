package org.jmisb.api.klv.st1108;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Tests for DocumentVersion (ST 1108 Tag 10). */
public class DocumentVersionTest {
    @Test
    public void testConstructFromValue() {
        DocumentVersion version = new DocumentVersion(3);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x03});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.3");
        assertEquals(version.getVersion(), 3);
    }

    @Test
    public void testConstructFromValue128() {
        DocumentVersion version = new DocumentVersion(128);
        assertEquals(version.getBytes(), new byte[] {(byte) 0x80});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.128");
        assertEquals(version.getVersion(), 128);
    }

    @Test
    public void testConstructFromValue255() {
        DocumentVersion version = new DocumentVersion(255);
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testConstructFromEncodedBytes() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0x03});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x03});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.3");
        assertEquals(version.getVersion(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes3() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0x03});
        assertEquals(version.getBytes(), new byte[] {(byte) 0x03});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.3");
        assertEquals(version.getVersion(), 3);
    }

    @Test
    public void testConstructFromEncodedBytes255() {
        DocumentVersion version = new DocumentVersion(new byte[] {(byte) 0xFF});
        assertEquals(version.getBytes(), new byte[] {(byte) 0xFF});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.255");
        assertEquals(version.getVersion(), 255);
    }

    @Test
    public void testFactoryEncodedBytes() throws KlvParseException {
        IInterpretabilityQualityMetadataValue value =
                InterpretabilityQualityLocalSet.createValue(
                        InterpretabilityQualityMetadataKey.DocumentVersion,
                        new byte[] {(byte) 0x04});
        assertTrue(value instanceof DocumentVersion);
        DocumentVersion version = (DocumentVersion) value;
        assertEquals(version.getBytes(), new byte[] {(byte) 0x04});
        assertEquals(version.getDisplayName(), "Document Version");
        assertEquals(version.getDisplayableValue(), "ST 1108.4");
        assertEquals(version.getVersion(), 4);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooSmall() {
        new DocumentVersion(2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTooBig() {
        new DocumentVersion(256);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void badArrayLength() {
        new DocumentVersion(new byte[] {0x01, 0x02});
    }
}
