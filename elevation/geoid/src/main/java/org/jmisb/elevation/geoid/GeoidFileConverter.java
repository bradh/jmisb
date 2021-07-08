package org.jmisb.elevation.geoid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

/**
 * Geoid File Converter.
 *
 * <p>Tool to do offline conversion of the NGA format "WW15MGH.GRD" interpolation grid into a local
 * format that is easy to deserialise using the Java DataInput interface.
 */
public class GeoidFileConverter {

    /** @param args the command line arguments */
    public static void main(String[] args) throws IOException {
        GeoidFileConverter converter = new GeoidFileConverter();
        float[][] values = converter.getValues();
        Geoid geoid = new Geoid(values);

        System.out.println(
                "value (-31.628)"
                        + geoid.getValue(38.6281550, 269.7791550)
                        + " - "
                        + geoid.getValueBicubic(38.6281550, 269.7791550));
        System.out.println(
                "value ( -2.969)"
                        + geoid.getValue(-14.6212170, 305.0211140)
                        + " - "
                        + geoid.getValueBicubic(-14.6212170, 305.0211140));
        System.out.println(
                "value (-43.575)"
                        + geoid.getValue(46.8743190, 102.4487290)
                        + " - "
                        + geoid.getValueBicubic(46.8743190, 102.4487290));
        System.out.println(
                "value (15.871)"
                        + geoid.getValue(-23.6174460, 133.8747120)
                        + " - "
                        + geoid.getValueBicubic(-23.6174460, 133.8747120));
        System.out.println(
                "value (50.066)"
                        + geoid.getValue(38.6254730, 359.9995000)
                        + " - "
                        + geoid.getValueBicubic(38.6254730, 359.9995000));
        System.out.println(
                "value (17.329)"
                        + geoid.getValue(-0.4667440, 0.0023000)
                        + " - "
                        + geoid.getValueBicubic(-0.4667440, 0.0023000));
    }

    private float[][] getValues() throws IOException {
        float[][] values;
        try (Reader reader =
                        new InputStreamReader(
                                getClass()
                                        .getResourceAsStream(
                                                "/EGM96_Interpolation_Grid/WW15MGH.GRD"),
                                StandardCharsets.UTF_8);
                BufferedReader inputStream = new BufferedReader(reader)) {
            String firstLine = inputStream.readLine();
            if (firstLine == null) {
                return null;
            }
            String[] headerParts = firstLine.trim().split("\\s+");
            if (headerParts.length != 6) {
                throw new IOException("Header line not in expected format:" + firstLine);
            }
            float bottom = Float.parseFloat(headerParts[0]);
            float top = Float.parseFloat(headerParts[1]);
            float left = Float.parseFloat(headerParts[2]);
            float right = Float.parseFloat(headerParts[3]);
            float yres = Float.parseFloat(headerParts[4]);
            float xres = Float.parseFloat(headerParts[5]);
            System.out.println(
                    String.format("%f, %f, %f, %f, %f, %f", bottom, top, left, right, yres, xres));
            int numRows = (int) Math.ceil((top - bottom) / yres);
            int numColumns = (int) Math.ceil((right - left) / xres);
            values = new float[numRows + 1][numColumns + 1];
            String line = inputStream.readLine();
            int rowIndex = 0;
            int columnIndex = 0;
            while (line != null) {
                String cleanline = line.strip();
                String[] lineParts = cleanline.split("\\s+");
                for (String linePart : lineParts) {
                    if (linePart.strip().isEmpty()) {
                        continue;
                    }
                    float value = Float.parseFloat(linePart.strip());
                    values[rowIndex][columnIndex] = value;
                    columnIndex += 1;
                    if (columnIndex > numColumns) {
                        rowIndex += 1;
                        columnIndex = 0;
                    }
                }
                line = inputStream.readLine();
            }
        }
        return values;
    }
}
