package org.jmisb.elevation.dted;

/**
 * Data Set Identification.
 *
 * <p>See MIL-PRF-89020B 3.12.d for the structure definition.
 */
public class DataSetIdentification {
    private SecurityClassification securityClassification;
    private String securityControlAndReleaseMarkings;
    private String securityHandlingDescription;
    private NIMASeriesDesignator seriesDesignator;
    private String uniqueReferenceNumber;
    private int dataEditionNumber;
    private String matchMergeVersion;
    private String maintenanceDate;
    private String matchMergeDate;
    private String maintenanceDescriptionCode;
    private String producerCode;
    private String productSpecification;
    private String amendmentChange;
    private String productSpecDate;
    private String verticalDatum; // maybe an enum?
    private String horizontalDatum;
    private String digitizingCollectionSystem;
    private String compilationDate;
    private double latitudeOfOriginOfData;
    private double longitudeOfOriginOfData;
    private double swCornerLatitude;
    private double swCornerLongitude;
    private double nwCornerLatitude;
    private double nwCornerLongitude;
    private double neCornerLatitude;
    private double neCornerLongitude;
    private double seCornerLatitude;
    private double seCornerLongitude;
    private double clockwiseOrientationAngle;
    private double latitudeInterval;
    private double longitudeInterval;
    private int numLatitudeLines;
    private int numLongitudeLines;
    private int partialCellIndicator;
    private String nimaPrivateUse;
    private String countryPrivateUse;
    private String freeTextComments;

    public SecurityClassification getSecurityClassification() {
        return securityClassification;
    }

    public void setSecurityClassification(SecurityClassification securityClassification) {
        this.securityClassification = securityClassification;
    }

    public String getSecurityControlAndReleaseMarkings() {
        return securityControlAndReleaseMarkings;
    }

    public void setSecurityControlAndReleaseMarkings(String securityControlAndReleaseMarkings) {
        this.securityControlAndReleaseMarkings = securityControlAndReleaseMarkings;
    }

    public String getSecurityHandlingDescription() {
        return securityHandlingDescription;
    }

    public void setSecurityHandlingDescription(String securityHandlingDescription) {
        this.securityHandlingDescription = securityHandlingDescription;
    }

    public NIMASeriesDesignator getSeriesDesignator() {
        return seriesDesignator;
    }

    public void setSeriesDesignator(NIMASeriesDesignator seriesDesignator) {
        this.seriesDesignator = seriesDesignator;
    }

    public String getUniqueReferenceNumber() {
        return uniqueReferenceNumber;
    }

    public void setUniqueReferenceNumber(String uniqueReferenceNumber) {
        this.uniqueReferenceNumber = uniqueReferenceNumber;
    }

    public int getDataEditionNumber() {
        return dataEditionNumber;
    }

    public void setDataEditionNumber(int dataEditionNumber) {
        this.dataEditionNumber = dataEditionNumber;
    }

    public String getMatchMergeVersion() {
        return matchMergeVersion;
    }

    public void setMatchMergeVersion(String matchMergeVersion) {
        this.matchMergeVersion = matchMergeVersion;
    }

