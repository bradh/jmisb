package org.jmisb.api.klv.st1902;

import org.jmisb.api.klv.IKlvKey;

/**
 * Pseudo-key item for items in an array.
 *
 * <p>This is intended to support enumeration of array items. You should not normally be
 * instantiating this class yourself.
 */
public class ArrayItemKey implements IKlvKey, Comparable<ArrayItemKey> {
    private final int identifier;

    public ArrayItemKey(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.identifier;
        return hash;
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
        final ArrayItemKey other = (ArrayItemKey) obj;
        return this.identifier == other.identifier;
    }

    @Override
    public int compareTo(ArrayItemKey p) {
        return Integer.compare(identifier, p.identifier);
    }
}
