package org.jmisb.api.klv.st1108.metric;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

/**
 * Metric Local Sets.
 *
 * <p>This class represents any number (zero or more) Metric Local Set instances that may be found
 * within an {@code InterpretabilityQualityLocalSet}.
 */
public class MetricLocalSets implements IInterpretabilityQualityMetadataValue, INestedKlvValue {

    public List<MetricLocalSet> metricLocalSets = new ArrayList<>();

    /** Create new instance. */
    public MetricLocalSets() {}

    @Override
    public byte[] getBytes() {
        ArrayBuilder arrayBuilder = new ArrayBuilder();
        metricLocalSets.forEach(
                metricLocalSet -> {
                    metricLocalSet.addBytesTo(arrayBuilder);
                });
        return arrayBuilder.toBytes();
    }

    @Override
    public String getDisplayableValue() {
        return "[Metric Local Sets]";
    }

    @Override
    public final String getDisplayName() {
        return "Metric Local Sets";
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        return metricLocalSets.get(tag.getIdentifier());
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        Set<MetricLocalSetsKey> identifiers = new TreeSet<>();
        for (int i = 0; i < metricLocalSets.size(); i++) {
            identifiers.add(new MetricLocalSetsKey(i));
        }
        return identifiers;
    }
}
