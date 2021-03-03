<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

<#if minValue??>
    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        <#if (units == "None")>
        assertEquals(uut.getDisplayableValue(), "${minValue}");
        <#else>
        assertEquals(uut.getDisplayableValue(), "${minValue} ${units}");
        </#if>
    }
<#else>
    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}(0);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${namespacedName} uut = new ${namespacedName}(0);
        <#if (units == "None")>
        assertEquals(uut.getDisplayableValue(), "0");
        <#else>
        assertEquals(uut.getDisplayableValue(), "0 ${units}");
        </#if>
    }
</#if>

    @Test
    public void fromBytes1() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {0x01});
        <#if (units == "None")>
        assertEquals(uut.getDisplayableValue(), "1");
        <#else>
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
        </#if>
    }

    @Test
    public void fromBytesConstructor1() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x01});
        <#if (units == "None")>
        assertEquals(uut.getDisplayableValue(), "1");
        <#else>
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
        </#if>
    }

    @Test
    public void getBytes1() {
        ${namespacedName} uut = new ${namespacedName}(1);
        assertEquals(uut.getBytes(), new byte[]{0x01});
    }

<#if maxValue?? && maxValue < 255>
    @Test
    public void getBytesMax() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(${maxValue});
        assertEquals(uut.getBytes(), new byte[]{(byte)${maxValue}});
    }
<#else>
    @Test
    public void getBytes255() {
        ${namespacedName} uut = new ${namespacedName}(255);
        assertEquals(uut.getBytes(), new byte[]{(byte)0xFF});
    }
</#if>

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthTooLong() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {1, 2, 3, 4, 5, 6, 7, 8, 9});
    }

<#if minValue??>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void minValueTooSmall() throws KlvParseException {
        new ${namespacedName}(${minValue} - 1);
    }
<#else>
    @Test (expectedExceptions = IllegalArgumentException.class)
    public void tooSmall() throws KlvParseException {
        new ${namespacedName}(-1);
    }
</#if>

<#if maxValue??>
    @Test
    public void maxValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(${maxValue});
        assertEquals(uut.getValue(), ${maxValue});
    }

    @Test (expectedExceptions = IllegalArgumentException.class)
    public void maxValueTooLarge() throws KlvParseException {
        new ${namespacedName}(${maxValue} + 1);
    }
</#if>
}
