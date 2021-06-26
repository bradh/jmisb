package org.jmisb.api.klv.st1108.metric;

import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.ArrayBuilder;
import org.jmisb.api.klv.IKlvKey;
import org.jmisb.api.klv.IKlvValue;
import org.jmisb.api.klv.INestedKlvValue;
import org.jmisb.api.klv.LdsField;
import org.jmisb.api.klv.LdsParser;
import org.jmisb.api.klv.st1108.IInterpretabilityQualityMetadataValue;

public class MetricLocalSet implements IInterpretabilityQualityMetadataValue, INestedKlvValue {

    static MetricLocalSet fromBytes(byte[] bytes) throws KlvParseException {
        MetricLocalSet metricLocalSet = new MetricLocalSet();
        List<LdsField> fields = LdsParser.parseFields(bytes, 0, bytes.length);
        for (LdsField field : fields) {
            MetricLocalSetKey key = MetricLocalSetKey.getKey(field.getTag());
            switch (key) {
                case MetricName:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricName, new MetricName(field.getData()));
                    break;
                case MetricVersion:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricVersion, new MetricVersion(field.getData()));
                    break;
                case MetricImplementer:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricImplementer,
                            new MetricImplementer(field.getData()));
                    break;
                case MetricParameters:
                    metricLocalSet.map.put(
                            MetricLocalSetKey.MetricParameters,
                            new MetricParameters(field.getData()));
                    break;
                case MetricTime:
                    break;
                case MetricValue:
                    break;
                default:
                    throw new AssertionError(key.name());
            }
        }
        return metricLocalSet;
    }

    /** Map containing all data elements in the message. */
    private final SortedMap<MetricLocalSetKey, IInterpretabilityQualityMetadataValue> map =
            new TreeMap<>();

    /**
     * Add the bytes for this local set to the specified array builder.
     *
     * <p>This is for internal usage. You can use {@code getBytes()} for other applications.
     *
     * @param arrayBuilder the array builder to use.
     */
    void addBytesTo(ArrayBuilder arrayBuilder) {
        arrayBuilder.append(getBytes());
    }

    @Override
    public IKlvValue getField(IKlvKey tag) {
        MetricLocalSetKey identifier = (MetricLocalSetKey) tag;
        return map.get(identifier);
    }

    @Override
    public Set<? extends IKlvKey> getIdentifiers() {
        return map.keySet();
    }

    @Override
    public String getDisplayName() {
        return "Metric";
    }

    @Override
    public String getDisplayableValue() {
        return "[Metric]";
    }

    @Override
    public byte[] getBytes() {
        throw new UnsupportedOperationException(
                "Not supported yet."); // To change body of generated methods, choose Tools |
        // Templates.
    }
}
