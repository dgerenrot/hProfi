package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public abstract class HPROFEntity {
    protected  long startsAtByte;

    public void readSelf(DataInputStreamWrapper l)throws IOException {
    }

    public abstract void readBody(DataInputStreamWrapper l)
            throws IOException;

    public abstract void initStartByte(DataInputStreamWrapper l);

    public long getStartsAtByte() {
        return startsAtByte;
    }
}
