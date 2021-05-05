package org.jmisb.elevation.dted;

/**
 * Accuracy Description.
 *
 * <p>See MIL-PRF-89020B 3.12.e for the structure definition.
 */
public class AccuracyDescription {
    private Integer absoluteHorizontalAccuracy;
    private Integer absoluteVerticalAccuracy;
    private Integer relativeHorizontalAccuracy;
    private Integer relativeVerticalAccuracy;
    private String nimaUseSection1;

    public Integer getAbsoluteHorizontalAccuracy() {
        return absoluteHorizontalAccuracy;
    }

    public void setAbsoluteHorizontalAccuracy(Integer absoluteHorizontalAccuracy) {
        this.absoluteHorizontalAccuracy = absoluteHorizontalAccuracy;
    }

    public Integer getAbsoluteVerticalAccuracy() {
        return absoluteVerticalAccuracy;
    }

    public void setAbsoluteVerticalAccuracy(Integer absoluteVerticalAccuracy) {
        this.absoluteVerticalAccuracy = absoluteVerticalAccuracy;
    }

    public Integer getRelativeHorizontalAccuracy() {
        return relativeHorizontalAccuracy;
    }

    public void setRelativeHorizontalAccuracy(Integer relativeHorizontalAccuracy) {
        this.relativeHorizontalAccuracy = relativeHorizontalAccuracy;
    }

    public Integer getRelativeVerticalAccuracy() {
        return relativeVerticalAccuracy;
    }

    public void setRelativeVerticalAccuracy(Integer relativeVerticalAccuracy) {
        this.relativeVerticalAccuracy = relativeVerticalAccuracy;
    }

    public String getNimaUseSection1() {
        return nimaUseSection1;
    }

    public void setNimaUseSection1(String nimaUseSection1) {
        this.nimaUseSection1 = nimaUseSection1;
    }
}
