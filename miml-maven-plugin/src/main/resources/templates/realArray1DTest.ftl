<#setting number_format="computer">
<#assign
    minVal=minValue!0.0
    arrayDimension0=arrayDimensionSize(0)!1
>
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Set;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
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

    @Test
    public void getBytes() {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        assertNotNull(uut.getBytes());
    }

    @Test
    public void fromBytes() throws KlvParseException {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[]
                { 0x01, ${arrayDimension0}, 0x04, 0x01, <#list 1..arrayDimension0 as i>0x00, 0x00, 0x00, 0x00<#sep>, </#list>});
    }


    @Test
    public void getNestedValues() {
        ${namespacedName} uut = new ${namespacedName}(new double[] {<#list 1..arrayDimension0 as x>${minVal}<#sep>, </#list>});
        Set<? extends IKlvKey> identifiers = uut.getIdentifiers();
        assertEquals(identifiers.size(), ${arrayDimension0});
        for (IKlvKey identifier: identifiers) {
            IKlvValue field = uut.getField(identifier);
            assertNotNull(field.getDisplayName());
            assertEquals(field.getDisplayableValue(), "0.000000");
        }
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
