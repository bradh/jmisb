/*
 * The MIT License
 *
 * Copyright 2021 West Ridge Systems.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.jmisb.api.klv.st0805;

/**
 * CoT Sensor element.
 *
 * <p>This represents the data in a Sensor detail element.
 */
public class CotSensor extends CotElement {
    public Double sensorAzimuth;
    public Double sensorFov;
    public Double sensorVfov;
    public String sensorModel;
    public Double sensorRange;

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
    void writeAsXML(StringBuilder sb) {
        sb.append("<sensor");
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
