package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class UnloadClass extends HPROFMainEntry {

    private int classSerialNo;

    public int getClassSerialNo() {
        return classSerialNo;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        classSerialNo = l.readInt();
    }
}
