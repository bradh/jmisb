package org.jmisb.elevation.dted;

/**
 * Security classification level.
 *
 * <p>MIL-PRF-89020B calls the encoded value "Security code".
 */
public enum SecurityClassification {
    Unknown("", "", "Unknown"),
    Secret("S  ", "S", "Secret"),
    Confidential("C  ", "C", "Confidential"),
    Unclassified("U  ", "U", "Unclassified"),
    Restricted("R  ", "R", "Restricted");
    private final String uhlEncoding;
    private final String dsiEncoding;
    private final String displayName;

    private SecurityClassification(String uhlEncoding, String dsiEncoding, String displayName) {
        this.uhlEncoding = uhlEncoding;
        this.dsiEncoding = dsiEncoding;
        this.displayName = displayName;
    }

    public String getUHLEncoding() {
        return uhlEncoding;
    }

    public String getDSIEncoding() {
        return dsiEncoding;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Create SecurityClassfication from the UHL encoded text value.
     *
     * <p>This is intended to support file parsing, and is not usually necessary for other purposes.
     *
     * @param textEquivalent the three character text equivalent for a security classification
     * @return the classification enumerated value corresponding to textEquivalent.
     */
    public static SecurityClassification getEnumValueFromUHLEncoding(final String textEquivalent) {
        for (SecurityClassification securityClassification : values()) {
            if (securityClassification.uhlEncoding.equals(textEquivalent)) {
                return securityClassification;
            }
        }
        return Unknown;
    }

    /**
     * Create SecurityClassfication from the DSI encoded text value.
     *
     * <p>This is intended to support file parsing, and is not usually necessary for other purposes.
     *
     * @param textEquivalent the one character text equivalent for a security classification
     * @return the classification enumerated value corresponding to textEquivalent.
     */
    public static SecurityClassification getEnumValueFromDSIEncoding(final String textEquivalent) {
        for (SecurityClassification securityClassification : values()) {
            if (securityClassification.dsiEncoding.equals(textEquivalent)) {
                return securityClassification;
            }
        }
        return Unknown;
    }
}
