// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import static org.testng.Assert.*;

import java.util.Collections;
import org.testng.annotations.Test;

/** Unit tests for ${namespacedName} */
public class ${namespacedName}Test {

    @Test
    public void displayName() {
        ${namespacedName} uut = new ${namespacedName}("x");
        assertEquals(uut.getDisplayName(), "${nameSentenceCase}");
    }

    @Test
    public void displayableValue() {
        ${namespacedName} uut = new ${namespacedName}("x");
        assertEquals(uut.getDisplayableValue(), "x");
    }

    @Test
    public void value() {
        ${namespacedName} uut = new ${namespacedName}("x");
        assertEquals(uut.getValue(), "x");
    }

    @Test
    public void fromBytesConstructor() {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x48, 0x45, 0x6c, 0x6c, 0x6f});
        assertEquals(uut.getDisplayableValue(), "HEllo");
    }

    @Test
    public void fromBytes() {
        ${namespacedName} uut = ${namespacedName}.fromBytes(new byte[] {0x48, 0x45, 0x6c, 0x6c, 0x6f});
        assertEquals(uut.getDisplayableValue(), "HEllo");
    }

    @Test
    public void fromBytesConstructorOffset() {
        ${namespacedName} uut = new ${namespacedName}(new byte[] {0x01, 0x02, 0x48, 0x45, 0x6c, 0x6c, 0x6f}, 2, 5);
        assertEquals(uut.getDisplayableValue(), "HEllo");
    }

    @Test
    public void fromValueMaxLength() {
        String s = String.join("", Collections.nCopies(${maxLength}, String.valueOf('z')));
        ${namespacedName} uut = new ${namespacedName}(s);
        assertEquals(uut.getDisplayableValue().length(), ${maxLength});
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void fromValueTooLong() {
        String s = String.join("", Collections.nCopies(${maxLength + 1}, String.valueOf('z')));
        new ${namespacedName}(s);
    }

    @Test
    public void getBytes1() {
        ${namespacedName} uut = new ${namespacedName}("x");
        assertEquals(uut.getBytes(), new byte[]{0x78});
    }

    @Test
    public void getBytes5() {
        ${namespacedName} uut = new ${namespacedName}("HEllo");
        assertEquals(uut.getBytes(), new byte[]{0x48, 0x45, 0x6c, 0x6c, 0x6f});
    }
}
