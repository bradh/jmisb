<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName}. */
public class ${namespacedName}Test {

    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}(0.0);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${namespacedName} uut = new ${namespacedName}(0.0);
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "0.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "0.000");
</#if>
    }

<#if minValue?? && maxValue??>
    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
        assertEquals(uut.getDisplayableValue(), "${minValue?string["0.000"]} ${units}");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBad() throws KlvParseException {
        ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x04,
                    (byte) 0x05,
                    (byte) 0x06,
                    (byte) 0x07,
                    (byte) 0x08,
                    (byte) 0x09});
    }
<#else>
    @Test
    public void fromBytes8() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "2.000");
</#if>
    }

    @Test
    public void fromBytesConstructor8() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "2.000");
</#if>
    }

    @Test
    public void fromBytes8Negative() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0xC0,
                    (byte) 0x10,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "-4.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "-4.000");
</#if>
    }

    @Test
    public void fromBytes4() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "2.000");
</#if>
    }

    @Test
    public void fromBytesConstructor4Offset() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00},
                    2,
                    4);
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "2.000");
</#if>
    }

    @Test
    public void fromBytesConstructor8Offset() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x03,
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00},
                    3,
                    8);
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "2.000 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "2.000");
</#if>
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructorOffset() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00},
                    1,
                    5);
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }
</#if>

<#if minValue?? && maxValue??>
<#if resolution??>
    @Test
    public void getBytes() {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        byte[] bytes = uut.getBytes();
        // Attempt to handle variation based on resolution (IMAPA).
        assertTrue(bytes.length >= 1);
        for (int i = 0; i < bytes.length; ++i) {
            assertEquals(bytes[i], (byte)0x00);
        }
    }
<#else>
    @Test
    public void getBytes() {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        assertEquals(uut.getBytes(), new byte[] {
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }
</#if>
<#else>
    @Test
    public void getBytes() {
        ${namespacedName} uut = new ${namespacedName}(2.0);
        assertEquals(uut.getBytes(), new byte[] {
                    (byte) 0x40,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00,
                    (byte) 0x00});
    }
</#if>

<#if minValue??>
    @Test
    public void fromValueMin() {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        assertNotNull(uut);
        assertEquals(uut.getValue(), ${minValue}, 0.000001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueMinOutOfRange() {
        new ${namespacedName}(${minValue} - 0.5);
    }
</#if>

<#if maxValue??>
    @Test
    public void fromValueMax() {
        ${namespacedName} uut = new ${namespacedName}(${maxValue});
        assertNotNull(uut);
        assertEquals(uut.getValue(), ${maxValue}, 0.000001);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueMaxOutOfRange() {
        new ${namespacedName}(${maxValue} + 0.5);
    }
</#if>
}
