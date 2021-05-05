package org.jmisb.elevation.dted;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/** Parser for DTED files */
public class DtedParser {

    private static final int DEGREES_LEN = 3;
    private static final int MINUTES_LEN = 2;
    private static final int SECONDS_LEN = 2;
    private static final int HEMISPHERE_LEN = 1;
    private static final double MINUTES_PER_DEGREE = 60.0;
    private static final double SECONDS_PER_DEGREE = 60.0 * 60.0;
    private static final int UNIQUE_REFERENCE_LEN = 12;
    private static final int USER_HEADER_LABEL_RESERVED_LEN = 24;
    private static final int DSI_RESERVED1_LEN = 26;
    private static final int DSI_RESERVED2_LEN = 8;
    private static final int DSI_RESERVED3_LEN = 16;
    private static final int DSI_RESERVED4_LEN = 22;
    private static final int BLANK_ACC_SUBREGION_LEN = 284;
    private static final int ACC_RESERVED1_LEN = 4;
    private static final int ACC_NIMA_USE1_LEN = 1;
    private static final int ACC_RESERVED2_LEN = 31;
    private static final int ACC_NIMA_USE2_LEN = 18;
    private static final int ACC_RESERVED3_LEN = 69;

    public DtedParser() {}

    public DtedFile parse(String filename) throws IOException {
        DtedFile dtedFile = new DtedFile();
        try (FileInputStream fis = new FileInputStream(new File(filename))) {
            dtedFile.setUserHeaderLabel(parseUserHeaderLabel(fis));
            dtedFile.setDataSetIdentification(parseDataSetIdentification(fis));
            dtedFile.setAccuracyDescription(parseAccuracyDescription(fis));
            dtedFile.addDataRecords(
                    parseDataRecords(
                            fis,
                            dtedFile.getDataSetIdentification().getNumLongitudeLines(),
                            dtedFile.getDataSetIdentification().getNumLatitudeLines()));
        }
        return dtedFile;
    }

    private UserHeaderLabel parseUserHeaderLabel(FileInputStream fis) throws IOException {
        UserHeaderLabel userHeaderLabel = new UserHeaderLabel();
        checkUHL1(fis);
        userHeaderLabel.setLongitudeOfOrigin(readDMS(fis));
        userHeaderLabel.setLatitudeOfOrigin(readDMS(fis));
        userHeaderLabel.setLongitudeDataInterval(readInteger(fis, 4) / 10.0);
        userHeaderLabel.setLatitudeDataInterval(readInteger(fis, 4) / 10.0);
        userHeaderLabel.setAbsoluteVerticalAccuracy(readAbsoluteVerticalAccuracy(fis));
        userHeaderLabel.setSecurityClassification(
                SecurityClassification.getEnumValueFromUHLEncoding(readString(fis, 3)));
        userHeaderLabel.setUniqueReference(readString(fis, UNIQUE_REFERENCE_LEN));
        userHeaderLabel.setNumLongitudeLines(readInteger(fis, 4));
        userHeaderLabel.setNumLatitudePoints(readInteger(fis, 4));
        userHeaderLabel.setMultipleAccuracy(readInteger(fis, 1));
        // Reserved section.
        if (USER_HEADER_LABEL_RESERVED_LEN != fis.skip(USER_HEADER_LABEL_RESERVED_LEN)) {
            throw new IOException("Failed to skip UHL Reserved Section");
        }
        return userHeaderLabel;
    }

    private void checkUHL1(FileInputStream fis) throws IOException {
        // The standard separates the "UHL" and the "1", but its fixed content in any case
        String uhl1 = new String(fis.readNBytes(4), StandardCharsets.US_ASCII);
        if (!uhl1.equals("UHL1")) {
            throw new IOException("UHL Recognition Sentinel or 1 not found: " + uhl1);
        }
    }

    private Integer readAbsoluteVerticalAccuracy(FileInputStream fis) throws IOException {
        String val = readString(fis, 4);
        if (val.contains("N/A")) {
            return null;
        }
        return Integer.parseInt(val);
    }

    private double readDMS(FileInputStream fis) throws IOException {
        double result = readAngleDMS(fis);
        String hemisphere = readString(fis, HEMISPHERE_LEN);
        if ((hemisphere.equals("S")) || (hemisphere.equals("W"))) {
            result *= -1.0;
        }
        return result;
    }

    private double readAngleDMS(FileInputStream fis) throws IOException, NumberFormatException {
        int degrees = readInteger(fis, DEGREES_LEN);
        int minutes = readInteger(fis, MINUTES_LEN);
        int seconds = readInteger(fis, SECONDS_LEN);
        double result = degrees + (minutes / MINUTES_PER_DEGREE) + (seconds / SECONDS_PER_DEGREE);
        return result;
    }

    private double readAngleDMSDecimalSeconds(FileInputStream fis)
            throws IOException, NumberFormatException {
        int degrees = readInteger(fis, DEGREES_LEN);
        int minutes = readInteger(fis, MINUTES_LEN);
        double seconds = readDouble(fis, 4);
        double result = degrees + (minutes / MINUTES_PER_DEGREE) + (seconds / SECONDS_PER_DEGREE);
        return result;
    }

