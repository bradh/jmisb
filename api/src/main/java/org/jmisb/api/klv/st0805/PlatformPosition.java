package org.jmisb.api.klv.st0805;

import java.time.Clock;

/** Represents a Platform Position CoT message. */
public class PlatformPosition extends CotMessage {
    public Double sensorAzimuth;
    public Double sensorFov;
    public Double sensorVfov;
    public String sensorModel;
    public Double sensorRange;

    PlatformPosition(Clock clock) {
        super(clock);
    }

    /**
     * Get sensor azimuth.
     *
     * @return sensor azimuth in degrees, or null if not set
     */
    public Double getSensorAzimuth() {
        return sensorAzimuth;
    }

    /**
     * Set sensor azimuth.
     *
     * @param sensorAzimuth sensor azimuth in degrees
     */
    public void setSensorAzimuth(double sensorAzimuth) {
        this.sensorAzimuth = sensorAzimuth;
    }

    /**
     * Get sensor field of view.
     *
     * <p>This is the horizontal direction.
     *
     * @return field of view in degrees, or null if not set
     */
    public Double getSensorFov() {
        return sensorFov;
    }

    /**
     * Set sensor field of view.
     *
     * @param sensorFov field of view in degrees
     */
    public void setSensorFov(double sensorFov) {
        this.sensorFov = sensorFov;
    }

    /**
     * Get sensor vertical field of view.
     *
     * @return vertical field of view in degrees, or null if not set
     */
    public Double getSensorVfov() {
        return sensorVfov;
    }

    /**
     * Set sensor vertical field of view.
     *
     * @param sensorVfov vertical field of view in degrees
     */
    public void setSensorVfov(double sensorVfov) {
        this.sensorVfov = sensorVfov;
    }

    /**
     * Get sensor model (e.g., "EOW", "EON").
     *
     * @return currently active sensor
     */
    public String getSensorModel() {
        return sensorModel;
    }

    /**
     * Set sensor model.
     *
     * @param sensorModel currently active sensor
     */
    public void setSensorModel(String sensorModel) {
        this.sensorModel = sensorModel;
    }

    /**
     * Get sensor range.
     *
     * @return slant range in meters, or null if not set
     */
    public Double getSensorRange() {
        return sensorRange;
    }

    /**
     * Set sensor range.
     *
     * @param sensorRange slant range in meters
     */
    public void setSensorRange(double sensorRange) {
        this.sensorRange = sensorRange;
    }

    @Override
    public String toXml() {
        StringBuilder sb = new StringBuilder();
        addXmlHeader(sb);
        addEventStartToXML(sb);
        addEventLevelAttributesToXML(sb);
        closeEventStartInXML(sb);
        writeFlowTags(sb);
        writeSensor(sb);
        if (getPoint() != null) {
            getPoint().writeAsXML(sb);
        }
        addEventEndToXML(sb);
        return sb.toString();
    }

    private void writeSensor(StringBuilder sb) {
        sb.append("<sensor ");
        if (getSensorAzimuth() != null) {
            writeAttribute(sb, "azimuth", getSensorAzimuth());
        }
        if (getSensorFov() != null) {
            writeAttribute(sb, "fov", getSensorFov());
        }
        if (getSensorVfov() != null) {
            writeAttribute(sb, "vfov", getSensorVfov());
        }
        if (getSensorModel() != null) {
            writeAttribute(sb, "model", getSensorModel());
        }
        if (getSensorRange() != null) {
            writeAttribute(sb, "range", getSensorRange());
        }
        sb.append("/>");
    }
}
