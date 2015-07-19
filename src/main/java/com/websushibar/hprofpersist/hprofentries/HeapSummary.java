package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;

@Document(collection = "hprofHeapSummary")
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
