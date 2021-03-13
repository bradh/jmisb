// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.LoggerChecks;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for LIST&lt;${name}&gt;. */
public class ListOf${name}Test extends LoggerChecks {

    public ListOf${name}Test () {
        super(ListOf${name}.class);
    }

    @Test
    public void displayName() {
        ListOf${name} uut = new ListOf${name}(new ArrayList<>(), "testDisplayName");
        assertEquals(uut.getDisplayName(), "testDisplayName");
    }

    @Test
    public void displayableValue() {
        ListOf${name} uut = new ListOf${name}(new ArrayList<>(), "testDisplayName");
        assertEquals(uut.getDisplayableValue(), "LIST[${name}]");
    }

    @Test
    public void fromBytesConstructorZeroLength() throws KlvParseException {
        ListOf${name} uut = new ListOf${name}(new byte[]{}, 0, 0, "testDisplayName");
        assertNotNull(uut);
        assertEquals(uut.getDisplayName(), "testDisplayName");
    }

    @Test
    public void fromBytesNoIdentifiers() throws KlvParseException {
        ListOf${name} uut = new ListOf${name}(new byte[]{}, 0, 0, "testDisplayName");
        assertNotNull(uut);
        assertEquals(uut.getIdentifiers().size(), 0);
    }

    @Test
    public void fromList${name}() throws KlvParseException {
        List<${name}> listOfValues = new ArrayList<>();
        listOfValues.add(${name}Test.makeValue());
        ListOf${name} uut = new ListOf${name}(listOfValues, "testDisplayName");
        assertNotNull(uut);
        byte[] bytes = uut.getBytes();
        assertTrue(bytes.length > 0);
        ListOf${name} uutRebuilt = ListOf${name}.fromBytes(bytes, "testDisplayName2");
        assertEquals(uut.getIdentifiers(), uutRebuilt.getIdentifiers());
        for (IKlvKey k: uut.getIdentifiers()) {
            assertNotNull(uut);
            assertTrue(uut.getField(k) instanceof IMimdMetadataValue);
        }
    }
}

