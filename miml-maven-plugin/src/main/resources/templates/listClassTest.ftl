// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.ArrayList;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.LoggerChecks;
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
}

