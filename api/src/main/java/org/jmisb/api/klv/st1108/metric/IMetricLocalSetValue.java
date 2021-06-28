package org.jmisb.api.klv.st1108.metric;

import org.jmisb.api.klv.IKlvValue;

public interface IMetricLocalSetValue extends IKlvValue {

    byte[] getBytes();
}
