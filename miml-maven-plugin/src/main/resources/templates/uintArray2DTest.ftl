<#setting number_format="computer">
<#assign
    minVal=minValue!0
    arrayDimension0=arrayDimensionSize(0)!1
    arrayDimension1=arrayDimensionSize(1)!1
>
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import org.jmisb.api.common.KlvParseException;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}(
            new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() {
        ${namespacedName} uut = new ${namespacedName}(new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() {
        ${namespacedName} uut = new ${namespacedName}(new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getValue(), new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getBytes(),
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..(arrayDimension0*arrayDimension1) as x>(byte) 0x00<#sep>,
                </#list>});
    }

    @Test
    public void bytesConstructor() throws KlvParseException {
        ${namespacedName} uut = new ${namespacedName}(
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0*arrayDimension1 as x>(byte) 0x00<#sep>,
                </#list>});
        assertEquals(uut.getValue(), new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getBytes(),
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0 as x>(byte) 0x00<#sep>,
                </#list>});
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0 as x>(byte) 0x00<#sep>,
                </#list>});
        assertEquals(uut.getValue(), new long[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getBytes(),
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0 as x>(byte) 0x00<#sep>,
                </#list>});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void fromBytesBad() throws KlvParseException {
        ${namespacedName}.fromBytes(
            new byte[] {
                (byte) 0x01, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0 as x>(byte) 0x00<#sep>,
                </#list>});
    }
<#if minValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minValueBad() {
        new ${namespacedName}(new long[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minValue - 1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if maxValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void maxValueBad() {
        new ${namespacedName}(new long[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${maxValue + 1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsLowRow() {
        new ${namespacedName}(new long[][]
            {<#list 2..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsRowHigh() {
        new ${namespacedName}(new long[][]
            {<#list 0..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(1)??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsLowColumns() {
        new ${namespacedName}(new long[][]
            {<#list 1..arrayDimension0 as r>{<#list 2..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsHighColumns() {
        new ${namespacedName}(new long[][]
            {<#list 1..arrayDimension0 as r>{<#list 0..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
}