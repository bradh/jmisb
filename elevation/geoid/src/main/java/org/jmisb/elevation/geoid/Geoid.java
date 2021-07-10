package org.jmisb.elevation.geoid;

import java.io.IOException;

/** Geoid (EGM 96) conversion routines. */
public class Geoid {

    private final Grid grid;

    Geoid() throws IOException {
        this.grid = Grid.fromEGM96Grid();
    }

    public float getValue(double lat, double lon) {

        return getNearest(lat, lon);
    }

    public float getNearest(double lat, double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float topLeft = grid.findValue(baseRow, baseColumn);
        float topRight = grid.findValue(baseRow, baseColumn + 1);
        float bottomLeft = grid.findValue(baseRow + 1, baseColumn);
        float bottomRight = grid.findValue(baseRow + 1, baseColumn + 1);
        double yOffset = getOffsetFromBaseRow(lat, baseRow);
        double xOffset = getOffsetFromBaseColumn(lon, baseColumn);
        if (yOffset < 0.5) {
            if (xOffset < 0.5) {
                return topLeft;
            } else {
                return topRight;
            }
        } else {
            if (xOffset < 0.5) {
                return bottomLeft;
            } else {
                return bottomRight;
            }
        }
    }

    public float getValueBilinear(double lat, double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float topLeft = grid.findValue(baseRow, baseColumn);
        float topRight = grid.findValue(baseRow, baseColumn + 1);
        float bottomLeft = grid.findValue(baseRow + 1, baseColumn);
        float bottomRight = grid.findValue(baseRow + 1, baseColumn + 1);
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
        return value;
    }

    public float getValueBicubic(double lat, double lon) {
        int baseRow = getBaseRow(lat);
        int baseColumn = getBaseColumn(lon);
        float[] interpolatedRows = new float[4];
        for (int i = 0; i < 4; ++i) {
            float v00 = grid.findValue(baseRow - 1 + i, baseColumn - 1);
            float v01 = grid.findValue(baseRow - 1 + i, baseColumn);
            float v02 = grid.findValue(baseRow - 1 + i, baseColumn + 1);
            float v03 = grid.findValue(baseRow - 1 + i, baseColumn + 2);
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
        return value;
    }

    private static float interpolateCubic(
            float v00, float v01, float v02, float v03, float xOffset) {
        return (-0.5f * v00 + 1.5f * v01 - 1.5f * v02 + 0.5f * v03) * xOffset * xOffset * xOffset
                + (v00 - 2.5f * v01 + 2f * v02 - 0.5f * v03) * xOffset * xOffset
                + (-0.5f * v00 + 0.5f * v02) * xOffset
                + v01;
    }

    double getOffsetFromBaseColumn(double lon, int baseColumn) {
        return (lon - (baseColumn / 4.0)) * 4;
    }

    double getOffsetFromBaseRow(double lat, int baseRow) {
        return ((90.0 - (baseRow * grid.getxResolution())) - lat) / grid.getxResolution();
    }

    int getBaseColumn(double lon) {
        return (int) Math.floor(lon / grid.getxResolution());
    }

    int getBaseRow(double lat) {
        return (int) Math.floor((90 - lat) / grid.getyResolution());
    }

    private float interpolateBilinear(
            float topLeft,
            float topRight,
            float bottomLeft,
            float bottomRight,
            float xOffset,
            float yOffset) {
        float a = (1 - xOffset) * topLeft + xOffset * topRight;
        float b = (1 - xOffset) * bottomLeft + xOffset * bottomRight;
        return (1 - yOffset) * a + yOffset * b;
    }
}
