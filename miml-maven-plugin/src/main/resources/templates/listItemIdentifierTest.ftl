// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for ${namespacedName}ItemKey. */
public class ${namespacedName}ItemKeyTest {

    @Test
    public void constructFromInteger() {
        ${namespacedName}ItemKey uut = new ${namespacedName}ItemKey(1);
        assertEquals(uut.getIdentifier(), 1);
        assertEquals(uut.toString(), "Item 1");
    }

    @Test
    public void testEquals() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertFalse(uut1.equals(null));
        assertTrue(uut1.equals(uut1));
        assertFalse(uut1.equals("1"));
        assertFalse(uut1.equals(uut2));
    }

    @Test
    public void testCompareTo() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertEquals(uut1.compareTo(uut1), 0);
        assertTrue(uut1.compareTo(uut2) < 0);
        assertTrue(uut2.compareTo(uut1) > 0);
    }

    @Test
    public void testHashCode() {
        ${namespacedName}ItemKey uut1 = new ${namespacedName}ItemKey(1);
        ${namespacedName}ItemKey uut2 = new ${namespacedName}ItemKey(2);
        assertNotEquals(uut1.hashCode(), uut2.hashCode());
    }
}
