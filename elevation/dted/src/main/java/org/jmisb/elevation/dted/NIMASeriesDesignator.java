package org.jmisb.elevation.dted;

public enum NIMASeriesDesignator {
    Unknown("", "Unknown"),
    DTED0("DTED0", "DTED Level 0"),
    DTED1("DTED1", "DTED Level 1"),
    DTED2("DTED2", "DTED Level 2");
    private final String encoding;
    private final String displayName;

    private NIMASeriesDesignator(String encoding, String displayName) {
        this.encoding = encoding;
        this.displayName = displayName;
    }

    /**
     * Create Series Designator from the encoded text value.
     *
     * <p>This is intended to support file parsing, and is not usually necessary for other purposes.
     *
     * @param textEquivalent the five character text equivalent for a security classification
     * @return the enumerated value corresponding to textEquivalent.
     */
    public static NIMASeriesDesignator getEnumValue(final String textEquivalent) {
        for (NIMASeriesDesignator v : values()) {
            if (v.encoding.equals(textEquivalent)) {
                return v;
            }
        }
        return Unknown;
    }

    /**
     * Get a human readable display name for this data series.
     *
     * @return string name (e.g. "DTED Level 1")
     */
    String getDisplayName() {
        return this.displayName;
    }
}
