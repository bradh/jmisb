// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${name}Identifier. */
public class ${name}IdentifierTest {

    @Test
    public void constructFromInteger() {
        ${name}Identifier uut = new ${name}Identifier(1);
        assertEquals(uut.getIdentifier(), 1);
        assertEquals(uut.toString(), "List<${name}> item 1");
    }

    @Test
    public void testEquals() {
        ${name}Identifier uut1 = new ${name}Identifier(1);
        ${name}Identifier uut2 = new ${name}Identifier(2);
        assertFalse(uut1.equals(null));
        assertTrue(uut1.equals(uut1));
        assertFalse(uut1.equals("1"));
        assertFalse(uut1.equals(uut2));
    }

    @Test
    public void testCompareTo() {
        ${name}Identifier uut1 = new ${name}Identifier(1);
        ${name}Identifier uut2 = new ${name}Identifier(2);
        assertEquals(uut1.compareTo(uut1), 0);
        assertTrue(uut1.compareTo(uut2) < 0);
        assertTrue(uut2.compareTo(uut1) > 0);
    }

    @Test
    public void testHashCode() {
        ${name}Identifier uut1 = new ${name}Identifier(1);
        ${name}Identifier uut2 = new ${name}Identifier(2);
        assertNotEquals(uut1.hashCode(), uut2.hashCode());
    }
}
