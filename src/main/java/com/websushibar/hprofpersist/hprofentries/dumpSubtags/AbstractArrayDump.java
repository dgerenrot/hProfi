package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class AbstractArrayDump extends AbstractObjDump {

    private int nElementsSigned;

    public void readBody(DataInputStreamWrapper l)
        throws IOException {

        super.readBody(l);
        nElementsSigned = l.readInt();
    }

    public int getnElementsSigned() {
        return nElementsSigned;
    }
}
