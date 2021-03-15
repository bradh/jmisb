package org.jmisb.api.klv.st1902;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.testng.annotations.Test;

/** Unit tests for Tuple. */
public class TupleTest {

    public TupleTest() {}

    @Test
    public void fromListSingle() throws KlvParseException {
        Tuple uut = new Tuple(new int[] {64});
        assertEquals(uut.getDisplayName(), "Tuple");
        assertEquals(uut.getDisplayableValue(), "(64)");
        assertEquals(uut.getBytes(), new byte[] {0x40});
        assertEquals(uut.getValues(), new int[] {64});
    }

    @Test
    public void fromListMultiple() throws KlvParseException {
        Tuple uut = new Tuple(new int[] {64, 3, 8});
        assertEquals(uut.getDisplayName(), "Tuple");
        assertEquals(uut.getDisplayableValue(), "(64, 3, 8)");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x03, 0x08});
    }

    @Test
    public void fromBytesSingle() throws KlvParseException {
        Tuple uut = new Tuple(new byte[] {0x40});
        assertEquals(uut.getDisplayName(), "Tuple");
        assertEquals(uut.getDisplayableValue(), "(64)");
        assertEquals(uut.getBytes(), new byte[] {0x40});
        assertEquals(uut.getValues(), new int[] {64});
    }

    @Test
    public void fromBytesMultiple() throws KlvParseException {
        Tuple uut = new Tuple(new byte[] {0x40, 0x03, 0x08});
        assertEquals(uut.getDisplayName(), "Tuple");
        assertEquals(uut.getDisplayableValue(), "(64, 3, 8)");
        assertEquals(uut.getBytes(), new byte[] {0x40, 0x03, 0x08});
    }
}
