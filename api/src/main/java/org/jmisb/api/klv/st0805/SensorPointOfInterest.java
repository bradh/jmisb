package org.jmisb.api.klv.st0805;

import java.time.Clock;

/**
 * Represents a Sensor Point of Interest (SPI) CoT message.
 *
 * <p>Every SPI message should be linked to a "parent" {@link PlatformPosition} UID/type identifying
 * the platform producing the sensor feed.
 */
public class SensorPointOfInterest extends CotMessage {
    public String linkType;
    public String linkUid;
    public String linkRelation;

    SensorPointOfInterest(Clock clock) {
        super(clock);
        setType("b-m-p-s-p-i");
    }

    /**
     * Get the link type (CoT type of the producing {@link PlatformPosition}).
     *
     * @return link type
     */
    public String getLinkType() {
        return linkType;
    }

    /**
     * Set the link type (CoT type of the producing {@link PlatformPosition}).
     *
     * @param linkType link type
     */
    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    /**
     * Get the link UID (unique ID of the producing {@link PlatformPosition}).
     *
     * @return link UID
     */
    public String getLinkUid() {
        return linkUid;
    }

    /**
     * Set the link UID (unique ID of the producing {@link PlatformPosition}).
     *
     * @param linkUid link UID
     */
    public void setLinkUid(String linkUid) {
        this.linkUid = linkUid;
    }

    /**
     * Get the link relation.
     *
     * <p>This is the relationship from this message to the link target, typically {@code "p-p"} for
     * parent producer.
     *
     * @return the link relation
     */
    public String getLinkRelation() {
        return linkRelation;
    }

    /**
     * Set the link relation.
     *
     * <p>This is the relationship from this message to the link target, typically {@code "p-p"} for
     * parent producer.
     *
     * @param relation the link relation
     */
    public void setLinkRelation(String relation) {
        this.linkRelation = relation;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        addXmlHeader(sb);
        addEventLevelAttributesToXML(sb);
        closeEventStartInXML(sb);
        if (getPoint() != null) {
            getPoint().writeAsXML(sb);
        }
        writeFlowTags(sb);
        // TODO: add detail link
        addEventEndToXML(sb);
        return sb.toString();
    }
}