    private double readLatitudeDMS(FileInputStream fis) throws IOException {
        int degrees = readInteger(fis, 2);
        int minutes = readInteger(fis, MINUTES_LEN);
        int seconds = readInteger(fis, SECONDS_LEN);
        double result = degrees + (minutes / MINUTES_PER_DEGREE) + (seconds / SECONDS_PER_DEGREE);
        String hemisphere = readString(fis, HEMISPHERE_LEN);
        if (hemisphere.equals("S")) {
            result *= -1.0;
        }
        return result;
    }

    private double readLongitudeDMS(FileInputStream fis) throws IOException {
        return readDMS(fis);
    }

    private double readLatitudeOfOrigin(FileInputStream fis) throws IOException {
        int degrees = readInteger(fis, 2);
        int minutes = readInteger(fis, MINUTES_LEN);
        double seconds = readDouble(fis, 4);
        double result = degrees + (minutes / MINUTES_PER_DEGREE) + (seconds / SECONDS_PER_DEGREE);
        String hemisphere = readString(fis, HEMISPHERE_LEN);
        if (hemisphere.equals("S")) {
            result *= -1.0;
        }
        return result;
    }

    private double readLongitudeOfOrigin(FileInputStream fis) throws IOException {
        int degrees = readInteger(fis, 3);
        int minutes = readInteger(fis, MINUTES_LEN);
        double seconds = readDouble(fis, 4);
        double result = degrees + (minutes / MINUTES_PER_DEGREE) + (seconds / SECONDS_PER_DEGREE);
        String hemisphere = readString(fis, HEMISPHERE_LEN);
        if (hemisphere.equals("W")) {
            result *= -1.0;
        }
        return result;
    }

    private int readInteger(FileInputStream fis, int numBytes)
            throws IOException, NumberFormatException {
        return Integer.parseInt(readString(fis, numBytes));
    }

    private Integer readIntegerOrNA(FileInputStream fis, int numBytes)
            throws IOException, NumberFormatException {
        String val = readString(fis, numBytes);
        if (val.startsWith("NA")) {
            return null;
        }
        return Integer.parseInt(val);
    }

    private double readDouble(FileInputStream fis, int numBytes)
            throws IOException, NumberFormatException {
        return Double.parseDouble(readString(fis, numBytes));
    }

    private static String readString(FileInputStream fis, int numBytes) throws IOException {
        return new String(fis.readNBytes(numBytes), StandardCharsets.US_ASCII);
    }

    private DataSetIdentification parseDataSetIdentification(FileInputStream fis)
            throws IOException {
        DataSetIdentification dataSetIdentification = new DataSetIdentification();
        String dsi = new String(fis.readNBytes(3), StandardCharsets.US_ASCII);
        if (!dsi.equals("DSI")) {
            throw new IOException("DSI Recognition Sentinel not found: " + dsi);
        }
        dataSetIdentification.setSecurityClassification(
                SecurityClassification.getEnumValueFromDSIEncoding(readString(fis, 1)));
        dataSetIdentification.setSecurityControlAndReleaseMarkings(readString(fis, 2));
        dataSetIdentification.setSecurityHandlingDescription(readString(fis, 27));
        // Reserved section.
        if (DSI_RESERVED1_LEN != fis.skip(DSI_RESERVED1_LEN)) {
            throw new IOException("Failed to skip DSI first reserved section");
        }
        dataSetIdentification.setSeriesDesignator(
                NIMASeriesDesignator.getEnumValue(readString(fis, 5)));
        dataSetIdentification.setUniqueReferenceNumber(readString(fis, 15));
        if (DSI_RESERVED2_LEN != fis.skip(DSI_RESERVED2_LEN)) {
            throw new IOException("Failed to skip DSI second reserved section");
        }
        dataSetIdentification.setDataEditionNumber(readInteger(fis, 2));
        dataSetIdentification.setMatchMergeVersion(readString(fis, 1));
        dataSetIdentification.setMaintenanceDate(readString(fis, 4));
        dataSetIdentification.setMatchMergeDate(readString(fis, 4));
        dataSetIdentification.setMaintenanceDescriptionCode(readString(fis, 4));
        dataSetIdentification.setProducerCode(readString(fis, 8));
        if (DSI_RESERVED3_LEN != fis.skip(DSI_RESERVED3_LEN)) {
            throw new IOException("Failed to skip DSI third reserved section");
        }
        dataSetIdentification.setProductSpecification(readString(fis, 9));
        dataSetIdentification.setAmendmentChange(readString(fis, 2));
        dataSetIdentification.setProductSpecDate(readString(fis, 4));
        dataSetIdentification.setVerticalDatum(readString(fis, 3));
        dataSetIdentification.setHorizontalDatum(readString(fis, 5));
        dataSetIdentification.setDigitizingCollectionSystem(readString(fis, 10));
        dataSetIdentification.setCompilationDate(readString(fis, 4));
        if (DSI_RESERVED4_LEN != fis.skip(DSI_RESERVED4_LEN)) {
            throw new IOException("Failed to skip DSI fourth reserved section");
        }
        dataSetIdentification.setLatitudeOfOriginOfData(readLatitudeOfOrigin(fis));
        dataSetIdentification.setLongitudeOfOriginOfData(readLongitudeOfOrigin(fis));
        dataSetIdentification.setSwCornerLatitude(readLatitudeDMS(fis));
        dataSetIdentification.setSwCornerLongitude(readLongitudeDMS(fis));
        dataSetIdentification.setNwCornerLatitude(readLatitudeDMS(fis));
        dataSetIdentification.setNwCornerLongitude(readLongitudeDMS(fis));
        dataSetIdentification.setNeCornerLatitude(readLatitudeDMS(fis));
        dataSetIdentification.setNeCornerLongitude(readLongitudeDMS(fis));
        dataSetIdentification.setSeCornerLatitude(readLatitudeDMS(fis));
        dataSetIdentification.setSeCornerLongitude(readLongitudeDMS(fis));
        dataSetIdentification.setClockwiseOrientationAngle(readAngleDMSDecimalSeconds(fis));
        dataSetIdentification.setLatitudeInterval(readInteger(fis, 4) / 10.0);
        dataSetIdentification.setLongitudeInterval(readInteger(fis, 4) / 10.0);
        dataSetIdentification.setNumLatitudeLines(readInteger(fis, 4));
        dataSetIdentification.setNumLongitudeLines(readInteger(fis, 4));
        dataSetIdentification.setPartialCellIndicator(readInteger(fis, 2));
        dataSetIdentification.setNimaPrivateUse(readString(fis, 101));
        dataSetIdentification.setCountryPrivateUse(readString(fis, 100));
        dataSetIdentification.setFreeTextComments(readString(fis, 156));
        return dataSetIdentification;
    }

