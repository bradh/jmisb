package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.PrecisionTimeStamp;
import org.jmisb.api.klv.st0601.TargetErrorEstimateCe90;
import org.jmisb.api.klv.st0601.TargetErrorEstimateLe90;
import org.jmisb.api.klv.st0601.TargetLocationElevation;
import org.jmisb.api.klv.st0601.TargetLocationLatitude;
import org.jmisb.api.klv.st0601.TargetLocationLongitude;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkString;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.testng.annotations.Test;

/** Unit tests for KlvToCot. */
public class KlvToCotTest {

    public KlvToCotTest() {}

    @Test
    public void checkSPI() {
        SortedMap<UasDatalinkTag, IUasDatalinkValue> map = new TreeMap<>();
        UasDatalinkMessage sourceMessage = new UasDatalinkMessage(map);
        map.put(
                UasDatalinkTag.PrecisionTimeStamp,
                new PrecisionTimeStamp(
                        LocalDateTime.of(LocalDate.of(2021, 7, 4), LocalTime.of(9, 0, 3, 0))));
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
        SensorPointOfInterest sensorPointOfInterest =
                KlvToCot.getSensorPointOfInterest(sourceMessage);
        assertEquals(sensorPointOfInterest.getType(), "b-m-p-s-p-i");
        assertEquals(sensorPointOfInterest.getHow(), "m-p");
        assertEquals(sensorPointOfInterest.getUid(), "TESTPLAT1_Mission2_SenSOR3");
    }
}
