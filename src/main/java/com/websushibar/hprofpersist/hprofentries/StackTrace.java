package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class StackTrace extends HPROFMainEntry {

    private int stackTraceSerialNo;
    private int threadSerialNo;
    private int nFrames;
    private IDField[] stackFrameIds;

    public int getStackTraceSerialNo() {
        return stackTraceSerialNo;
    }

    public int getThreadSerialNo() {
        return threadSerialNo;
    }

    public int getnFrames() {
        return nFrames;
    }

    public IDField[] getStackFrameIds() {
        return stackFrameIds;
    }


    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        stackTraceSerialNo = l.readInt();
        threadSerialNo = l.readInt();
        nFrames = l.readInt();

        stackFrameIds = new IDField[nFrames];

        for (int i = 0; i < nFrames; i++) {
            stackFrameIds[i] = l.readId();
        }
    }
}
