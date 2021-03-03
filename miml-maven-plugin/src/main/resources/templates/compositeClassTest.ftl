<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st190x.IMimdMetadataValue;
<#list entries as entry>
    <#if entry.name == "mimdId">
import org.jmisb.api.klv.st190x.MimdId;
    <#break>
    </#if>
</#list>
<#list entries as entry>
    <#if entry.ref>
import org.jmisb.api.klv.st190x.MimdIdReference;
    <#break>
    </#if>
</#list>
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name} */
public class ${name}Test extends LoggerChecks {

    public ${name}Test () {
        super(${name}.class);
    }

    @Test
    public void displayName() {
        ${name} uut = new ${name}(new TreeMap<>());
        assertEquals(uut.getDisplayName(), "${name}");
    }

    @Test
    public void displayableValue() {
        ${name} uut = new ${name}(new TreeMap<>());
        assertEquals(uut.getDisplayableValue(), "[${name}]");
    }

    @Test
    public void createValueUndefined() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.Undefined, new byte[]{0x00});
        verifySingleLoggerMessage("Unknown ${name} Metadata tag: Undefined");
        assertNull(uut);
    }

<#if topLevel>
    @Test
    public void parseFromBytesMimdId() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValue() throws KlvParseException {
        new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x05, 0x01, 0x01, (byte)0x81, (byte)0xc6, (byte)0x8f});
    }

    @Test
    public void parseFromBytesBadTag() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x09, 0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03, 0x59, 0x4d});
        verifySingleLoggerMessage("Unknown MIMD Metadata id: 127");
        assertEquals(uut.getBytes(), new byte[]{0x06, 0x0E, 0x2B, 0x34, 0x02, 0x05, 0x01, 0x01, 0x0E, 0x01, 0x05, 0x03,
                        0x00, 0x00, 0x00, 0x00, 0x06, 0x01, 0x02, 0x04, 0x03, (byte)0xbb, (byte)0xd4});
    }
<#else>
    @Test
    public void parseFromBytesEmpty() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{}, 0, 0);
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes().length, 0);
    }

    <#if (inherits??)>
        <#if !(extends??)>
    @Test
    public void parseFromBytesMimdId() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = new ${name}(new byte[]{0x01, 0x02, 0x04, 0x03}, 0, 4);
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

        </#if>
    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValue() throws KlvParseException {
        new ${name}(new byte[]{0x01, 0x01, (byte)0x81}, 0, 3);
    }

    @Test
    public void parseFromBytesBadTag() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = new ${name}(new byte[]{0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03}, 0, 7);
        verifySingleLoggerMessage("Unknown MIMD ${name} Metadata tag: 127");
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
    }
    </#if>
</#if>
<#list entries as entry>
    @Test
    public void createValue${entry.nameSentenceCase}() throws KlvParseException {
    <#if entry.array>
        // TODO: array
    <#elseif entry.typeName == "String">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{0x74});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), "t");
    <#elseif entry.typeName == "UInt">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), 255);
    <#elseif entry.typeName == "Integer">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte)0xFF});
        assertTrue(uut instanceof ${entry.namespacedQualifiedName});
        ${entry.namespacedQualifiedName} value = (${entry.namespacedQualifiedName})uut;
        assertEquals(value.getValue(), -1);
    <#elseif entry.typeName == "Real">
        <#if entry.minValue?? && entry.maxValue??>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), ${entry.minValue}, 0.00001);
        <#else>
        IMimdMetadataValue uut = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x40,
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x00});
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        assertEquals(value.getValue(), 2.000);
        </#if>
    <#elseif entry.ref>
        // Ref
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof MimdIdReference);
    <#elseif entry.list>
        // List
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        assertTrue(uut instanceof  ${entry.qualifiedListTypeName});
    <#elseif entry.name == "mimdId">
        // mimdId
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01});
        assertTrue(uut instanceof MimdId);
    <#elseif entry.typeName == "Boolean">
        // TODO - Boolean
    <#else>
        // Fallback
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x01, (byte)0x01, (byte)0x06});
        assertTrue(uut instanceof ${entry.qualifiedTypeName});
    </#if>
    }
</#list>
}
