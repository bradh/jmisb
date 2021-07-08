package org.jmisb.elevation.geoid;

/** Geoid (EGM 96) conversion routines. */
public class Geoid {

    private final float[][] values;

    Geoid(float[][] values) {
        this.values = values;
    }

    public String getValue(double lat, double lon) {
        return getValueBilinear(lat, lon);
    }

    public String getValueBilinear(double lat, double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float topLeft = findValue(baseRow, baseColumn);
        float topRight = findValue(baseRow, baseColumn + 1);
        float bottomLeft = findValue(baseRow + 1, baseColumn);
        float bottomRight = findValue(baseRow + 1, baseColumn + 1);
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        double xOffset = getOffsetFromBaseColumn(lon, baseColumn);
        float value =
                interpolateBilinear(
                        topLeft,
                        topRight,
                        bottomLeft,
                        bottomRight,
                        (float) yOffset,
                        (float) xOffset);
        return String.format("%f", value);
    }

    public String getValueBicubic(double lat, double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float[] interpolatedRows = new float[4];
        for (int i = 0; i < 4; ++i) {
            float v00 = findValue(baseRow - 1 + i, baseColumn - 1);
            float v01 = findValue(baseRow - 1 + i, baseColumn);
            float v02 = findValue(baseRow - 1 + i, baseColumn + 1);
            float v03 = findValue(baseRow - 1 + i, baseColumn + 2);
            float xOffset = (float) getOffsetFromBaseColumn(lon, baseColumn);
            interpolatedRows[i] = interpolateCubic(v00, v01, v02, v03, xOffset);
        }
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        float value =
                interpolateCubic(
                        interpolatedRows[0],
                        interpolatedRows[1],
                        interpolatedRows[2],
                        interpolatedRows[3],
                        (float) yOffset);
        return String.format("%f", value);
    }

    private static float interpolateCubic(
            float v00, float v01, float v02, float v03, float xOffset) {
        return (-0.5f * v00 + 1.5f * v01 - 1.5f * v02 + 0.5f * v03) * xOffset * xOffset * xOffset
                + (v00 - 2.5f * v01 + 2f * v02 - 0.5f * v03) * xOffset * xOffset
                + (-0.5f * v00 + 0.5f * v02) * xOffset
                + v01;
    }

    static double getOffsetFromBaseColumn(double lon, int baseColumn) {
        return lon - (baseColumn / 4.0);
    }

    static double getOffsetFromBaseRow(double lat, int baseRow) {
        return (90.0 - (baseRow / 4.0)) - lat;
    }

    static int getBaseColumn(double lon) {
        return (int) Math.floor(lon * 4.0);
    }

    static int getBaseRow(double lat) {
        return (int) Math.floor((90 - lat) * 4.0);
    }

    private float interpolateBilinear(
            float topLeft,
            float topRight,
            float bottomLeft,
            float bottomRight,
            float xOffset,
            float yOffset) {
        return (1 - yOffset) * ((1 - xOffset) * topLeft + xOffset * topRight)
                + yOffset * ((1 - xOffset) * bottomLeft + xOffset * bottomRight);
    }

    private float findValue(int baseRow, int baseColumn) {
        if (baseRow < 0) {
            baseRow += values.length;
        }
        if (baseRow >= values.length) {
            baseRow = baseRow - values.length;
        }
        if (baseColumn < 0) {
            baseColumn += values[0].length;
        }
        if (baseColumn >= values[0].length) {
            baseColumn = baseColumn - values[0].length;
        }
        return values[baseRow][baseColumn];
    }
}
