package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public abstract class AbstractSubtagWThreadAndFrame
        extends AbstractRootSubtagWithThread{
    public static final int FRAME_NO_EMPTY = -1;

    private int frameNoInTrace;

    public int getFrameNoInTrace() {
        return frameNoInTrace;
    }

    public boolean isFrameNoEmpty() {
        return frameNoInTrace == FRAME_NO_EMPTY;
    }

    @Override
    public void readBody(DataInputStreamWrapper l)
            throws IOException {
        super.readBody(l);
        frameNoInTrace = l.readInt();
    }
}
