package org.jmisb.api.klv.st0805;

import java.time.Clock;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import org.jmisb.api.klv.st0603.ST0603TimeStamp;

/** A Cursor-on-Target (CoT) message. */
public abstract class CotMessage {
    private static final String COT_VERSION = "2.0";
    private static final DateTimeFormatter DT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private Double pointLat;
    private Double pointLon;
    private Double pointHae;
    private double pointCe = 9_999_999.0;
    private double pointLe = 9_999_999.0;
    private String type;
    private String uid;
    private long time;
    private long start;
    private long stale;
    private String how;
    private final FlowTags flowTags = new FlowTags();

    /**
     * Constructor.
     *
     * @param clock reference clock for the message
     */
    public CotMessage(Clock clock) {
        flowTags.addFlowTag("ST0601CoT", clock.instant().atZone(ZoneOffset.UTC));
    }

    /**
     * Get the latitude.
     *
     * @return latitude, or null if not set
     */
    public Double getPointLat() {
        return pointLat;
    }

    /**
     * Set the latitude.
     *
     * @param pointLat The latitude
     */
    public void setPointLat(double pointLat) {
        this.pointLat = pointLat;
    }

    /**
     * Get the longitude.
     *
     * @return longitude, or null if not set
     */
    public Double getPointLon() {
        return pointLon;
    }

    /**
     * Set the longitude.
     *
     * @param pointLon longitude
     */
    public void setPointLon(double pointLon) {
        this.pointLon = pointLon;
    }

    /**
     * Get the altitude (height above ellipsoid).
     *
     * @return altitude in meters HAE, or null if not set
     */
    public Double getPointHae() {
        return pointHae;
    }

    /**
     * Set the altitude (height above ellipsoid).
     *
     * @param pointHae altitude in meters HAE
     */
    public void setPointHae(double pointHae) {
        this.pointHae = pointHae;
    }

    /**
     * Get the circular error.
     *
     * @return circular error
     */
    public double getPointCe() {
        return pointCe;
    }

    /**
     * Set the circular error.
     *
     * @param pointCe circular error
     */
    public void setPointCe(double pointCe) {
        this.pointCe = pointCe;
    }

    /**
     * Get the lateral error.
     *
     * @return lateral error
     */
    public double getPointLe() {
        return pointLe;
    }

    /**
     * Set the lateral error.
     *
     * @param pointLe lateral error
     */
    public void setPointLe(double pointLe) {
        this.pointLe = pointLe;
    }

    /**
     * Get the CoT type.
     *
     * @return CoT message type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the CoT type.
     *
     * @param type CoT message type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get the unique ID.
     *
     * @return the unique ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * Set the unique ID.
     *
     * @param uid The unique ID
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Get the time.
     *
     * @return time the message was generated
     */
    public long getTime() {
        return time;
    }

    /**
     * Set the time.
     *
     * @param time time the message was generated
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * Get the start time.
     *
     * @return time the message becomes valid
     */
    public long getStart() {
        return start;
    }

    /**
     * Set the start time.
     *
     * @param start time the message becomes valid
     */
    public void setStart(long start) {
        this.start = start;
    }

    /**
     * Get the stale time.
     *
     * @return time the message becomes invalid
     */
    public long getStale() {
        return stale;
    }

    /**
     * Set the stale time.
     *
     * @param stale time the message becomes invalid.
     */
    public void setStale(long stale) {
        this.stale = stale;
    }

    /**
     * Get the how string.
     *
     * @return how string
     */
    public String getHow() {
        return how;
    }

    /**
     * Set the how string.
     *
     * @param how string
     */
    public void setHow(String how) {
        this.how = how;
    }

    /**
     * Get the flow tags.
     *
     * @return flow tags
     */
    public FlowTags getFlowTags() {
        return flowTags;
    }

    /**
     * Write this message out as CoT XML.
     *
     * @return the message as an XML String.
     */
    public abstract String toXml();

    protected void closeEventStartInXML(StringBuilder sb) {
        sb.append(">");
    }

    protected void addEventLevelAttributesToXML(StringBuilder sb) {
        writeAttribute(sb, "version", COT_VERSION);
        writeAttribute(sb, "type", getType());
        writeAttribute(sb, "uid", getUid());
        writeAttribute(
                sb, "time", new ST0603TimeStamp(getTime()).getDateTime().format(DT_FORMATTER));
        // writeAttribute(sb, "start", DateTime8601.formatDate(this.startTime));
        // writeAttribute(sb, "stale", DateTime8601.formatDate(this.staleTime));
        writeAttribute(sb, "how", getHow());
    }

    protected void addEventEndToXML(StringBuilder sb) {
        sb.append("</event>");
    }

    protected void addEventStartToXML(StringBuilder sb) {
        sb.append("<event ");
    }

    protected void addPoint(StringBuilder sb) {
        sb.append("<point ");
        writeAttribute(sb, "lat", getPointLat());
        writeAttribute(sb, "lon", getPointLon());
        writeAttribute(sb, "hae", getPointHae());
        writeAttribute(sb, "ce", getPointCe());
        writeAttribute(sb, "le", getPointLe());
        sb.append("/>");
    }

    protected void addXmlHeader(StringBuilder sb) {
        sb.append("<?xml version='1.0' standalone='yes'?>");
    }

    protected void writeFlowTags(StringBuilder sb) {
        this.flowTags.writeAsXML(sb);
    }

    protected void writeAttribute(StringBuilder sb, String key, String value) {
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("' ");
    }

    protected void writeAttribute(StringBuilder sb, String key, double value) {
        sb.append(key);
        sb.append("='");
        sb.append(value);
        sb.append("' ");
    }
}
