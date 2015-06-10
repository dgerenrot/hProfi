package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class StartThread extends HPROFMainEntry
        implements HasId{

    private int threadSerialNo;
    private IDField threadObjId;
    private int stackTraceSerialNo;
    private IDField threadNameStringId;
    private IDField threadGroupNameId;
    private IDField threadParentGroupNameId;

    public int getThreadSerialNo() {
        return threadSerialNo;
    }

    public IDField getThreadObjId() {
        return threadObjId;
    }

    @Override
    public IDField getId() { return getThreadObjId(); }

    public int getStackTraceSerialNo() {
        return stackTraceSerialNo;
    }

    public IDField getThreadNameStringId() {
        return threadNameStringId;
    }

    public IDField getThreadGroupNameId() {
        return threadGroupNameId;
    }

    public IDField getThreadParentGroupNameId() {
        return threadParentGroupNameId;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        threadSerialNo = l.readInt();
        threadObjId = l.readId();
        stackTraceSerialNo = l.readInt();
        threadNameStringId = l.readId();
        threadGroupNameId = l.readId();
        threadParentGroupNameId = l.readId();
    }
}
