package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;


public class ControlSettings extends HPROFMainEntry {

    public final int ALLOC_TRACES_ON = 0x1;
    public final int CPU_SAMPLING_ON = 0x2;

    private int bitMaskFlags;
    private short stackTraceDepth;

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        bitMaskFlags = l.readInt();
        stackTraceDepth = l.readShort();
    }

    public int getBitMaskFlags() {
        return bitMaskFlags;
    }

    public short getStackTraceDepth() {
        return stackTraceDepth;
    }
}
