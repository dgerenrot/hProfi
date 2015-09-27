package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.hprofentries.AbstractHeapDumpEntity;
import com.websushibar.hprofpersist.hprofentries.HPROFEntity;
import com.websushibar.hprofpersist.hprofentries.HasId;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.annotation.Transient;

import java.io.IOException;

public abstract class DumpSubtagEntry extends HPROFEntity
        implements HasId {

    private IDField objId;

    @Transient
    protected AbstractHeapDumpEntity parent;

    public IDField getObjId() {
        return objId;
    }

    public IDField getId() { return getObjId(); }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        initStartByte(l);
        objId = l.readId();
    }

    public AbstractHeapDumpEntity getParent() {
        return parent;
    }

    public void setParent(AbstractHeapDumpEntity parent) {
        this.parent = parent;
    }

    @Override
    public void initStartByte(DataInputStreamWrapper l) {
        startsAtByte = l.getBytesRead() - IDField.getSize();
    }
}
