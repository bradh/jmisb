package org.jmisb.elevation.geoid;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** Interpolation Grid. */
public class Grid {
    private float left;
    private float top;
    private float right;
    private float bottom;
    private float yResolution;
    private float xResolution;

    private float[][] values;

    public static Grid fromEGM96Grid() throws IOException {
        Grid grid = new Grid();
        InputStream is = grid.getClass().getResourceAsStream("/egm96.dat");
        DataInputStream dis = new DataInputStream(is);
        grid.readHeaderFrom(dis);
        grid.values = new float[grid.getNumRows()][grid.getNumColumns()];
        grid.readValuesFrom(dis);
        return grid;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }

    public float getyResolution() {
        return yResolution;
    }

    public void setyResolution(float yResolution) {
        this.yResolution = yResolution;
    }

    public float getxResolution() {
        return xResolution;
    }

    public void setxResolution(float xResolution) {
        this.xResolution = xResolution;
    }

    public void writeHeaderTo(DataOutputStream dos) throws IOException {
        dos.writeFloat(bottom);
        dos.writeFloat(top);
        dos.writeFloat(left);
        dos.writeFloat(right);
        dos.writeFloat(yResolution);
        dos.writeFloat(xResolution);
    }

    private void readHeaderFrom(DataInputStream dis) throws IOException {
        bottom = dis.readFloat();
        top = dis.readFloat();
        left = dis.readFloat();
        right = dis.readFloat();
        yResolution = dis.readFloat();
        xResolution = dis.readFloat();
    }

    public void writeValuesTo(DataOutputStream dos) throws IOException {
        for (float[] value : values) {
            for (int c = 0; c < value.length; c++) {
                dos.writeFloat(value[c]);
            }
        }
    }

    private void readValuesFrom(DataInputStream dis) throws IOException {
        for (float[] value : values) {
            for (int c = 0; c < value.length; c++) {
                value[c] = dis.readFloat();
            }
        }
    }

    public int getNumRows() {
        return 1 + (int) Math.ceil((top - bottom) / yResolution);
    }

    public int getNumColumns() {
        return 1 + (int) Math.ceil((right - left) / xResolution);
    }

    public void setValue(int row, int column, float value) {
        values[row][column] = value;
    }

    float findValue(int baseRow, int baseColumn) {
        if (baseRow < 0) {
            baseRow = 0;
        }
        if (baseRow >= values.length) {
            baseRow = values.length - 1;
        }
        if (baseColumn < 0) {
            baseColumn += values[baseRow].length - 1;
        }
        if (baseColumn >= values[baseRow].length) {
            baseColumn = baseColumn - (values[0].length - 1);
        }
        return values[baseRow][baseColumn];
    }
}
