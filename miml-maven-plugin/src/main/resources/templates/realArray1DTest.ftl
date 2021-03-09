<#setting number_format="computer">
<#assign
    minVal=minValue!0.0
    arrayDimension0=arrayDimensionSize(0)!1
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
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void getDisplayName() {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getDisplayableValue(), "[${nameSentenceCase} Array]");
    }

    @Test
    public void getValue() {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertEquals(uut.getValue(), new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }
<#if minValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void minValueBad() {
        new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minValue - 0.5}<#sep>, </#list>});
    }
</#if>
<#if maxValue??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void maxValueBad() {
        new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${maxValue + 0.5}<#sep>, </#list>});
    }
</#if>
<#if arrayDimensionSize(0)??>

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsLow() {
        new ${namespacedName}(new double[] {<#list 2..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void BadNumberOfElementsHigh() {
        new ${namespacedName}(new double[] {<#list 0..arrayDimension0 as x>${minVal}<#sep>, </#list>});
    }
</#if>
}
