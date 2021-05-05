package org.jmisb.elevation.dted;

import static org.testng.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import org.testng.annotations.Test;

/** DTED file parser. */
public class DtedParserTest {

    public DtedParserTest() {}

    @Test
    public void dt1() throws IOException, URISyntaxException {
        DtedParser parser = new DtedParser();
        URL resource = this.getClass().getClassLoader().getResource("s36_e149_3arc_v2.dt1");
        File file = Paths.get(resource.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        DtedFile dtedFile = parser.parse(absolutePath);
        assertNotNull(dtedFile);
        assertNotNull(dtedFile.getUserHeaderLabel());
        assertEquals(dtedFile.getUserHeaderLabel().getLongitudeOfOrigin(), 149.0000, 0.0001);
        assertEquals(dtedFile.getUserHeaderLabel().getLatitudeOfOrigin(), -36.0000, 0.0001);
        assertEquals(dtedFile.getUserHeaderLabel().getLongitudeDataInterval(), 3.0, 0.1);
        assertEquals(dtedFile.getUserHeaderLabel().getLatitudeDataInterval(), 3.0, 0.1);
        assertEquals(dtedFile.getUserHeaderLabel().getAbsoluteVerticalAccuracy().intValue(), 7);
        assertEquals(
                dtedFile.getUserHeaderLabel().getSecurityClassification(),
                SecurityClassification.Unclassified);
        assertEquals(dtedFile.getUserHeaderLabel().getUniqueReference(), "R13 065     ");
        assertEquals(dtedFile.getUserHeaderLabel().getNumLongitudeLines(), 1201);
        assertEquals(dtedFile.getUserHeaderLabel().getNumLatitudePoints(), 1201);
        assertEquals(dtedFile.getUserHeaderLabel().getMultipleAccuracy(), 0);
        assertNotNull(dtedFile.getDataSetIdentification());
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityClassification(),
                SecurityClassification.Unclassified);
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityControlAndReleaseMarkings(), "OO");
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityHandlingDescription(),
                "PUBLIC SALE/NO RESTRICTION ");
        assertEquals(
                dtedFile.getDataSetIdentification().getSeriesDesignator(),
                NIMASeriesDesignator.DTED1);
        assertEquals(
                dtedFile.getDataSetIdentification().getUniqueReferenceNumber(), "R13 065        ");
        assertEquals(dtedFile.getDataSetIdentification().getDataEditionNumber(), 2);
        assertEquals(dtedFile.getDataSetIdentification().getMatchMergeVersion(), "A");
        assertEquals(dtedFile.getDataSetIdentification().getMaintenanceDate(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getMatchMergeDate(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getMaintenanceDescriptionCode(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getProducerCode(), "USCNIMA ");
        assertEquals(dtedFile.getDataSetIdentification().getProductSpecification(), "PRF89020B");
        assertEquals(dtedFile.getDataSetIdentification().getAmendmentChange(), "00");
        assertEquals(dtedFile.getDataSetIdentification().getProductSpecDate(), "0005");
        assertEquals(dtedFile.getDataSetIdentification().getVerticalDatum(), "E96");
        assertEquals(dtedFile.getDataSetIdentification().getHorizontalDatum(), "WGS84");
        assertEquals(
                dtedFile.getDataSetIdentification().getDigitizingCollectionSystem(), "SRTM      ");
        assertEquals(dtedFile.getDataSetIdentification().getCompilationDate(), "0002");
        assertEquals(
                dtedFile.getDataSetIdentification().getLatitudeOfOriginOfData(), -36.0, 0.00001);
        assertEquals(
                dtedFile.getDataSetIdentification().getLongitudeOfOriginOfData(), 149.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSwCornerLatitude(), -36.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSwCornerLongitude(), 149.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNwCornerLatitude(), -35.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNwCornerLongitude(), 149.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNeCornerLatitude(), -35.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNeCornerLongitude(), 150.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSeCornerLatitude(), -36.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSeCornerLongitude(), 150.0, 0.00001);
        assertEquals(
                dtedFile.getDataSetIdentification().getClockwiseOrientationAngle(), 0.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getLatitudeInterval(), 3.0, 0.1);
        assertEquals(dtedFile.getDataSetIdentification().getLongitudeInterval(), 3.0, 0.1);
        assertEquals(dtedFile.getDataSetIdentification().getNumLatitudeLines(), 1201);
        assertEquals(dtedFile.getDataSetIdentification().getNumLongitudeLines(), 1201);
        assertEquals(dtedFile.getDataSetIdentification().getPartialCellIndicator(), 0);
        assertEquals(dtedFile.getDataSetIdentification().getNimaPrivateUse().length(), 101);
        assertTrue(dtedFile.getDataSetIdentification().getNimaPrivateUse().trim().isEmpty());
        assertEquals(dtedFile.getDataSetIdentification().getCountryPrivateUse().length(), 100);
        assertTrue(dtedFile.getDataSetIdentification().getCountryPrivateUse().trim().isEmpty());
        assertEquals(dtedFile.getDataSetIdentification().getFreeTextComments().length(), 156);
        assertEquals(
                dtedFile.getDataSetIdentification().getFreeTextComments(),
                "NON-SRTM DTED1 USED FOR VOID FILL. By:BA. Date:060607.                                                                                                      ");
        assertEquals(
                dtedFile.getAccuracyDescription().getAbsoluteHorizontalAccuracy().intValue(), 7);
        assertEquals(dtedFile.getAccuracyDescription().getAbsoluteVerticalAccuracy().intValue(), 7);
        assertNull(dtedFile.getAccuracyDescription().getRelativeHorizontalAccuracy());
        assertEquals(dtedFile.getAccuracyDescription().getRelativeVerticalAccuracy().intValue(), 5);
        assertEquals(dtedFile.getAccuracyDescription().getNimaUseSection1(), "X");
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 0), 1150);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 1), 1152);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 2), 1156);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 1200), 592);
        assertEquals(dtedFile.getDataRecords().getDataAt(1200, 0), 243);
        assertEquals(dtedFile.getDataRecords().getDataAt(1200, 1), 235);
        assertEquals(dtedFile.getDataRecords().getDataAt(1200, 1200), 563);
    }

    @Test
    public void dt2() throws IOException, URISyntaxException {
        DtedParser parser = new DtedParser();
        URL resource = this.getClass().getClassLoader().getResource("n38_w078_1arc_v3.dt2");
        File file = Paths.get(resource.toURI()).toFile();
        String absolutePath = file.getAbsolutePath();
        DtedFile dtedFile = parser.parse(absolutePath);
        assertNotNull(dtedFile);
        assertNotNull(dtedFile.getUserHeaderLabel());
        assertEquals(dtedFile.getUserHeaderLabel().getLongitudeOfOrigin(), -78.0000, 0.0001);
        assertEquals(dtedFile.getUserHeaderLabel().getLatitudeOfOrigin(), 38.0000, 0.0001);
        assertEquals(dtedFile.getUserHeaderLabel().getLongitudeDataInterval(), 1.0, 0.1);
        assertEquals(dtedFile.getUserHeaderLabel().getLatitudeDataInterval(), 1.0, 0.1);
        assertEquals(dtedFile.getUserHeaderLabel().getAbsoluteVerticalAccuracy().intValue(), 5);
        assertEquals(
                dtedFile.getUserHeaderLabel().getSecurityClassification(),
                SecurityClassification.Unclassified);
        assertEquals(dtedFile.getUserHeaderLabel().getUniqueReference(), "G21 093     ");
        assertEquals(dtedFile.getUserHeaderLabel().getNumLongitudeLines(), 3601);
        assertEquals(dtedFile.getUserHeaderLabel().getNumLatitudePoints(), 3601);
        assertEquals(dtedFile.getUserHeaderLabel().getMultipleAccuracy(), 0);
        assertNotNull(dtedFile.getDataSetIdentification());
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityClassification(),
                SecurityClassification.Unclassified);
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityControlAndReleaseMarkings(), "OO");
        assertEquals(
                dtedFile.getDataSetIdentification().getSecurityHandlingDescription(),
                "PUBLIC SALE/NO RESTRICTION ");
        assertEquals(
                dtedFile.getDataSetIdentification().getSeriesDesignator(),
                NIMASeriesDesignator.DTED2);
        assertEquals(
                dtedFile.getDataSetIdentification().getUniqueReferenceNumber(), "G21 093        ");
        assertEquals(dtedFile.getDataSetIdentification().getDataEditionNumber(), 2);
        assertEquals(dtedFile.getDataSetIdentification().getMatchMergeVersion(), "A");
        assertEquals(dtedFile.getDataSetIdentification().getMaintenanceDate(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getMatchMergeDate(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getMaintenanceDescriptionCode(), "0000");
        assertEquals(dtedFile.getDataSetIdentification().getProducerCode(), "USCNIMA ");
        assertEquals(dtedFile.getDataSetIdentification().getProductSpecification(), "PRF89020B");
        assertEquals(dtedFile.getDataSetIdentification().getAmendmentChange(), "00");
        assertEquals(dtedFile.getDataSetIdentification().getProductSpecDate(), "0005");
        assertEquals(dtedFile.getDataSetIdentification().getVerticalDatum(), "E96");
        assertEquals(dtedFile.getDataSetIdentification().getHorizontalDatum(), "WGS84");
        assertEquals(
                dtedFile.getDataSetIdentification().getDigitizingCollectionSystem(), "SRTM      ");
        assertEquals(dtedFile.getDataSetIdentification().getCompilationDate(), "0002");
        assertEquals(
                dtedFile.getDataSetIdentification().getLatitudeOfOriginOfData(), 38.0, 0.00001);
        assertEquals(
                dtedFile.getDataSetIdentification().getLongitudeOfOriginOfData(), -78.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSwCornerLatitude(), 38.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSwCornerLongitude(), -78.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNwCornerLatitude(), 39.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNwCornerLongitude(), -78.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNeCornerLatitude(), 39.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getNeCornerLongitude(), -77.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSeCornerLatitude(), 38.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getSeCornerLongitude(), -77.0, 0.00001);
        assertEquals(
                dtedFile.getDataSetIdentification().getClockwiseOrientationAngle(), 0.0, 0.00001);
        assertEquals(dtedFile.getDataSetIdentification().getLatitudeInterval(), 1.0, 0.1);
        assertEquals(dtedFile.getDataSetIdentification().getLongitudeInterval(), 1.0, 0.1);
        assertEquals(dtedFile.getDataSetIdentification().getNumLatitudeLines(), 3601);
        assertEquals(dtedFile.getDataSetIdentification().getNumLongitudeLines(), 3601);
        assertEquals(dtedFile.getDataSetIdentification().getPartialCellIndicator(), 0);
        assertEquals(dtedFile.getDataSetIdentification().getNimaPrivateUse().length(), 101);
        assertTrue(dtedFile.getDataSetIdentification().getNimaPrivateUse().trim().isEmpty());
        assertEquals(dtedFile.getDataSetIdentification().getCountryPrivateUse().length(), 100);
        assertTrue(dtedFile.getDataSetIdentification().getCountryPrivateUse().trim().isEmpty());
        assertEquals(dtedFile.getDataSetIdentification().getFreeTextComments().length(), 156);
        assertEquals(
                dtedFile.getDataSetIdentification().getFreeTextComments(),
                "NON-SRTM DTED2 USED FOR VOID FILL. By:BA. Date:180406.                                                                                                      ");
        assertEquals(
                dtedFile.getAccuracyDescription().getAbsoluteHorizontalAccuracy().intValue(), 13);
        assertEquals(dtedFile.getAccuracyDescription().getAbsoluteVerticalAccuracy().intValue(), 5);
        assertNull(dtedFile.getAccuracyDescription().getRelativeHorizontalAccuracy());
        assertEquals(dtedFile.getAccuracyDescription().getRelativeVerticalAccuracy().intValue(), 4);
        assertEquals(dtedFile.getAccuracyDescription().getNimaUseSection1(), "X");
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 0), 132);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 1), 127);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 2), 122);
        assertEquals(dtedFile.getDataRecords().getDataAt(0, 3600), 409);
        assertEquals(dtedFile.getDataRecords().getDataAt(3600, 0), 48);
        assertEquals(dtedFile.getDataRecords().getDataAt(3600, 1), 52);
        assertEquals(dtedFile.getDataRecords().getDataAt(3600, 3600), 81);
    }
}
