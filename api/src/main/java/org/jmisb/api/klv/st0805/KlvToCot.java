package org.jmisb.api.klv.st0805;

import java.time.Clock;
import org.jmisb.api.klv.st0601.*;

/** Perform KLV to CoT conversion as defined by ST 0805. */
public class KlvToCot {
    private static String platformType = "a-f-A";
    private static final long SECONDS_TO_MICROSECONDS = 1_000_000;
    private static final long STALE_PERIOD = 5 * SECONDS_TO_MICROSECONDS;
    private static final String PARENT_PRODUCER_RELATIONSHIP = "p-p";

    private KlvToCot() {}

    /**
     * Convert a MISB UAS Datalink message to a CoT Sensor Point of Interest (SPI) message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @return The CoT message
     */
    public static SensorPointOfInterest getSensorPointOfInterest(UasDatalinkMessage uasMessage) {
        SensorPointOfInterest spiMessage = new SensorPointOfInterest(Clock.systemUTC());

        // The standard is unclear, but seems to indicate target position is preferred over frame
        // center
        TargetLocationLatitude targetLat =
                (TargetLocationLatitude) uasMessage.getField(UasDatalinkTag.TargetLocationLatitude);
        TargetLocationLongitude targetLon =
                (TargetLocationLongitude)
                        uasMessage.getField(UasDatalinkTag.TargetLocationLongitude);
        TargetLocationElevation targetAlt =
                (TargetLocationElevation)
                        uasMessage.getField(UasDatalinkTag.TargetLocationElevation);

        if (targetLat != null && targetLon != null && targetAlt != null) {
            spiMessage.setPointLat(targetLat.getDegrees());
            spiMessage.setPointLon(targetLon.getDegrees());
            // TODO: convert MSL -> HAE
            spiMessage.setPointHae(targetAlt.getMeters());
        } else {
            // Try frame center
            FrameCenterLatitude frameCenterLat =
                    (FrameCenterLatitude) uasMessage.getField(UasDatalinkTag.FrameCenterLatitude);
            FrameCenterLongitude frameCenterLon =
                    (FrameCenterLongitude) uasMessage.getField(UasDatalinkTag.FrameCenterLongitude);
            FrameCenterElevation frameCenterAlt =
                    (FrameCenterElevation) uasMessage.getField(UasDatalinkTag.FrameCenterElevation);

            if (frameCenterLat != null && frameCenterLon != null && frameCenterAlt != null) {
                spiMessage.setPointLat(frameCenterLat.getDegrees());
                spiMessage.setPointLon(frameCenterLon.getDegrees());
                // TODO: convert MSL -> HAE
                spiMessage.setPointHae(frameCenterAlt.getMeters());
            }
        }

        TargetErrorEstimateCe90 targetErrorCe =
                (TargetErrorEstimateCe90) uasMessage.getField(UasDatalinkTag.TargetErrorCe90);
        if (targetErrorCe != null) { // Conversion from 2.146-sigma to 1-sigma necessary
            spiMessage.setPointCe(targetErrorCe.getMetres() / 2.146);
        } else {
            spiMessage.setPointCe(9_999_999);
        }

        TargetErrorEstimateLe90 targetErrorLe =
                (TargetErrorEstimateLe90) uasMessage.getField(UasDatalinkTag.TargetErrorLe90);
        if (targetErrorLe != null) { // Conversion from 1.645-sigma to 1-sigma necessary
            spiMessage.setPointLe(targetErrorLe.getMetres() / 1.645);
        } else {
            spiMessage.setPointLe(9_999_999);
        }

        spiMessage.setType("b-m-p-s-p-i");

        // TODO: allow client to specify UID & handle missing tags from KLV
        UasDatalinkString platformDesignation =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.PlatformDesignation);
        UasDatalinkString missionId =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.MissionId);
        UasDatalinkString imageSourceSensor =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        if (platformDesignation != null && missionId != null && imageSourceSensor != null) {
            spiMessage.setUid(
                    platformDesignation.getValue()
                            + "_"
                            + missionId.getValue()
                            + "_"
                            + imageSourceSensor.getValue());
        }

        setTimes(uasMessage, spiMessage);

        spiMessage.setHow("m-p");

        // TODO: allow client to specify platform type
        spiMessage.setLinkType(platformType);

        // TODO: allow client to specify platform UID
        spiMessage.setLinkUid(getPlatformUid(uasMessage));

        spiMessage.setLinkRelation(PARENT_PRODUCER_RELATIONSHIP);

        return spiMessage;
    }

    /**
     * Convert a UAS Datalink message to a CoT Platform Position message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @return The CoT message
     */
    public static PlatformPosition getPlatformPosition(UasDatalinkMessage uasMessage) {
        return getPlatformPosition(uasMessage, Clock.systemUTC());
    }

    /**
     * Convert a UAS Datalink message to a CoT Platform Position message.
     *
     * @param uasMessage The UAS Datalink message to convert
     * @param clock the clock to use when creating the CoT message
     * @return The CoT message
     */
    public static PlatformPosition getPlatformPosition(UasDatalinkMessage uasMessage, Clock clock) {
        PlatformPosition platformMessage = new PlatformPosition(clock);

        SensorLatitude pointLat =
                (SensorLatitude) uasMessage.getField(UasDatalinkTag.SensorLatitude);
        SensorLongitude pointLon =
                (SensorLongitude) uasMessage.getField(UasDatalinkTag.SensorLongitude);
        if ((pointLat != null) && (pointLon != null)) {
            SensorTrueAltitude sensorTrueAltitude =
                    (SensorTrueAltitude) uasMessage.getField(UasDatalinkTag.SensorTrueAltitude);
            SensorEllipsoidHeight sensorEllipsoidHeight =
                    (SensorEllipsoidHeight)
                            uasMessage.getField(UasDatalinkTag.SensorEllipsoidHeight);
            SensorEllipsoidHeightExtended sensorEllipsoidHeightExtended =
                    (SensorEllipsoidHeightExtended)
                            uasMessage.getField(UasDatalinkTag.SensorEllipsoidHeightExtended);
            if ((sensorEllipsoidHeight != null)
                    || (sensorEllipsoidHeightExtended != null)
                    || (sensorTrueAltitude != null)) {
                platformMessage.setPointLat(pointLat.getDegrees());
                platformMessage.setPointLon(pointLon.getDegrees());
                if (sensorEllipsoidHeightExtended != null) {
                    platformMessage.setPointHae(sensorEllipsoidHeightExtended.getMeters());
                } else if (sensorEllipsoidHeight != null) {
                    platformMessage.setPointHae(sensorEllipsoidHeight.getMeters());
                } else {
                    // TODO: convert MSL -> HAE
                    platformMessage.setPointHae(sensorTrueAltitude.getMeters());
                }
                // Represents "no value given" - ST 0601 does not contain platform uncertainty
                platformMessage.setPointCe(9_999_999);
                platformMessage.setPointLe(9_999_999);
            }
        }

        // TODO: allow client to specify platform type
        platformMessage.setType(platformType);

        // TODO: allow client to specify UID & handle missing tags from KLV
        platformMessage.setUid(getPlatformUid(uasMessage));

        setTimes(uasMessage, platformMessage);

        platformMessage.setHow("m-p");

        // Sensor absolute azimuth obtained by adding platform heading and sensor relative azimuth
        PlatformHeadingAngle platformHeading =
                (PlatformHeadingAngle) uasMessage.getField(UasDatalinkTag.PlatformHeadingAngle);
        SensorRelativeAzimuth sensorRelativeAzimuth =
                (SensorRelativeAzimuth)
                        uasMessage.getField(UasDatalinkTag.SensorRelativeAzimuthAngle);
        if (platformHeading != null && sensorRelativeAzimuth != null) {
            platformMessage.setSensorAzimuth(
                    (platformHeading.getDegrees() + sensorRelativeAzimuth.getDegrees()) % 360);
        }

        HorizontalFov fov = (HorizontalFov) uasMessage.getField(UasDatalinkTag.SensorHorizontalFov);
        if (fov != null) {
            platformMessage.setSensorFov(fov.getDegrees());
        }

        VerticalFov vfov = (VerticalFov) uasMessage.getField(UasDatalinkTag.SensorVerticalFov);
        if (vfov != null) {
            platformMessage.setSensorVfov(vfov.getDegrees());
        }

        UasDatalinkString sensor =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.ImageSourceSensor);
        if (sensor != null) {
            platformMessage.setSensorModel(sensor.getValue());
        }

        SlantRange slantRange = (SlantRange) uasMessage.getField(UasDatalinkTag.SlantRange);
        if (slantRange != null) {
            platformMessage.setSensorRange(slantRange.getMeters());
        }

        return platformMessage;
    }

    private static String getPlatformUid(UasDatalinkMessage uasMessage) {
        // TODO: allow client to specify UID & handle missing tags from KLV
        UasDatalinkString platformDesignation =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.PlatformDesignation);
        UasDatalinkString missionId =
                (UasDatalinkString) uasMessage.getField(UasDatalinkTag.MissionId);
        if (platformDesignation != null && missionId != null) {
            return platformDesignation.getValue() + "_" + missionId.getValue();
        } else {
            return "jmisb";
        }
    }

    private static void setTimes(UasDatalinkMessage uasMessage, CotMessage cotMessage) {
        PrecisionTimeStamp unixTimeStamp =
                (PrecisionTimeStamp) uasMessage.getField(UasDatalinkTag.PrecisionTimeStamp);
        if (unixTimeStamp != null) {
            cotMessage.setTime(unixTimeStamp.getMicroseconds());
            cotMessage.setStart(unixTimeStamp.getMicroseconds());

            // TODO: allow client to specify stale time
            cotMessage.setStale(unixTimeStamp.getMicroseconds() + STALE_PERIOD);
        }
    }
}
