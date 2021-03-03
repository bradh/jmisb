// Generated file - changes will be lost on rebuild
// Template: ${.current_template_name}
package ${packageName};

import java.util.Objects;
import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key item for series identifier. */
public class List_${name}Identifier implements IKlvKey, Comparable<List_${name}Identifier> {

    private final Integer identifier;

    /**
     * Constructor.
     *
     * @param identifier the integer code for this ${name} identifier.
     */
    public List_${name}Identifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final List_${name}Identifier other = (List_${name}Identifier) obj;
        return Objects.equals(this.identifier, other.identifier);
    }

    @Override
    public int compareTo(List_${name}Identifier o) {
        return identifier.compareTo(o.identifier);
    }

    @Override
    public String toString() {
        return "List<${name}> item " + identifier;
    }
}
