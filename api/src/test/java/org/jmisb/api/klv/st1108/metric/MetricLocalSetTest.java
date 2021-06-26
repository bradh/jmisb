package org.jmisb.api.klv.st1108.metric;

import static org.testng.Assert.*;

import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.IKlvValue;
import org.testng.annotations.Test;

/** Unit tests for Metric Local Set. */
public class MetricLocalSetTest {

    @Test
    public void mandatory() throws KlvParseException {
        byte[] bytes =
                new byte[] {
                    0x01,
                    0x03,
                    (byte) 0x52,
                    (byte) 0x45,
                    (byte) 0x52,
                    0x02,
                    0x04,
                    (byte) 0x31,
                    (byte) 0x2E,
                    (byte) 0x33,
                    (byte) 0x38,
                    0x03,
                    0x0F,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x4F,
                    (byte) 0x72,
                    (byte) 0x67,
                    (byte) 0x1e,
                    (byte) 0x4D,
                    (byte) 0x79,
                    (byte) 0x20,
                    (byte) 0x67,
                    (byte) 0x72,
                    (byte) 0x6F,
                    (byte) 0x75,
                    (byte) 0x70,
                    0x04,
                    0x09,
                    (byte) 0xC3,
                    (byte) 0xBC,
                    (byte) 0x2C,
                    (byte) 0x41,
                    (byte) 0x42,
                    (byte) 0x3A,
                    (byte) 0x33,
                    (byte) 0x32,
                    (byte) 0x34
                };
        MetricLocalSet uut = MetricLocalSet.fromBytes(bytes);
        assertEquals(uut.getIdentifiers().size(), 4);
        checkMetricName(uut);
        checkMetricVersion(uut);
        checkMetricImplementer(uut);
        checkMetricParameters(uut);
    }

    private void checkMetricName(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricName));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricName);
        assertTrue(value instanceof MetricName);
        MetricName metricName = (MetricName) value;
        assertEquals(metricName.getDisplayableValue(), "RER");
    }

    private void checkMetricVersion(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricVersion));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricVersion);
        assertTrue(value instanceof MetricVersion);
        MetricVersion metricVersion = (MetricVersion) value;
        assertEquals(metricVersion.getDisplayableValue(), "1.38");
    }

    private void checkMetricImplementer(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricImplementer));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricImplementer);
        assertTrue(value instanceof MetricImplementer);
        MetricImplementer metricImplementer = (MetricImplementer) value;
        assertEquals(metricImplementer.getDisplayableValue(), "My Org - My group");
    }

    private void checkMetricParameters(MetricLocalSet uut) {
        assertTrue(uut.getIdentifiers().contains(MetricLocalSetKey.MetricParameters));
        IKlvValue value = uut.getField(MetricLocalSetKey.MetricParameters);
        assertTrue(value instanceof MetricParameters);
        MetricParameters metricParameters = (MetricParameters) value;
        assertEquals(metricParameters.getDisplayableValue(), "ü,AB:324");
    }
}
