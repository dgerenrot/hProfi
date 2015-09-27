package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class AllocSites extends HPROFMainEntry {

    public static final short INCREMENTAL_VS_COMPLETE = 0x1;
    public static final short SORT_BY_ALLOC_VS_LINE = 0x2;
    public static final short FORCE_GC = 0x4;

    private short bitMaskFlags;
    private int cutoffRatio;
    private int totLiveBytes;
    private int totLiveInstances;
    private long totBytesAllocated;
    private long totInstancesAllocated;

    private int nSites;

    private AllocSite[] allocSites;

    public short getBitMaskFlags() {
        return bitMaskFlags;
    }

    public int getCutoffRatio() {
        return cutoffRatio;
    }

    public int getTotLiveBytes() {
        return totLiveBytes;
    }

    public int getTotLiveInstances() {
        return totLiveInstances;
    }

    public long getTotBytesAllocated() {
        return totBytesAllocated;
    }

    public long getTotInstancesAllocated() {
        return totInstancesAllocated;
    }


    public int getnSites() {
        return nSites;
    }

    public AllocSite[] getAllocSites() {
        return allocSites;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        bitMaskFlags = l.readShort();
        cutoffRatio = l.readInt();
        totLiveBytes = l.readInt();
        totLiveInstances = l.readInt();
        totBytesAllocated = l.readLong();
        totInstancesAllocated = l.readLong();

        nSites = l.readInt();
        allocSites = new AllocSite[nSites];
        for (int i = 0; i < nSites; i++) {
            allocSites[i] = new AllocSite();
            allocSites[i].initStartByte(l);
            allocSites[i].readBody(l);
        }
    }

    public static class AllocSite extends HPROFEntity {

        private byte arrayIndicator;
        private int classSerialNo;
        private int stackTraceSerialNo;
        private int nLiveBytes;
        private int nLiveInstances;
        private int bytesAllocated;
        private int instancesAllocated;

        public byte getArrayIndicator() {
            return arrayIndicator;
        }

        public int getClassSerialNo() {
            return classSerialNo;
        }

        public int getStackTraceSerialNo() {
            return stackTraceSerialNo;
        }

        public int getnLiveBytes() {
            return nLiveBytes;
        }

        public int getnLiveInstances() {
            return nLiveInstances;
        }

        public int getBytesAllocated() {
            return bytesAllocated;
        }

        public int getInstancesAllocated() {
            return instancesAllocated;
        }

        @Override
        public void readBody(DataInputStreamWrapper l) throws IOException {
            arrayIndicator = l.readByte();
            classSerialNo = l.readInt();
            stackTraceSerialNo = l.readInt();
            nLiveBytes = l.readInt();
            nLiveInstances = l.readInt();
            bytesAllocated = l.readInt();
            instancesAllocated = l.readInt();
        }

        @Override
        public void initStartByte(DataInputStreamWrapper l) {
            startsAtByte = l.getBytesRead();
        }
    }
}
