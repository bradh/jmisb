package org.jmisb.api.klv.st0805;

import static org.testng.Assert.*;

import org.testng.annotations.Test;

/** Unit tests for CotSensor. */
public class CotSensorTest {

    public CotSensorTest() {}

    @Test
    public void serialise() {
        CotSensor uut = new CotSensor();
        uut.setSensorAzimuth(10.3);
        uut.setSensorFov(3.53);
        uut.setSensorVfov(2.45);
        uut.setSensorModel("EON");
        uut.setSensorRange(1234.5);
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(
                sb.toString(),
                "<sensor azimuth='10.3' fov='3.53' vfov='2.45' model='EON' range='1234.5'/>");
    }

    @Test
    public void serialiseEmpty() {
        CotSensor uut = new CotSensor();
        StringBuilder sb = new StringBuilder();
        uut.writeAsXML(sb);
        assertEquals(sb.toString(), "<sensor/>");
    }
}
