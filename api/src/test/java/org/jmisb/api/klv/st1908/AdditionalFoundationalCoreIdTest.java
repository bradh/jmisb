package org.jmisb.api.klv.st1908;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import org.jmisb.core.klv.UuidUtils;
import org.testng.annotations.Test;

/** Additional unit tests for FoundationalCoreId. */
public class AdditionalFoundationalCoreIdTest {

    public AdditionalFoundationalCoreIdTest() {}

    @Test
    public void fromValues() throws KlvParseException {
        SortedMap<FoundationalCoreIdMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        List<IdComponent> idComponents = new ArrayList<>();
        SortedMap<IdComponentMetadataKey, IMimdMetadataValue> sensorComponentValues =
                new TreeMap<>();
        UUID sensorUuid = UUID.fromString("9bebbfa3-c5b9-4a08-89e3-271356407963");
        long[] sensorUuidBytes = UuidUtils.uuidToLongArray(sensorUuid);
        sensorComponentValues.put(
                IdComponentMetadataKey.uuid, new IdComponent_Uuid(sensorUuidBytes));
        sensorComponentValues.put(IdComponentMetadataKey.idComponent, IdentifierComponent.Sensor);
        sensorComponentValues.put(IdComponentMetadataKey.idType, IdentifierType.Managed);
        IdComponent sensorComponent = new IdComponent(sensorComponentValues);
        idComponents.add(sensorComponent);
        SortedMap<IdComponentMetadataKey, IMimdMetadataValue> platformComponentValues =
                new TreeMap<>();
        UUID platformUuid = UUID.fromString("8533fd35-4590-4533-8b20-daa040917b9a");
        platformComponentValues.put(
                IdComponentMetadataKey.uuid,
                new IdComponent_Uuid(UuidUtils.uuidToLongArray(platformUuid)));
        platformComponentValues.put(
                IdComponentMetadataKey.idComponent, IdentifierComponent.Platform);
        platformComponentValues.put(IdComponentMetadataKey.idType, IdentifierType.Virtual);
        IdComponent platformComponent = new IdComponent(platformComponentValues);
        idComponents.add(platformComponent);
        values.put(
                FoundationalCoreIdMetadataKey.idComponents,
                new ListOfIdComponent(idComponents, "Test Name"));
        FoundationalCoreId uut = new FoundationalCoreId(values);
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 1);
        IKlvKey identifier = (IKlvKey) uut.getIdentifiers().toArray()[0];
        assertEquals(identifier, FoundationalCoreIdMetadataKey.idComponents);
        assertEquals(uut.getField(identifier).getDisplayableValue(), "LIST[IdComponent]");
        byte[] encodedBytes = uut.getBytes();
        assertEquals(
                encodedBytes,
                new byte[] {
                    (byte) 0x21,
                    (byte) 0x3a,
                    (byte) 0x1c,
                    (byte) 0x21,
                    (byte) 0x14,
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x9b,
                    (byte) 0xeb,
                    (byte) 0xbf,
                    (byte) 0xa3,
                    (byte) 0xc5,
                    (byte) 0xb9,
                    (byte) 0x4a,
                    (byte) 0x08,
                    (byte) 0x89,
                    (byte) 0xe3,
                    (byte) 0x27,
                    (byte) 0x13,
                    (byte) 0x56,
                    (byte) 0x40,
                    (byte) 0x79,
                    (byte) 0x63,
                    (byte) 0x22,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x23,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x1c,
                    (byte) 0x21,
                    (byte) 0x14,
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x85,
                    (byte) 0x33,
                    (byte) 0xfd,
                    (byte) 0x35,
                    (byte) 0x45,
                    (byte) 0x90,
                    (byte) 0x45,
                    (byte) 0x33,
                    (byte) 0x8b,
                    (byte) 0x20,
                    (byte) 0xda,
                    (byte) 0xa0,
                    (byte) 0x40,
                    (byte) 0x91,
                    (byte) 0x7b,
                    (byte) 0x9a,
                    (byte) 0x22,
                    (byte) 0x01,
                    (byte) 0x02,
                    (byte) 0x23,
                    (byte) 0x01,
                    (byte) 0x00
                });
        FoundationalCoreId rebuilt = new FoundationalCoreId(encodedBytes, 0, encodedBytes.length);
        assertNotNull(rebuilt);
        assertEquals(rebuilt.getIdentifiers().size(), 1);
        IKlvKey rebuiltIdentifier = (IKlvKey) uut.getIdentifiers().toArray()[0];
        assertEquals(rebuiltIdentifier, FoundationalCoreIdMetadataKey.idComponents);
        assertEquals(uut.getField(rebuiltIdentifier).getDisplayableValue(), "LIST[IdComponent]");
    }

    @Test(expectedExceptions = KlvParseException.class)
    public void checkFromBytesConstructorBad() throws KlvParseException {
        new FoundationalCoreId(
                new byte[] {0x21, 0x09, 0x01, 0x07, 0x22, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00},
                0,
                11);
    }
}
