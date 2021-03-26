<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(0);
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue()  throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(0);
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "0 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "0");
</#if>
    }

    @Test
    public void fromBytes1() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {0x01});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "1");
</#if>
    }

    @Test
    public void fromBytesConstructor1() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x01});
<#if units?has_content>
        assertEquals(uut.getDisplayableValue(), "1 ${units}");
<#else>
        assertEquals(uut.getDisplayableValue(), "1");
</#if>
    }

    @Test
    public void getBytes1() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(1);
        assertEquals(uut.getBytes(), new byte[]{0x01});
    }

    @Test
    public void getBytes255() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(255);
        assertEquals(uut.getBytes(), new byte[]{(byte)0x00, (byte)0xFF});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadLengthConstructor() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBadLength() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {});
    }

<#if minValue??>
    @Test
    public void minValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(${minValue});
        assertEquals(uut.getValue(), ${minValue});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void minValueTooSmall() throws KlvParseException {
        new ${namespacedName}(${minValue} - 1);
    }
</#if>

<#if maxValue??>
    @Test
    public void maxValue() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(${maxValue});
        assertEquals(uut.getValue(), ${maxValue});
    }

    @Test (expectedExceptions = KlvParseException.class)
    public void maxValueTooLarge() throws KlvParseException {
        new ${namespacedName}(${maxValue} + 1);
    }
</#if>
}
