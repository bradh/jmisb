package org.jmisb.api.klv.st1206;

/**
 * Radar Cross Section Scale Factor Polynomial (ST 1206 Item 22).
 *
 * <p>To determine the Radar Cross Section (RCS) for a pixel in a SARMI frame, the RCS scale factor
 * is defined as a quantity that relates pixel power for an ideal point scatterer to the RCS in
 * square meters.
 *
 * <p>See ST 1206.1 Section 6.2.19 for more information.
 */
public class RadarCrossSectionScaleFactorPolynomial implements ISARMIMetadataValue {

    private double[][] values;
    /**
     * Create from value.
     *
     * @param polynomial the RCS scale factor polynomial values
     */
    public RadarCrossSectionScaleFactorPolynomial(double[][] polynomial) {
        values = polynomial.clone();
    }

    /**
     * Create from encoded bytes.
     *
     * @param bytes the byte array to decode the value from.
     */
    public RadarCrossSectionScaleFactorPolynomial(byte[] bytes) {
        // TODO
    }

    @Override
    public byte[] getBytes() {
        // TODO
        return null;
    }

    @Override
    public final String getDisplayableValue() {
        return "[RCS Polynomial]";
    }

    @Override
    public final String getDisplayName() {
        return "Radar Cross Section Scale Factor Polynomia";
    }

    /**
     * Get the RCS polynomial.
     *
     * @return polynomial values in m^2, m, 1, 1/m, etc.
     */
    public double[][] getPolynomialValues() {
        return values.clone();
    }
}
