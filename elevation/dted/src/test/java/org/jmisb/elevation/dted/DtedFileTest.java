package org.jmisb.elevation.dted;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

public class DtedFileTest {

    public DtedFileTest() {}

    @Test
    public void constructorTest() {
        DtedFile dtedFile = new DtedFile();
        assertNotNull(dtedFile);
    }
}
