package org.jmisb.elevation.dted;

/**
 * DTED File.
 *
 * <p>This corresponds to a top level DTED file (e.g. with a .dt1 or .dt2 suffix).
 *
 * <p>MIL-PRF-89020B states that "A data file of DTED is a cell defined by latitudes and longitudes
 * of a geographic reference system. The terrain elevation information is expressed in meters. The
 * locations of elevation posts are defined by the intersections of rows and columns within a
 * matrix. The required matrix intervals, defined in terms of geographic arc seconds, vary according
 * to latitude. Each terrain elevation data file consists of the User Header Label(UHL), Data Set
 * Identification(DSI) Record, Accuracy Description Record(ACC) and the Data records.
 */
public class DtedFile {
    private UserHeaderLabel userHeaderLabel;
    private DataSetIdentification dataSetIdentification;
    private AccuracyDescription accuracyDescription;
    private DataRecords dataRecords;

    public UserHeaderLabel getUserHeaderLabel() {
        return userHeaderLabel;
    }

    public void setUserHeaderLabel(UserHeaderLabel userHeaderLabel) {
        this.userHeaderLabel = userHeaderLabel;
    }

    public DataSetIdentification getDataSetIdentification() {
        return dataSetIdentification;
    }

    public void setDataSetIdentification(DataSetIdentification dataSetIdentification) {
        this.dataSetIdentification = dataSetIdentification;
    }

    public AccuracyDescription getAccuracyDescription() {
        return accuracyDescription;
    }

    public void setAccuracyDescription(AccuracyDescription accuracyDescription) {
        this.accuracyDescription = accuracyDescription;
    }

    public DataRecords getDataRecords() {
        return dataRecords;
    }

    public void addDataRecords(DataRecords dataRecord) {
        this.dataRecords = dataRecord;
    }
}
