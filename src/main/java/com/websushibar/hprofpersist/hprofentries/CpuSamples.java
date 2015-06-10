package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class CpuSamples extends HPROFMainEntry {

    private int totSamples;
    private int nTraces;
    List<Trace> traces = new LinkedList<Trace>();

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        totSamples = l.readInt();
        nTraces = l.readInt();

        for (int i = 0; i < nTraces; i++) {

            // Apparently, these should total the totSamples above
            int nSamples = l.readInt();
            int stackTraceSerialNo = l.readInt();

            traces.add(new Trace(nSamples, stackTraceSerialNo));
        }
    }

    public int getTotSamples() {
        return totSamples;
    }

    public static class Trace implements Serializable {
        private int nSamples;

        private int stackTraceSerialNo;

        public Trace(int nSamples, int stackTraceSerialNo) {
            this.nSamples = nSamples;
            this.stackTraceSerialNo = stackTraceSerialNo;
        }

        public int getnSamples() {
            return nSamples;
        }

        public int getStackTraceSerialNo() {
            return stackTraceSerialNo;
        }
    }
}
