package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class RootThreadObject  extends AbstractRootSubtagWithThread{

    private int stackTraceSerialNo;

    public int getStackTraceSerialNo() {
        return stackTraceSerialNo;
    }


    @Override
    public void readBody(DataInputStreamWrapper l)
            throws IOException {
        super.readBody(l);
        stackTraceSerialNo = l.readInt();
    }
}
