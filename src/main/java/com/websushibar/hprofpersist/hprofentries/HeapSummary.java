package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class HeapSummary extends HPROFMainEntry {
    private int totLiveBytesSigned;
    private int totLiveInstancesSigned;
    private long totBytesAllocatedSigned;
    private long totInstancesAllocatedSigned;

    public int getTotLiveBytesSigned() {
        return totLiveBytesSigned;
    }

    public int getTotLiveInstancesSigned() {
        return totLiveInstancesSigned;
    }

    public long getTotBytesAllocatedSigned() {
        return totBytesAllocatedSigned;
    }

    public long getTotInstancesAllocatedSigned() {
        return totInstancesAllocatedSigned;
    }


    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        totLiveBytesSigned = l.readInt();
        totLiveInstancesSigned = l.readInt();
        totBytesAllocatedSigned = l.readInt();
        totInstancesAllocatedSigned = l.readInt();
    }
}
