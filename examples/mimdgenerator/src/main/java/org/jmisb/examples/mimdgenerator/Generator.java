package org.jmisb.examples.mimdgenerator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.jmisb.api.common.KlvParseException;
import org.jmisb.api.klv.st1204.CoreIdentifier;
import org.jmisb.api.klv.st1902.IMimdMetadataValue;
import org.jmisb.api.klv.st1902.MimdId;
import org.jmisb.api.klv.st1903.ListOfSecurity;
import org.jmisb.api.klv.st1903.ListOfTimer;
import org.jmisb.api.klv.st1903.MIMD;
import org.jmisb.api.klv.st1903.MIMDMetadataKey;
import org.jmisb.api.klv.st1903.MIMD_Version;
import org.jmisb.api.klv.st1903.Security;
import org.jmisb.api.klv.st1903.SecurityMetadataKey;
import org.jmisb.api.klv.st1903.Security_Classification;
import org.jmisb.api.klv.st1903.Security_ClassifyingMethod;
import org.jmisb.api.klv.st1903.TimeTransferMethod;
import org.jmisb.api.klv.st1903.Timer;
import org.jmisb.api.klv.st1903.TimerMetadataKey;
import org.jmisb.api.klv.st1903.Timer_NanoPrecisionTimestamp;
import org.jmisb.api.klv.st1903.Timer_UtcLeapSeconds;
import org.jmisb.api.klv.st1905.ListOfPlatform;
import org.jmisb.api.klv.st1905.Platform;
import org.jmisb.api.klv.st1905.PlatformMetadataKey;
import org.jmisb.api.klv.st1905.PlatformType;
import org.jmisb.api.klv.st1905.Platform_Identity;
import org.jmisb.api.klv.st1905.Platform_Name;
import org.jmisb.api.klv.st1906.AbsEnu;
import org.jmisb.api.klv.st1906.AbsEnuMetadataKey;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutEast;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutNorth;
import org.jmisb.api.klv.st1906.AbsEnu_RotAboutUp;
import org.jmisb.api.klv.st1906.AbsGeodetic;
import org.jmisb.api.klv.st1906.AbsGeodeticMetadataKey;
import org.jmisb.api.klv.st1906.AbsGeodetic_Hae;
import org.jmisb.api.klv.st1906.AbsGeodetic_Lat;
import org.jmisb.api.klv.st1906.AbsGeodetic_Lon;
import org.jmisb.api.klv.st1906.ListOfStage;
import org.jmisb.api.klv.st1906.Orientation;
import org.jmisb.api.klv.st1906.OrientationMetadataKey;
import org.jmisb.api.klv.st1906.Position;
import org.jmisb.api.klv.st1906.PositionMetadataKey;
import org.jmisb.api.klv.st1906.Position_Country;
import org.jmisb.api.klv.st1906.Stage;
import org.jmisb.api.klv.st1906.StageMetadataKey;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensor;
import org.jmisb.api.klv.st1907.GeoIntelligenceSensorMetadataKey;
import org.jmisb.api.klv.st1907.ListOfGeoIntelligenceSensor;
import org.jmisb.api.klv.st1907.ListOfPayload;
import org.jmisb.api.klv.st1907.Payload;
import org.jmisb.api.klv.st1907.PayloadMetadataKey;
import org.jmisb.api.klv.st1908.FieldOfView;
import org.jmisb.api.klv.st1908.FieldOfViewMetadataKey;
import org.jmisb.api.klv.st1908.FieldOfView_Horizontal;
import org.jmisb.api.klv.st1908.FieldOfView_Vertical;
import org.jmisb.api.klv.st1908.ImagerSystem;
import org.jmisb.api.klv.st1908.ImagerSystemMetadataKey;
import org.jmisb.api.klv.st1908.ImagerSystem_Name;
import org.jmisb.api.klv.st1908.MIIS;
import org.jmisb.api.klv.st1908.MIISMetadataKey;
import org.jmisb.api.klv.st1908.MinorCoreId;
import org.jmisb.api.klv.st1908.MinorCoreIdMetadataKey;
import org.jmisb.api.klv.st1908.MinorCoreId_Uuid;
import org.jmisb.api.video.IVideoFileOutput;
import org.jmisb.api.video.KlvFormat;
import org.jmisb.api.video.MetadataFrame;
import org.jmisb.api.video.VideoFileOutput;
import org.jmisb.api.video.VideoFrame;
import org.jmisb.api.video.VideoOutputOptions;
import org.jmisb.core.klv.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Generator {

    private final int width = 1280;
    private final int height = 960;
    private final int bitRate = 500_000;
    private final int gopSize = 30;
    private final double frameRate = 15.0;
    private final double frameDuration = 1.0 / frameRate;
    private final int duration = 90;
    private final String filename = "mimd.mpeg";

    private static final Logger LOG = LoggerFactory.getLogger(Generator.class);
    private final UUID coreIdentifierUUID;

    public Generator() {
        coreIdentifierUUID = UUID.randomUUID();
    }

    public void generate() throws KlvParseException {

        CoreIdentifier coreIdentifier = new CoreIdentifier();
        coreIdentifier.setMinorUUID(UUID.randomUUID());
        coreIdentifier.setVersion(1);

        VideoOutputOptions options =
                new VideoOutputOptions(
                        width, height, bitRate, frameRate, gopSize, KlvFormat.Asynchronous);
        try (IVideoFileOutput output = new VideoFileOutput(options)) {
            output.open(filename);

            // Write some frames
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
            try {
                image = ImageIO.read(new File("test1280.jpg"));
            } catch (IOException e) {
                // TODO: log
            }

            final long numFrames = duration * Math.round(frameRate);
            double pts = 1000.0 * System.currentTimeMillis(); // Close enough for this.
            for (long i = 0; i < numFrames; ++i) {
                SortedMap<MIMDMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
                values.put(MIMDMetadataKey.version, new MIMD_Version(1));
                values.put(MIMDMetadataKey.timers, this.getTimers((long) pts * 1000));
                values.put(MIMDMetadataKey.securityOptions, this.getSecurityOptions());
                values.put(MIMDMetadataKey.security, new MimdId(0, 1));
                values.put(MIMDMetadataKey.compositeProductSecurity, new MimdId(0, 1));
                values.put(MIMDMetadataKey.compositeMotionImagerySecurity, new MimdId(0, 1));
                values.put(MIMDMetadataKey.compositeMetadataSecurity, new MimdId(0, 1));
                values.put(MIMDMetadataKey.platforms, this.getPlatforms());
                MIMD message = new MIMD(values);
                output.addVideoFrame(new VideoFrame(image, pts * 1.0e-6));
                output.addMetadataFrame(new MetadataFrame(message, pts));
                pts += frameDuration * 1.0e6;
            }

        } catch (IOException e) {
            LOG.error("Failed to write file", e);
        }
    }

    private ListOfTimer getTimers(long nanos) throws KlvParseException {
        List<Timer> timerList = new ArrayList<>();
        timerList.add(this.getTimer(nanos));
        ListOfTimer timers = new ListOfTimer(timerList, "Timers");
        return timers;
    }

    private Timer getTimer(long nanos) throws KlvParseException {
        SortedMap<TimerMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(
                TimerMetadataKey.nanoPrecisionTimestamp, new Timer_NanoPrecisionTimestamp(nanos));
        values.put(TimerMetadataKey.utcLeapSeconds, new Timer_UtcLeapSeconds(37));
        values.put(TimerMetadataKey.timeTransferMethod, TimeTransferMethod.NTP_V3_3);
        Timer timer = new Timer(values);
        return timer;
    }

    private ListOfSecurity getSecurityOptions() throws KlvParseException {
        List<Security> securityList = new ArrayList<>();
        securityList.add(this.getSecurityUnclas());
        securityList.add(this.getSecurityFOUO());
        ListOfSecurity securityOptions = new ListOfSecurity(securityList, "SecurityOptions");
        return securityOptions;
    }

    private Security getSecurityUnclas() throws KlvParseException {
        SortedMap<SecurityMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.mimdId, new MimdId(0, 1));
        values.put(SecurityMetadataKey.classifyingMethod, new Security_ClassifyingMethod("US-1"));
        values.put(
                SecurityMetadataKey.classification,
                new Security_Classification("UNCLASSIFIED//REL TO USA, AUS, CAN, GBR"));
        Security security = new Security(values);
        return security;
    }

    private Security getSecurityFOUO() throws KlvParseException {
        SortedMap<SecurityMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(SecurityMetadataKey.mimdId, new MimdId(1, 1));
        values.put(SecurityMetadataKey.classifyingMethod, new Security_ClassifyingMethod("US-1"));
        values.put(
                SecurityMetadataKey.classification,
                new Security_Classification(
                        "UNCLASSIFIED//FOR OFFICIAL USE ONLY//REL TO USA, AUS, CAN, GBR"));
        Security security = new Security(values);
        return security;
    }

    private ListOfPlatform getPlatforms() throws KlvParseException {
        List<Platform> platformList = new ArrayList<>();
        platformList.add(this.getPlatform());
        ListOfPlatform platforms = new ListOfPlatform(platformList, "Platforms");
        return platforms;
    }

    private Platform getPlatform() throws KlvParseException {
        SortedMap<PlatformMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(PlatformMetadataKey.name, new Platform_Name("Test System"));
        values.put(PlatformMetadataKey.identity, new Platform_Identity("jMISB Test 1"));
        values.put(PlatformMetadataKey.type, PlatformType.Pole);
        values.put(PlatformMetadataKey.stages, this.getStages());
        values.put(PlatformMetadataKey.payloads, this.getPayloads());
        Platform platform = new Platform(values);
        return platform;
    }

    private ListOfStage getStages() throws KlvParseException {
        List<Stage> stageList = new ArrayList<>();
        stageList.add(this.getStage());
        ListOfStage stages = new ListOfStage(stageList, "Stages");
        return stages;
    }

    private Stage getStage() throws KlvParseException {
        SortedMap<StageMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(StageMetadataKey.position, this.getPosition());
        values.put(StageMetadataKey.orientation, this.getOrientation());
        Stage stage = new Stage(values);
        return stage;
    }

    private Position getPosition() throws KlvParseException {
        SortedMap<PositionMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(PositionMetadataKey.absGeodetic, this.getGeodeticPosition());
        values.put(PositionMetadataKey.country, new Position_Country("ge:ISO1:3:VII-13:AUS"));
        Position position = new Position(values);
        return position;
    }

    private Orientation getOrientation() throws KlvParseException {
        SortedMap<OrientationMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(OrientationMetadataKey.absEnu, this.getAbsEnu());
        Orientation orientation = new Orientation(values);
        return orientation;
    }

    private AbsEnu getAbsEnu() throws KlvParseException {
        SortedMap<AbsEnuMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(AbsEnuMetadataKey.rotAboutEast, new AbsEnu_RotAboutEast(0.0));
        values.put(AbsEnuMetadataKey.rotAboutNorth, new AbsEnu_RotAboutNorth(0.0));
        values.put(AbsEnuMetadataKey.rotAboutUp, new AbsEnu_RotAboutUp(45.0 * Math.PI / 180.0));
        AbsEnu absEnu = new AbsEnu(values);
        return absEnu;
    }

    private AbsGeodetic getGeodeticPosition() throws KlvParseException {
        SortedMap<AbsGeodeticMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(AbsGeodeticMetadataKey.lat, new AbsGeodetic_Lat(-35.35349 * Math.PI / 180.0));
        values.put(AbsGeodeticMetadataKey.lon, new AbsGeodetic_Lon(149.08932 * Math.PI / 180.0));
        values.put(AbsGeodeticMetadataKey.hae, new AbsGeodetic_Hae(642.1));
        AbsGeodetic geodeticPosition = new AbsGeodetic(values);
        return geodeticPosition;
    }

    private ListOfPayload getPayloads() throws KlvParseException {
        List<Payload> payloadList = new ArrayList<>();
        payloadList.add(this.getPayload());
        ListOfPayload payloads = new ListOfPayload(payloadList, "Payloads");
        return payloads;
    }

    private Payload getPayload() throws KlvParseException {
        SortedMap<PayloadMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(PayloadMetadataKey.geoIntelligenceSensors, getGeoIntelligenceSensors());
        Payload payload = new Payload(values);
        return payload;
    }

    private ListOfGeoIntelligenceSensor getGeoIntelligenceSensors() throws KlvParseException {
        List<GeoIntelligenceSensor> sensorList = new ArrayList<>();
        sensorList.add(this.getGeoIntelligenceSensor());
        ListOfGeoIntelligenceSensor sensors =
                new ListOfGeoIntelligenceSensor(sensorList, "GeoIntelligenceSensors");
        return sensors;
    }

    private GeoIntelligenceSensor getGeoIntelligenceSensor() throws KlvParseException {
        SortedMap<GeoIntelligenceSensorMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(GeoIntelligenceSensorMetadataKey.imagerSystem, getImagerSystem());
        GeoIntelligenceSensor sensor = new GeoIntelligenceSensor(values);
        return sensor;
    }

    private ImagerSystem getImagerSystem() throws KlvParseException {
        SortedMap<ImagerSystemMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(ImagerSystemMetadataKey.name, new ImagerSystem_Name("EO Fixed"));
        values.put(ImagerSystemMetadataKey.fieldOfView, getFieldOfView());
        values.put(ImagerSystemMetadataKey.miis, getMiis());
        ImagerSystem imagerSystem = new ImagerSystem(values);
        return imagerSystem;
    }

    private FieldOfView getFieldOfView() throws KlvParseException {
        SortedMap<FieldOfViewMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(
                FieldOfViewMetadataKey.horizontal,
                new FieldOfView_Horizontal(144.571298 * Math.PI / 180.0));
        values.put(
                FieldOfViewMetadataKey.vertical,
                new FieldOfView_Vertical(152.643626 * Math.PI / 180.0));
        FieldOfView fieldOfView = new FieldOfView(values);
        return fieldOfView;
    }

    private MIIS getMiis() throws KlvParseException {
        SortedMap<MIISMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        values.put(MIISMetadataKey.minorCoreId, getMinorCoreId());
        MIIS miis = new MIIS(values);
        return miis;
    }

    private MinorCoreId getMinorCoreId() throws KlvParseException {
        SortedMap<MinorCoreIdMetadataKey, IMimdMetadataValue> values = new TreeMap<>();
        byte[] coreIdentifierAsBytes = UuidUtils.uuidToArray(coreIdentifierUUID);
        long[] data = new long[coreIdentifierAsBytes.length];
        for (int i = 0; i < coreIdentifierAsBytes.length; ++i) {
            data[i] = (coreIdentifierAsBytes[i] & 0xFF);
        }
        MinorCoreId_Uuid uuid = new MinorCoreId_Uuid(data);
        values.put(MinorCoreIdMetadataKey.uuid, uuid);
        MinorCoreId minorCoreId = new MinorCoreId(values);
        return minorCoreId;
    }
}
