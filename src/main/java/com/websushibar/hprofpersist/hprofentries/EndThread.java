package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class EndThread extends HPROFMainEntry {
    private int threadSerialNo;

    public int getThreadSerialNo() {
        return threadSerialNo;
    }


    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        threadSerialNo = l.readInt();
    }
}
