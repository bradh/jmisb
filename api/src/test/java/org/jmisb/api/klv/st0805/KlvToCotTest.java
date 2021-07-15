package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.st0601.FrameCenterElevation;
import org.jmisb.api.klv.st0601.FrameCenterLatitude;
import org.jmisb.api.klv.st0601.FrameCenterLongitude;
import org.jmisb.api.klv.st0601.HorizontalFov;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PlatformHeadingAngle;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeight;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeightExtended;
import org.jmisb.api.klv.st0601.SensorLatitude;
import org.jmisb.api.klv.st0601.SensorLongitude;
import org.jmisb.api.klv.st0601.SensorRelativeAzimuth;
import org.jmisb.api.klv.st0601.SlantRange;
import org.jmisb.api.klv.st0601.TargetErrorEstimateCe90;
import org.jmisb.api.klv.st0601.TargetErrorEstimateLe90;
import org.jmisb.api.klv.st0601.TargetLocationElevation;
import org.jmisb.api.klv.st0601.TargetLocationLatitude;
import org.jmisb.api.klv.st0601.TargetLocationLongitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st0601.VerticalFov;
import org.testng.annotations.Test;

/** Unit tests for KlvToCot. */
public class KlvToCotTest {

    private static final String PLATFORM_POSITION_XML =
            "<?xml version='1.0' standalone='yes'?><event version='2.0' type='a-f-A' uid='TESTPLAT1_Mission2' time='2021-07-04T09:00:03.000Z' start='2021-07-04T09:00:03.000Z' stale='2021-07-04T09:00:08.000Z' how='m-p' ><detail><_flow-tags_ ST0601CoT='2021-07-13T10:22:26.935488Z' /></detail><sensor azimuth='135.8' fov='13.3' vfov='23.2' model='SenSOR3' range='1400.3' /><point lat='-32.42' lon='143.24' hae='1201.0' ce='9999999.0' le='9999999.0' /></event>";

    public KlvToCotTest() {}

    @Test
    public void checkSPI() {
        UasDatalinkMessage sourceMessage = buildSourceMessage();
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        SensorPointOfInterest sensorPointOfInterest =
                converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(sensorPointOfInterest.getType(), "b-m-p-s-p-i");
        assertEquals(sensorPointOfInterest.getHow(), "m-p");
        assertEquals(sensorPointOfInterest.getUid(), "TESTPLAT1_Mission2_SenSOR3");
        assertEquals(sensorPointOfInterest.getLinkType(), "a-f-A");
        assertEquals(sensorPointOfInterest.getLinkUid(), "TESTPLAT1_Mission2");
        assertEquals(sensorPointOfInterest.getLinkRelation(), "p-p");
        assertEquals(sensorPointOfInterest.getPoint().getLat(), -32.4, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getLon(), 143.23, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getHae(), 143, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getCe(), 46.6, 0.1);
        assertEquals(sensorPointOfInterest.getPoint().getLe(), 24.3, 0.1);
        assertEquals(sensorPointOfInterest.getTime(), 1625389203000000L);
        assertEquals(sensorPointOfInterest.getStart(), 1625389203000000L);
        assertEquals(sensorPointOfInterest.getStale(), 1625389208000000L);
    }

