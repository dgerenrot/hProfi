package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class AbstractRootSubtagWithThread extends AbstractRootSubtag {

    private int threadSerialNo;

    public int getThreadSerialNo() {
        return threadSerialNo;
    }

    @Override
    public void readBody(DataInputStreamWrapper l)
            throws IOException {
        super.readBody(l);
        threadSerialNo = l.readInt();
        
    }

}
