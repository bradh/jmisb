package org.jmisb.elevation.dted;

/**
 * DTED File User Header Label.
 *
 * <p>See MIL-PRF-89020B 3.12.c for the structure definition.
 */
public class UserHeaderLabel {
    private double longitudeOfOrigin;
    private double latitudeOfOrigin;
    private double longitudeDataInterval;
    private double latitudeDataInterval;
    private Integer absoluteVerticalAccuracy = null;
    private SecurityClassification securityClassification = SecurityClassification.Unknown;
    private String uniqueReference;
    private int numLongitudeLines;
    private int numLatitudePoints;
    private int multipleAccuracy;

    /**
     * Longitude of origin.
     *
     * <p>Lower left corner of data set
     *
     * @return longitude of origin in degrees.
     */
    public double getLongitudeOfOrigin() {
        return longitudeOfOrigin;
    }

    public void setLongitudeOfOrigin(double longitudeOfOrigin) {
        this.longitudeOfOrigin = longitudeOfOrigin;
    }

    /**
     * Latitude of origin.
     *
     * <p>Lower left corner of data set
     *
     * @return longitude of origin in degrees.
     */
    public double getLatitudeOfOrigin() {
        return latitudeOfOrigin;
    }

    public void setLatitudeOfOrigin(double latitudeOfOrigin) {
        this.latitudeOfOrigin = latitudeOfOrigin;
    }

    /**
     * Longitude data interval.
     *
     * @return longitude data interval in seconds;
     */
    public double getLongitudeDataInterval() {
        return longitudeDataInterval;
    }

    public void setLongitudeDataInterval(double longitudeDataInterval) {
        this.longitudeDataInterval = longitudeDataInterval;
    }

    /**
     * Latitude data interval.
     *
     * @return latitude data interval in seconds;
     */
    public double getLatitudeDataInterval() {
        return latitudeDataInterval;
    }

    public void setLatitudeDataInterval(double latitudeDataInterval) {
        this.latitudeDataInterval = latitudeDataInterval;
    }

    /**
     * Absolute Vertical Accuracy in Meters. With 90% assurance that the linear errors will not
     * exceed this value relative to mean sea level
     *
     * @return vertical accuracy in meters, or null if not available.
     */
    public Integer getAbsoluteVerticalAccuracy() {
        return absoluteVerticalAccuracy;
    }

    public void setAbsoluteVerticalAccuracy(Integer absoluteVerticalAccuracy) {
        this.absoluteVerticalAccuracy = absoluteVerticalAccuracy;
    }

    public SecurityClassification getSecurityClassification() {
        return securityClassification;
    }

    public void setSecurityClassification(SecurityClassification securityClassification) {
        this.securityClassification = securityClassification;
    }

    public String getUniqueReference() {
        return uniqueReference;
    }

    public void setUniqueReference(String uniqueReference) {
        this.uniqueReference = uniqueReference;
    }

    /**
     * Count of the number of longitude (profiles) lines for a full one-degree cell.
     *
     * <p>Count is based on the Level of DTED and the latitude zone of the cell.
     *
     * @return number of longitude lines
     */
    public int getNumLongitudeLines() {
        return numLongitudeLines;
    }

    public void setNumLongitudeLines(int numLongitudeLines) {
        this.numLongitudeLines = numLongitudeLines;
    }

    /**
     * Count of the number of latitude points per longitude line for a full one-degree cell.
     *
     * <p>e.g. 1201 for DTED1, 3601 for DTED2.
     *
     * @return number of latitude points per longitude line
     */
    public int getNumLatitudePoints() {
        return numLatitudePoints;
    }

    public void setNumLatitudePoints(int numLatitudePoints) {
        this.numLatitudePoints = numLatitudePoints;
    }

    public int getMultipleAccuracy() {
        return multipleAccuracy;
    }

    public void setMultipleAccuracy(int multipleAccuracy) {
        this.multipleAccuracy = multipleAccuracy;
    }
}
