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
                    (byte) 0x47,
                    (byte) 0x25,
                    (byte) 0x21,
                    (byte) 0x1d,
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x08,
                    (byte) 0x81,
                    (byte) 0x13,
                    (byte) 0x81,
                    (byte) 0x63,
                    (byte) 0x81,
                    (byte) 0x37,
                    (byte) 0x81,
                    (byte) 0x1b,
                    (byte) 0x81,
                    (byte) 0x3d,
                    (byte) 0x81,
                    (byte) 0x31,
                    (byte) 0x42,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0x01,
                    (byte) 0x81,
                    (byte) 0x5b,
                    (byte) 0x1f,
                    (byte) 0x0b,
                    (byte) 0x4e,
                    (byte) 0x38,
                    (byte) 0x71,
                    (byte) 0x5b,
                    (byte) 0x22,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x23,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x20,
                    (byte) 0x21,
                    (byte) 0x18,
                    (byte) 0x01,
                    (byte) 0x10,
                    (byte) 0x01,
                    (byte) 0x04,
                    (byte) 0x20,
                    (byte) 0x65,
                    (byte) 0x13,
                    (byte) 0x81,
                    (byte) 0x5d,
                    (byte) 0x15,
                    (byte) 0x25,
                    (byte) 0x70,
                    (byte) 0x25,
                    (byte) 0x13,
                    (byte) 0x6b,
                    (byte) 0x00,
                    (byte) 0x81,
                    (byte) 0x3a,
                    (byte) 0x81,
                    (byte) 0x00,
                    (byte) 0x20,
                    (byte) 0x71,
                    (byte) 0x5b,
                    (byte) 0x7a,
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
}