    @Test
    public void checkPlatformPosition() {
        UasDatalinkMessage sourceMessage = buildSourceMessage();
        Clock clock = Clock.fixed(Instant.parse("2021-07-13T10:22:26.935488Z"), ZoneOffset.UTC);
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage, clock);
        assertEquals(platformPosition.getType(), "a-f-A");
        assertEquals(platformPosition.getHow(), "m-p");
        assertEquals(platformPosition.getUid(), "TESTPLAT1_Mission2");
        assertEquals(platformPosition.getPoint().getLat(), -32.42, 0.0001);
        assertEquals(platformPosition.getPoint().getLon(), 143.24, 0.0001);
        assertEquals(platformPosition.getPoint().getHae(), 1201, 0.0001);
        assertEquals(platformPosition.getPoint().getCe(), 9_999_999, 0.1);
        assertEquals(platformPosition.getPoint().getLe(), 9_999_999, 0.1);
        assertEquals(platformPosition.getTime(), 1625389203000000L);
        assertEquals(platformPosition.getStart(), 1625389203000000L);
        assertEquals(platformPosition.getStale(), 1625389208000000L);
        assertEquals(platformPosition.getSensorModel(), "SenSOR3");
        assertEquals(platformPosition.getSensorAzimuth(), 135.8, 0.1);
        assertEquals(platformPosition.getSensorFov(), 13.3, 0.01);
        assertEquals(platformPosition.getSensorVfov(), 23.2, 0.01);
        assertEquals(platformPosition.getSensorRange(), 1400.3, 0.01);
        assertEquals(platformPosition.toXml(), PLATFORM_POSITION_XML);
    }

    private UasDatalinkMessage buildSourceMessage() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(1201));
        map.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(90.0));
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(45.8));
        map.put(UasDatalinkTag.SensorVerticalFov, new VerticalFov(23.2));
        map.put(UasDatalinkTag.SensorHorizontalFov, new HorizontalFov(13.3));
        map.put(UasDatalinkTag.SlantRange, new SlantRange(1400.3));
        map.put(UasDatalinkTag.TargetLocationLatitude, new TargetLocationLatitude(-32.4));
        map.put(UasDatalinkTag.TargetLocationLongitude, new TargetLocationLongitude(143.23));
        map.put(UasDatalinkTag.TargetLocationElevation, new TargetLocationElevation(143));
        map.put(UasDatalinkTag.TargetErrorCe90, new TargetErrorEstimateCe90(100));
        map.put(UasDatalinkTag.TargetErrorLe90, new TargetErrorEstimateLe90(40));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "TESTPLAT1"));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "Mission2"));
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "SenSOR3"));
        return sourceMessage;
    }

    @Test
    public void checkPlatformPositionAlternate() {
        UasDatalinkMessage sourceMessage = buildSourceMessageAlternate();
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertEquals(platformPosition.getPoint().getLat(), -32.42, 0.0001);
        assertEquals(platformPosition.getPoint().getLon(), 143.24, 0.0001);
        assertEquals(platformPosition.getPoint().getHae(), 12011, 0.0001);
        assertEquals(platformPosition.getPoint().getCe(), 9_999_999.0);
        assertEquals(platformPosition.getPoint().getLe(), 9_999_999.0);
        assertEquals(platformPosition.getSensorAzimuth(), 35.8, 0.1);
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkPlatformPositionNoLatitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoLatitudeOrLongitude() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorEllipsoidHeight, new SensorEllipsoidHeight(145));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
    }

    @Test
    public void checkPlatformPositionNoAlt() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getPoint());
        assertEquals(platformPosition.getUid(), "jmisb");
    }

    @Test
    public void checkPlatformPositionSensorAzimuthNoHeading() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(20.0));
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        PlatformPosition platformPosition = converter.getPlatformPosition(sourceMessage);
        assertNull(platformPosition.getSensorAzimuth());
    }

    private UasDatalinkMessage buildSourceMessageAlternate() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
        map.put(UasDatalinkTag.SensorLatitude, new SensorLatitude(-32.42));
        map.put(UasDatalinkTag.SensorLongitude, new SensorLongitude(143.24));

        map.put(
                UasDatalinkTag.SensorEllipsoidHeightExtended,
                new SensorEllipsoidHeightExtended(12011));
        map.put(UasDatalinkTag.PlatformHeadingAngle, new PlatformHeadingAngle(350.0));
        map.put(UasDatalinkTag.SensorRelativeAzimuthAngle, new SensorRelativeAzimuth(45.8));
        return sourceMessage;
    }

    @Test
    public void checkSPISimple() {
        UasDatalinkMessage sourceMessage = buildSourceMessageSimple();
        ConversionConfiguration configuration = new ConversionConfiguration();
        KlvToCot converter = new KlvToCot(configuration);
        SensorPointOfInterest sensorPointOfInterest =
                converter.getSensorPointOfInterest(sourceMessage);
        assertEquals(sensorPointOfInterest.getType(), "b-m-p-s-p-i");
        assertEquals(sensorPointOfInterest.getHow(), "m-p");
        assertEquals(sensorPointOfInterest.getUid(), "TESTPLAT1_Mission2_SenSOR3");
        assertEquals(sensorPointOfInterest.getLinkType(), "a-f-A");
        assertEquals(sensorPointOfInterest.getLinkUid(), "TESTPLAT1_Mission2");
        assertEquals(sensorPointOfInterest.getLinkRelation(), "p-p");
        assertEquals(sensorPointOfInterest.getPoint().getLat(), -32.4, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getLon(), 143.23, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getHae(), 143, 0.0001);
        assertEquals(sensorPointOfInterest.getPoint().getCe(), 9999999, 0.1);
        assertEquals(sensorPointOfInterest.getPoint().getLe(), 9999999, 0.1);
        assertEquals(sensorPointOfInterest.getTime(), 1625971208000000L);
        assertEquals(sensorPointOfInterest.getStart(), 1625971208000000L);
        assertEquals(sensorPointOfInterest.getStale(), 1625971213000000L);
    }

    private UasDatalinkMessage buildSourceMessageSimple() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 11), LocalTime.of(2, 40, 8, 0))));
        map.put(UasDatalinkTag.FrameCenterLatitude, new FrameCenterLatitude(-32.4));
        map.put(UasDatalinkTag.FrameCenterLongitude, new FrameCenterLongitude(143.23));
        map.put(UasDatalinkTag.FrameCenterElevation, new FrameCenterElevation(143));
        map.put(
                UasDatalinkTag.PlatformDesignation,
                new UasDatalinkString(UasDatalinkString.PLATFORM_DESIGNATION, "TESTPLAT1"));
        map.put(
                UasDatalinkTag.MissionId,
                new UasDatalinkString(UasDatalinkString.MISSION_ID, "Mission2"));
        map.put(
                UasDatalinkTag.ImageSourceSensor,
                new UasDatalinkString(UasDatalinkString.IMAGE_SOURCE_SENSOR, "SenSOR3"));
        return sourceMessage;
    }
}
