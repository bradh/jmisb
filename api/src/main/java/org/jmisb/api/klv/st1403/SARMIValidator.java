package org.jmisb.api.klv.st1403;

import java.util.ArrayList;
import java.util.List;
import org.jmisb.api.klv.st0102.SecurityMetadataKey;
import org.jmisb.api.klv.st0102.localset.SecurityMetadataLocalSet;
import org.jmisb.api.klv.st0601.IUasDatalinkValue;
import org.jmisb.api.klv.st0601.NestedSARMILocalSet;
import org.jmisb.api.klv.st0601.NestedSecurityMetadata;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeight;
import org.jmisb.api.klv.st0601.SensorEllipsoidHeightExtended;
import org.jmisb.api.klv.st0601.UasDatalinkMessage;
import org.jmisb.api.klv.st0601.UasDatalinkTag;
import org.jmisb.api.klv.st1206.SARMILocalSet;
import org.jmisb.api.klv.st1206.SARMIMetadataKey;

/**
 * SAR Motion Imagery Validator.
 *
 * <p>This class provides validation for Synthetic Aperture Radar Motion Imagery (SARMI) metadata
 * against the requirements of MISB ST 1403 "SARMI Threshold Metadata Sets".
 *
 * <p>ST 1403 describes two conformance targets - one that is for sequential display of SAR imagery,
 * and a second that is for sequential display of SAR coherent change products. This class provides
 * validation against those conformance targets.
 */
public class SARMIValidator {

    private SARMIValidator() {}

    /** Required Metadata Items from ST0601, from ST 1403.2 Table 1. */
    private static final UasDatalinkTag[] REQUIRED_ST0601_ITEMS =
            new UasDatalinkTag[] {
                /* No Checksum - that will get added / removed on serialisation */
                UasDatalinkTag.PrecisionTimeStamp,
                UasDatalinkTag.MissionId,
                UasDatalinkTag.PlatformDesignation,
                UasDatalinkTag.ImageSourceSensor,
                UasDatalinkTag.SensorLatitude,
                UasDatalinkTag.SensorLongitude,
                UasDatalinkTag.SlantRange,
                UasDatalinkTag.FrameCenterLatitude,
                UasDatalinkTag.FrameCenterLongitude,
                UasDatalinkTag.CornerLatPt1,
                UasDatalinkTag.CornerLonPt1,
                UasDatalinkTag.CornerLatPt2,
                UasDatalinkTag.CornerLonPt2,
                UasDatalinkTag.CornerLatPt3,
                UasDatalinkTag.CornerLonPt3,
                UasDatalinkTag.CornerLatPt4,
                UasDatalinkTag.CornerLonPt4,
                UasDatalinkTag.SecurityLocalMetadataSet,
                UasDatalinkTag.UasLdsVersionNumber,
                /* Ellipsoid Height | Ellipsoid Height Extended is special case */
                UasDatalinkTag.FrameCenterHae,
                UasDatalinkTag.MiisCoreIdentifier,
                UasDatalinkTag.SarMotionImageryMetadata
            };

    private static final SecurityMetadataKey[] REQUIRED_ST0102_ITEMS =
            new SecurityMetadataKey[] {
                SecurityMetadataKey.SecurityClassification,
                SecurityMetadataKey.CcCodingMethod,
                SecurityMetadataKey.ClassifyingCountry,
                // TODO: what to put in for this?
                // SecurityMetadataKey.SciShiInfo,
                // SecurityMetadataKey.Caveats,
                // SecurityMetadataKey.ReleasingInstructions,
                SecurityMetadataKey.OcCodingMethod,
                SecurityMetadataKey.ObjectCountryCodes,
                SecurityMetadataKey.Version
            };

    private static final SARMIMetadataKey[] REQUIRED_ST1206_ITEMS =
            new SARMIMetadataKey[] {
                SARMIMetadataKey.GrazingAngle,
                SARMIMetadataKey.GroundPlaneSquintAngle,
                SARMIMetadataKey.LookDirection,
                SARMIMetadataKey.ImagePlane,
                SARMIMetadataKey.RangeResolution,
                SARMIMetadataKey.RangeImagePlanePixelSize,
                SARMIMetadataKey.CrossRangeImagePlanePixelSize,
                SARMIMetadataKey.ImageRows,
                SARMIMetadataKey.ImageColumns,
                SARMIMetadataKey.RangeDirectionAngleRelativeToTrueNorth,
                SARMIMetadataKey.TrueNorthDirectionRelativeToTopImageEdge,
                SARMIMetadataKey.RangeLayoverAngleRelativeToTrueNorth,
                SARMIMetadataKey.DocumentVersion
            };

    /**
     * Validate that the given local set meets the requirements of ST 1403.2 for SAR Motion Imagery.
     *
     * <p>This only checks the requirements for the sequential display of SAR Imagery as SARMI data.
     *
     * @param localSet the local set to check
     * @return ValidationResult structure containing the validation results.
     */
    public static ValidationResults validateSARImageryMetadata(UasDatalinkMessage localSet) {
        ValidationResults result = new ValidationResults();
        result.addResults(validateCoreST0601Items(localSet));

        // TODO: null checks
        NestedSecurityMetadata nestedSecurityMetadata =
                (NestedSecurityMetadata) localSet.getField(UasDatalinkTag.SecurityLocalMetadataSet);
        SecurityMetadataLocalSet securityLocalSet = nestedSecurityMetadata.getLocalSet();
        result.addResults(validateSecurityItems(securityLocalSet));

        // TODO: null checks
        NestedSARMILocalSet nestedSARMILocalSet =
                (NestedSARMILocalSet) localSet.getField(UasDatalinkTag.SarMotionImageryMetadata);
        SARMILocalSet sarmiLocalSet = nestedSARMILocalSet.getSARMI();
        result.addResults(validateSARMIItems(sarmiLocalSet));
        return result;
    }

