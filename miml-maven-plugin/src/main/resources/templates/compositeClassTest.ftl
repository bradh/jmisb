<#setting number_format="computer">
// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
<#list entries as entry>
    <#if entry.name == "mimdId">
import org.jmisb.api.klv.st1902.MimdId;
    <#break>
    </#if>
</#list>
import org.jmisb.api.klv.st1902.MimdIdReference;
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

    <#if includes??>
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

    public void parseFromBytesMimdIdNoOffset() throws KlvParseException {
        verifyNoLoggerMessages();
        ${name} uut = ${name}.fromBytes(new byte[]{0x01, 0x02, 0x04, 0x03});
        verifyNoLoggerMessages();
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
        assertNotNull(uut.getIdentifiers());
        assertEquals(uut.getIdentifiers().size(), 1);
        assertNotNull(uut.getField(${name}MetadataKey.mimdId));
    }

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
    @Test
    public void parseFromBytesBadTagNoOffset() throws KlvParseException {
        verifyNoLoggerMessages();
        IMimdMetadataValue uut = ${name}.fromBytes(new byte[]{0x7F, 0x01, 0x00, 0x01, 0x02, 0x04, 0x03});
        verifySingleLoggerMessage("Unknown MIMD ${name} Metadata tag: 127");
        assertEquals(uut.getBytes(), new byte[]{0x01, 0x02, 0x04, 0x03});
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void parseFromBytesBadValueNoOffset() throws KlvParseException {
        ${name}.fromBytes(new byte[]{0x01, 0x01, (byte)0x81});
    }
    </#if>
</#if>
<#list entries as entry>
    @Test
    public void createValue${entry.nameSentenceCase}() throws KlvParseException {
    <#if entry.array>
        <#assign
            minVal=entry.minValue!0
            arrayDimension0=entry.arrayDimensionSize(0)!1
        >
        <#if entry.typeName == "UInt">
        IMimdMetadataValue uut = ${name}.createValue(${name}MetadataKey.${entry.name},
            <#if entry.array1D>
            new byte[] {
                (byte) 0x01, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..arrayDimension0 as x>(byte) 0x00<#sep>,
                </#list>});
            <#elseif entry.array2D>
            <#assign arrayDimension1=entry.arrayDimensionSize(1)!1 >
            new byte[] {
                (byte) 0x02, // num Dimensions
                (byte) ${arrayDimension0},
                (byte) ${arrayDimension1},
                (byte) 0x01, // Ebytes
                (byte) 0x04, // APA
                (byte) ${minVal}, // APAS aka bias value
                <#list 1..(arrayDimension0*arrayDimension1) as x>(byte) 0x00<#sep>,
                </#list>});
            </#if>
        assertTrue(uut instanceof ${entry.namespacedName});
        ${entry.namespacedName} value = (${entry.namespacedName})uut;
        <#else>
        // TODO: other array types
        </#if>
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
    @Test
    public void testBuildRef() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if entry.ref>
        // Ref
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(${name}MetadataKey.${entry.name}, new byte[]{(byte) 0x00});
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
                assertTrue(uut.getField(id) instanceof MimdIdReference);
            });
            byte[] bytes = uut.getBytes();
            assertTrue(bytes.length >= 3);
<#if topLevel>
            ${name} parseResult = new ${name}(bytes);
<#else>
            ${name} parseResult = ${name}.fromBytes(bytes);
</#if>
            assertEquals(parseResult.getIdentifiers().size(), values.keySet().size());
        }
    }

    @Test
    public void testBuildReal() throws KlvParseException {
        SortedMap<${name}MetadataKey, IMimdMetadataValue> values = new TreeMap<>();
<#list entries as entry>
    <#if (entry.typeName == "Real") && (!entry.array)>
        IMimdMetadataValue refValue${entry?index} = ${name}.createValue(
            ${name}MetadataKey.${entry.name},
            new byte[]{
                (byte) 0x00,
                (byte) 0x00,
                (byte) 0x01,
                (byte) 0x00});
        values.put(${name}MetadataKey.${entry.name}, refValue${entry?index});
    </#if>
</#list>
        ${name} uut = new ${name}(values);
        assertEquals(uut.getIdentifiers().size(), values.keySet().size());
        if (uut.getIdentifiers().size() > 0) {
            uut.getIdentifiers().stream().forEach(id -> {
                assertNotNull(uut.getField(id));
            });
            byte[] bytes = uut.getBytes();
            assertTrue(bytes.length >= 3);
<#if topLevel>
            ${name} parseResult = new ${name}(bytes);
<#else>
            ${name} parseResult = ${name}.fromBytes(bytes);
</#if>
            assertEquals(parseResult.getIdentifiers().size(), values.keySet().size());
        }
    }    
}
