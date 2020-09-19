package org.jmisb.maven;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ClassEntryModel and associated parsing. */
public class ClassEntryModelTest {

    public ClassEntryModelTest() {}

    @Test
    public void minValue() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getMinValue(), 0.0);
        assertNull(entry.getMaxValue());
    }

    @Test
    public void number() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getNumber(), 38);
    }

    @Test
    public void name() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getName(), "syncPulseFreq");
    }

    @Test
    public void nameSentenceCase() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getNameSentenceCase(), "SyncPulseFreq");
    }

    @Test
    public void type() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getTypeName(), "Real");
    }

    @Test
    public void units() {
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        assertEquals(entry.getUnits(), "Hz");
        assertEquals(entry.getEscapedUnits(), "Hz");
        assertFalse(entry.isDeprecated());
    }

    @Test
    public void unitsDeprecated() {
        ClassModelEntry entry =
                Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz, DEPRECATED};  ");
        assertEquals(entry.getUnits(), "Hz");
        assertEquals(entry.getEscapedUnits(), "Hz");
        assertTrue(entry.isDeprecated());
    }

    @Test
    public void escapedUnits() {
        ClassModelEntry entry =
                Parser.parseClassEntry("34_relativeHumidity     : Real (0, 100, 0.1)      {  %}; ");
        assertEquals(entry.getUnits(), "%");
        assertEquals(entry.getEscapedUnits(), "%%");
    }

    @Test
    public void realTraits() {
        ClassModelEntry entry =
                Parser.parseClassEntry("34_relativeHumidity     : Real (0, 100, 0.1)      {  %}; ");
        assertEquals(entry.getMinValue(), 0.0, 0.0000000000001);
        assertEquals(entry.getMaxValue(), 100.0, 0.0000000000001);
        assertEquals(entry.getResolution(), 0.1, 0.0000000000001);
    }

    @Test
    public void intTraits() {
        ClassModelEntry entry =
                Parser.parseClassEntry(
                        "41_exposureEndTimeOffset : Integer (-1000000000, 1000000000) {ns};");
        assertEquals(entry.getMinValue(), -1000000000.0, 0.0000000000001);
        assertEquals(entry.getMaxValue(), 1000000000, 0.0000000000001);
    }

    @Test
    public void uintTraits() {
        ClassModelEntry entry = Parser.parseClassEntry("33_laserCode : UInt (0, 65535) {None};");
        assertEquals(entry.getMinValue(), 0, 0.0000000000001);
        assertEquals(entry.getMaxValue(), 65535, 0.0000000000001);
    }

    @Test
    public void uintTraitsMin() {
        ClassModelEntry entry = Parser.parseClassEntry("37_level : UInt (1) {None};");
        assertEquals(entry.getMinValue(), 1, 0.0000000000001);
        assertNull(entry.getMaxValue());
    }

    @Test
    public void maxValue() {
        ClassModelEntry entry =
                Parser.parseClassEntry(
                        "  36_differentialPressure : Real (0, 5000)          {hPa};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 5000.0);
    }

    @Test
    public void maxValue2PI() {
        ClassModelEntry entry =
                Parser.parseClassEntry(
                        "  38_windDirection        : Real (0, TWO_PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 6.283185307179586, 0.000000000000001);
    }

    @Test
    public void maxValuePI() {
        ClassModelEntry entry =
                Parser.parseClassEntry("  38_windDirection        : Real (0, PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 3.141592653589793, 0.000000000000001);
    }

    @Test
    public void maxValueHALF_PI() {
        ClassModelEntry entry =
                Parser.parseClassEntry(
                        "  38_windDirection        : Real (0, HALF_PI)        {rad};");
        assertEquals(entry.getMinValue(), 0.0);
        assertEquals(entry.getMaxValue(), 1.5707963267948965, 0.000000000000001);
    }

    @Test
    public void REF() {
        ClassModelEntry entry = Parser.parseClassEntry("  02_timer : REF<Timer> {None};  ");
        assertEquals(entry.getNumber(), 2);
        assertEquals(entry.getRefItemType(), "Timer");
        assertEquals(entry.getName(), "timer");
        assertEquals(entry.getNameSentenceCase(), "Timer");
        assertEquals(entry.getUnits(), "None");
    }

    @Test
    public void LIST() {
        ClassModelEntry entry = Parser.parseClassEntry("38_timers : LIST<Timer> (1, *) {None}; ");
        assertEquals(entry.getNumber(), 38);
        assertEquals(entry.getListItemType(), "Timer");
        assertEquals(entry.getName(), "timers");
        assertEquals(entry.getNameSentenceCase(), "Timers");
        assertEquals(entry.getUnits(), "None");
        assertEquals(entry.getMinLength().longValue(), 1L);
        assertNull(entry.getMaxLength());
    }

    @Test
    public void LIST28() {
        ClassModelEntry entry = Parser.parseClassEntry("38_timers : LIST<Timer> (2, 8) {None}; ");
        assertEquals(entry.getNumber(), 38);
        assertEquals(entry.getListItemType(), "Timer");
        assertEquals(entry.getName(), "timers");
        assertEquals(entry.getNameSentenceCase(), "Timers");
        assertEquals(entry.getUnits(), "None");
        assertEquals(entry.getMinLength().longValue(), 2L);
        assertEquals(entry.getMaxLength().longValue(), 8L);
    }

    @Test
    public void parent() {
        ClassModel parent = new ClassModel();
        parent.setDocument("ST190x");
        parent.setPackageNameBase("test.miml");
        ClassModelEntry entry = Parser.parseClassEntry("  38_syncPulseFreq : Real (0.0) { Hz};  ");
        entry.setParent(parent);
        assertEquals(entry.getDocument(), "ST190x");
        assertEquals(entry.getPackageName(), "test.miml.st190x");
        assertFalse(entry.isDeprecated());
    }
}