    private static List<ValidationResult> validateCoreST0601Items(UasDatalinkMessage localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (UasDatalinkTag tag : REQUIRED_ST0601_ITEMS) {
            ValidationResult result = validateHasValidItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        results.addAll(validateSensorEllipsoidHeightCombination(localSet));
        return results;
    }

    private static List<ValidationResult> validateSensorEllipsoidHeightCombination(
            UasDatalinkMessage localSet) {
        List<ValidationResult> validationResults = new ArrayList<>();
        boolean hasSensorEllipsoidHeight =
                localSet.getTags().contains(UasDatalinkTag.SensorEllipsoidHeight);
        boolean hasSensorEllipsoidHeightExtended =
                localSet.getTags().contains(UasDatalinkTag.SensorEllipsoidHeightExtended);
        // Needs to be XOR
        if (hasSensorEllipsoidHeight && hasSensorEllipsoidHeightExtended) {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Only one of Sensor Ellipsoid Height (75) or Sensor Ellipsoid Height Extended (104) is allowed, but both were found.");
            validationResults.add(result);
        } else if (hasSensorEllipsoidHeight) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Required Ellipsoid Height (75) (but not Sensor Ellipsoid Height Extended) was found.");
            validationResults.add(result);
            IUasDatalinkValue v = localSet.getField(UasDatalinkTag.SensorEllipsoidHeight);
            if (v == null) {
                validationResults.add(getInvalidResultNullValue("Ellipsoid Height"));
            } else {
                if (v instanceof SensorEllipsoidHeight) {
                    ValidationResult typeResult = new ValidationResult(Validity.Conforms);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription("Sensor Ellipsoid Height was of the correct type.");
                    validationResults.add(typeResult);
                } else {
                    ValidationResult typeResult = new ValidationResult(Validity.DoesNotConform);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            String.format(
                                    "Sensor Ellipsoid Height was not of the correct type. Found %s",
                                    v.getClass().toString()));
                    validationResults.add(typeResult);
                }
            }
        } else if (hasSensorEllipsoidHeightExtended) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setTraceability("ST 1403-04");
            result.setDescription(
                    "Required Ellipsoid Height Extended (104) (but not Sensor Ellipsoid Height) was found.");
            validationResults.add(result);
            IUasDatalinkValue v = localSet.getField(UasDatalinkTag.SensorEllipsoidHeightExtended);
            if (v == null) {
                validationResults.add(getInvalidResultNullValue("Ellipsoid Height Extended"));
            } else {
                if (v instanceof SensorEllipsoidHeightExtended) {
                    ValidationResult typeResult = new ValidationResult(Validity.Conforms);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            "Sensor Ellipsoid Height Extended was of the correct type.");
                    validationResults.add(typeResult);
                } else {
                    ValidationResult typeResult = new ValidationResult(Validity.DoesNotConform);
                    typeResult.setTraceability("ST 1403-03");
                    typeResult.setDescription(
                            String.format(
                                    "Sensor Ellipsoid Height Extended was not of the correct type. Found %s",
                                    v.getClass().toString()));
                    validationResults.add(typeResult);
                }
            }
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setTraceability("ST 1403-03");
            result.setDescription(
                    "Neither Sensor Ellipsoid Height (75) or Sensor Ellipsoid Height Extended (104) was found.");
            validationResults.add(result);
        }
        return validationResults;
    }

    private static ValidationResult getInvalidResultNullValue(String tagLabel) {
        ValidationResult r = new ValidationResult(Validity.DoesNotConform);
        r.setTraceability("ST 1403.2-07");
        r.setDescription(String.format("Required %s tag was found, but has null value.", tagLabel));
        return r;
    }

    private static List<ValidationResult> validateSecurityItems(SecurityMetadataLocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (SecurityMetadataKey tag : REQUIRED_ST0102_ITEMS) {
            ValidationResult result = validateHasSecurityItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        return results;
    }

    private static List<ValidationResult> validateSARMIItems(SARMILocalSet localSet) {
        List<ValidationResult> results = new ArrayList<>();
        for (SARMIMetadataKey tag : REQUIRED_ST1206_ITEMS) {
            ValidationResult result = validateHasSARMIItem(localSet, tag);
            result.setTraceability("ST 1403-03");
            results.add(result);
        }
        return results;
    }

    private static ValidationResult validateHasValidItem(
            UasDatalinkMessage localSet, UasDatalinkTag uasDatalinkTag) {

        if (localSet.getIdentifiers().contains(uasDatalinkTag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST0601 item %s (%d) was found.",
                            uasDatalinkTag.toString(), uasDatalinkTag.getCode()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST0601 item %s (%d) was not found.",
                            uasDatalinkTag.toString(), uasDatalinkTag.getCode()));
            return result;
        }
    }

    private static ValidationResult validateHasSecurityItem(
            SecurityMetadataLocalSet localSet, SecurityMetadataKey tag) {

        if (localSet.getIdentifiers().contains(tag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST0102 item %s (%d) was found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST0102 item %s (%d) was not found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        }
    }

    private static ValidationResult validateHasSARMIItem(
            SARMILocalSet localSet, SARMIMetadataKey tag) {

        if (localSet.getIdentifiers().contains(tag)) {
            ValidationResult result = new ValidationResult(Validity.Conforms);
            result.setDescription(
                    String.format(
                            "Required ST1206 item %s (%d) was found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        } else {
            ValidationResult result = new ValidationResult(Validity.DoesNotConform);
            result.setDescription(
                    String.format(
                            "Required ST1206 item %s (%d) was not found.",
                            tag.toString(), tag.getIdentifier()));
            return result;
        }
    }
}
