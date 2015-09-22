package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public abstract class HPROFEntity {
    public void readSelf(DataInputStreamWrapper l)throws IOException {
    };

    public abstract void readBody(DataInputStreamWrapper l)
            throws IOException;
}
