package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import com.websushibar.hprofpersist.hprofentries.IDField;

import java.io.IOException;

public class RootJNIGlobal  extends AbstractRootSubtag{


    private IDField jniGlobalRefId;

    public IDField getJniGlobalRefId() {
        return jniGlobalRefId;
    }

    @Override
    public void readBody(DataInputStreamWrapper l)
            throws IOException {
        super.readBody(l);
        jniGlobalRefId = l.readId();
    }
}
