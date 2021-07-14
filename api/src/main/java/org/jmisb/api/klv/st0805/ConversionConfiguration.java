package org.jmisb.api.klv.st0805;

/**
 * Configuration options for KLV to CoT conversion.
 *
 * <p>This is intended to be set up, and be enduring over multiple CoT conversions.
 */
public class ConversionConfiguration {

    private static final long SECONDS_TO_MICROSECONDS = 1_000_000;

    /**
     * Platform type.
     *
     * <p>Defaults to atom-friendly-Air AOB(a-f-A).
     */
    private String platformType = "a-f-A";

    /**
     * Stale time in microseconds.
     *
     * <p>defaults to 5 seconds.
     */
    private long stalePeriod = 5 * SECONDS_TO_MICROSECONDS;

    /**
     * Get the CoT platform type.
     *
     * @return the platform type as a valid CoT platform type.
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * Set the CoT platform type.
     *
     * <p>Examples are "a-f-A-M-F" for atom-friendly-Air AOB-Military-Fixed Wing, or "a-f-A-M-F-Q"
     * for a UAV variant of that. Substitute "H" for "F" to represent rotary wing. See the CoT
     * "event.xml" list for options, of which there are many.
     *
     * @param platformType the platform type as String.
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    /**
     * Get the stale time.
     *
     * <p>This is the time after the valid time at which the message is no longer valid.
     *
     * @return stale time in microseconds
     */
    public long getStalePeriod() {
        return stalePeriod;
    }

    /**
     * Set the stale time.
     *
     * <p>This is the time after the valid time at which the message is no longer valid.
     *
     * @param stalePeriod the stale period in microseconds.
     */
    public void setStalePeriod(long stalePeriod) {
        this.stalePeriod = stalePeriod;
    }
}
