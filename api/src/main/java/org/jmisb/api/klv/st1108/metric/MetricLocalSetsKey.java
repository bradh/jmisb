package org.jmisb.api.klv.st1108.metric;

import org.jmisb.api.klv.IKlvKey;

/** Pseudo-key value to track the various {@code MetricLocalSet} instances. */
public class MetricLocalSetsKey implements IKlvKey {

    private final int identifier;

    MetricLocalSetsKey(int id) {
        this.identifier = id;
    }

    @Override
    public int getIdentifier() {
        return identifier;
    }
}