    public String getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }

    public String getMatchMergeDate() {
        return matchMergeDate;
    }

    public void setMatchMergeDate(String matchMergeDate) {
        this.matchMergeDate = matchMergeDate;
    }

    public String getMaintenanceDescriptionCode() {
        return maintenanceDescriptionCode;
    }

    public void setMaintenanceDescriptionCode(String maintenanceDescriptionCode) {
        this.maintenanceDescriptionCode = maintenanceDescriptionCode;
    }

    public String getProducerCode() {
        return producerCode;
    }

    public void setProducerCode(String producerCode) {
        this.producerCode = producerCode;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getAmendmentChange() {
        return amendmentChange;
    }

    public void setAmendmentChange(String amendmentChange) {
        this.amendmentChange = amendmentChange;
    }

    public String getProductSpecDate() {
        return productSpecDate;
    }

    public void setProductSpecDate(String productSpecDate) {
        this.productSpecDate = productSpecDate;
    }

    public String getVerticalDatum() {
        return verticalDatum;
    }

    public void setVerticalDatum(String verticalDatum) {
        this.verticalDatum = verticalDatum;
    }

    public String getHorizontalDatum() {
        return horizontalDatum;
    }

    public void setHorizontalDatum(String horizontalDatum) {
        this.horizontalDatum = horizontalDatum;
    }

    public String getDigitizingCollectionSystem() {
        return digitizingCollectionSystem;
    }

    public void setDigitizingCollectionSystem(String digitizingCollectionSystem) {
        this.digitizingCollectionSystem = digitizingCollectionSystem;
    }

    public String getCompilationDate() {
        return compilationDate;
    }

    public void setCompilationDate(String compilationDate) {
        this.compilationDate = compilationDate;
    }

    public double getLatitudeOfOriginOfData() {
        return latitudeOfOriginOfData;
    }

    public void setLatitudeOfOriginOfData(double latitudeOfOriginOfData) {
        this.latitudeOfOriginOfData = latitudeOfOriginOfData;
    }

    public double getLongitudeOfOriginOfData() {
        return longitudeOfOriginOfData;
    }

    public void setLongitudeOfOriginOfData(double longitudeOfOriginOfData) {
        this.longitudeOfOriginOfData = longitudeOfOriginOfData;
    }

    public double getSwCornerLatitude() {
        return swCornerLatitude;
    }

    public void setSwCornerLatitude(double swCornerLatitude) {
        this.swCornerLatitude = swCornerLatitude;
    }

    public double getSwCornerLongitude() {
        return swCornerLongitude;
    }

    public void setSwCornerLongitude(double swCornerLongitude) {
        this.swCornerLongitude = swCornerLongitude;
    }

    public double getNwCornerLatitude() {
        return nwCornerLatitude;
    }

    public void setNwCornerLatitude(double nwCornerLatitude) {
        this.nwCornerLatitude = nwCornerLatitude;
    }

    public double getNwCornerLongitude() {
        return nwCornerLongitude;
    }

    public void setNwCornerLongitude(double nwCornerLongitude) {
        this.nwCornerLongitude = nwCornerLongitude;
    }

    public double getNeCornerLatitude() {
        return neCornerLatitude;
    }

    public void setNeCornerLatitude(double neCornerLatitude) {
        this.neCornerLatitude = neCornerLatitude;
    }

    public double getNeCornerLongitude() {
        return neCornerLongitude;
    }

    public void setNeCornerLongitude(double neCornerLongitude) {
        this.neCornerLongitude = neCornerLongitude;
    }

    public double getSeCornerLatitude() {
        return seCornerLatitude;
    }

    public void setSeCornerLatitude(double seCornerLatitude) {
        this.seCornerLatitude = seCornerLatitude;
    }

    public double getSeCornerLongitude() {
        return seCornerLongitude;
    }

    public void setSeCornerLongitude(double seCornerLongitude) {
        this.seCornerLongitude = seCornerLongitude;
    }

    public double getClockwiseOrientationAngle() {
        return clockwiseOrientationAngle;
    }

    public void setClockwiseOrientationAngle(double clockwiseOrientationAngle) {
        this.clockwiseOrientationAngle = clockwiseOrientationAngle;
    }

    public double getLatitudeInterval() {
        return latitudeInterval;
    }

    public void setLatitudeInterval(double latitudeInterval) {
        this.latitudeInterval = latitudeInterval;
    }

    public double getLongitudeInterval() {
        return longitudeInterval;
    }

    public void setLongitudeInterval(double longitudeInterval) {
        this.longitudeInterval = longitudeInterval;
    }

    public int getNumLatitudeLines() {
        return numLatitudeLines;
    }

    public void setNumLatitudeLines(int numLatitudeLines) {
        this.numLatitudeLines = numLatitudeLines;
    }

    public int getNumLongitudeLines() {
        return numLongitudeLines;
    }

    public void setNumLongitudeLines(int numLongitudeLines) {
        this.numLongitudeLines = numLongitudeLines;
    }

    public int getPartialCellIndicator() {
        return partialCellIndicator;
    }

    public void setPartialCellIndicator(int partialCellIndicator) {
        this.partialCellIndicator = partialCellIndicator;
    }

    public String getNimaPrivateUse() {
        return nimaPrivateUse;
    }

    public void setNimaPrivateUse(String nimaPrivateUse) {
        this.nimaPrivateUse = nimaPrivateUse;
    }

    public String getCountryPrivateUse() {
        return countryPrivateUse;
    }

    public void setCountryPrivateUse(String countryPrivateUse) {
        this.countryPrivateUse = countryPrivateUse;
    }

    public String getFreeTextComments() {
        return freeTextComments;
    }

    public void setFreeTextComments(String freeTextComments) {
        this.freeTextComments = freeTextComments;
    }
}
