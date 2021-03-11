<#setting number_format="computer">
<#assign
    minVal=minValue!0.0
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
            new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        assertEquals(uut.getValue(), new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test
    public void getBytes() {
        ${namespacedName} uut = new ${namespacedName}(new double[][]
                {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
        // TODO: check values - may be messy given IMAPA.
        assertNotNull(uut.getBytes());
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x02, ${arrayDimension0}, ${arrayDimension1}, 0x04, 0x01, <#list 1..arrayDimension0*arrayDimension1 as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }
<#if arrayDimensionSize(0)??>
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromBytesBadDim0() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x02, ${arrayDimension0 + 1}, ${arrayDimension1}, 0x04, 0x01, <#list 1..((arrayDimension0+1)*arrayDimension1) as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(1)??>
    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromBytesBadDim1() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x02, ${arrayDimension0}, ${arrayDimension1+1}, 0x04, 0x01, <#list 1..(arrayDimension0*(arrayDimension1+1)) as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }
</#if>
<#if minValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minValueBad() {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minValue - 0.1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if maxValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void maxValueBad() {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${maxValue + 0.1}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsLowRow() {
        new ${namespacedName}(new double[][]
            {<#list 2..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsRowHigh() {
        new ${namespacedName}(new double[][]
            {<#list 0..arrayDimension0 as r>{<#list 1..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(1)??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsLowColumns() {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 2..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsHighColumns() {
        new ${namespacedName}(new double[][]
            {<#list 1..arrayDimension0 as r>{<#list 0..arrayDimension1 as c>${minVal}<#sep>, </#list>}<#sep>, </#list>});
    }
</#if>

}
