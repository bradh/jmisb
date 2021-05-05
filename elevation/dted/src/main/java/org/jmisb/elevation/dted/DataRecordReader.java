package org.jmisb.elevation.dted;

import java.io.FileInputStream;
import java.io.IOException;

/** @author bradh */
public class DataRecordReader {
    private final FileInputStream fis;
    private int checksum;

    DataRecordReader(FileInputStream fileInputStream) {
        this.fis = fileInputStream;
    }

    public int readFixedBinary(int numBytes) throws IOException {
        int firstByte = readByte();
        boolean isNegative = ((firstByte & 0x80) == 0x80);
        int result = (firstByte & 0x7f);
        for (int i = 1; i < numBytes; ++i) {
            int thisByte = readByte();
            result = (result << 8) + thisByte;
        }
        if (isNegative) {
            result *= -1;
        }
        return result;
    }

    public int readByte() throws IOException {
        int byteVal = fis.read();
        checksum += byteVal;
        return byteVal;
    }

    public void checkChecksum() throws IOException {
        int checksumRead = 0;
        for (int i = 0; i < 4; i++) {
            checksumRead = checksumRead << 8;
            checksumRead += fis.read();
        }
        if (checksumRead != checksum) {
            throw new IOException("bad checksum");
        }
    }
}