    private AccuracyDescription parseAccuracyDescription(FileInputStream fis) throws IOException {
        AccuracyDescription accuracyDescription = new AccuracyDescription();
        String acc = new String(fis.readNBytes(3), StandardCharsets.US_ASCII);
        if (!acc.equals("ACC")) {
            throw new IOException("ACC Recognition Sentinel not found: " + acc);
        }
        accuracyDescription.setAbsoluteHorizontalAccuracy(readIntegerOrNA(fis, 4));
        accuracyDescription.setAbsoluteVerticalAccuracy(readIntegerOrNA(fis, 4));
        accuracyDescription.setRelativeHorizontalAccuracy(readIntegerOrNA(fis, 4));
        accuracyDescription.setRelativeVerticalAccuracy(readIntegerOrNA(fis, 4));
        if (ACC_RESERVED1_LEN != fis.skip(ACC_RESERVED1_LEN)) {
            throw new IOException("Failed to skip ACC first reserved section");
        }
        accuracyDescription.setNimaUseSection1(readString(fis, ACC_NIMA_USE1_LEN));
        if (ACC_RESERVED2_LEN != fis.skip(ACC_RESERVED2_LEN)) {
            throw new IOException("Failed to skip ACC second reserved section");
        }
        int numAccuracySubregions = readInteger(fis, 2);
        assert numAccuracySubregions == 0;
        // TODO: parse accuracy subregions properly, if we ever have some
        for (int i = 0; i < 9 - numAccuracySubregions; i++) {
            if (BLANK_ACC_SUBREGION_LEN != fis.skip(BLANK_ACC_SUBREGION_LEN)) {
                throw new IOException("Failed to skip ACC empty subregion " + i);
            }
        }
        if (ACC_NIMA_USE2_LEN != fis.skip(ACC_NIMA_USE2_LEN)) {
            throw new IOException("Failed to skip ACC NIMA use section");
        }
        if (ACC_RESERVED3_LEN != fis.skip(ACC_RESERVED3_LEN)) {
            throw new IOException("Failed to skip ACC third reserved section");
        }
        return accuracyDescription;
    }

    private DataRecords parseDataRecords(
            FileInputStream fis, int numLongitudePoints, int numLatitudePoints) throws IOException {
        DataRecords dataRecords = new DataRecords(numLongitudePoints, numLatitudePoints);
        for (int x = 0; x < numLongitudePoints; ++x) {
            DataRecordReader dataRecordReader = new DataRecordReader(fis);
            int recognitionSentinel = dataRecordReader.readByte();
            if (recognitionSentinel != 0xAA) {
                throw new IOException("Data Recognition Sentinel not found");
            }
            // TODO: these should just validate. Maybe push to DataRecordReader.
            dataRecords.setDataBlockCount(dataRecordReader.readFixedBinary(3));
            dataRecords.setLongitudeCount(dataRecordReader.readFixedBinary(2));
            dataRecords.setLatitudeCount(dataRecordReader.readFixedBinary(2));
            for (int y = 0; y < numLatitudePoints; y++) {
                dataRecords.setData(x, y, dataRecordReader.readFixedBinary(2));
            }
            dataRecordReader.checkChecksum();
        }
        return dataRecords;
    }
}
