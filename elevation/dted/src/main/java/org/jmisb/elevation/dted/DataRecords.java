package org.jmisb.elevation.dted;
/**
 * Data Record.
 *
 * <p>The elevations with a data record have a constant longitude value. The first data value is the
 * southernmost known elevation and the last data value is the northernmost.
 *
 * <p>See MIL-PRF-89020B 3.12.f for the structure definition.
 */
public class DataRecords {
    private int dataBlockCount;
    private int longitudeCount;
    private int latitudeCount;
    private final int data[][];

    public DataRecords(int numLongitudePoints, int numLatitudePoints) {
        data = new int[numLongitudePoints][numLatitudePoints];
    }

    public int getDataBlockCount() {
        return dataBlockCount;
    }

    public void setDataBlockCount(int dataBlockCount) {
        this.dataBlockCount = dataBlockCount;
    }

    public int getLongitudeCount() {
        return longitudeCount;
    }

    public void setLongitudeCount(int longitudeCount) {
        this.longitudeCount = longitudeCount;
    }

    public int getLatitudeCount() {
        return latitudeCount;
    }

    public void setLatitudeCount(int latitudeCount) {
        this.latitudeCount = latitudeCount;
    }

    public void setData(int x, int y, int value) {
        data[x][y] = value;
    }

    public int[][] getData() {
        return data.clone();
    }

    public int getDataAt(int x, int y) {
        return data[x][y];
    }
}
