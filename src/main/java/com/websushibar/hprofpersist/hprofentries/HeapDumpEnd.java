package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class HeapDumpEnd extends HPROFMainEntry {
    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
         // no body to speak of
    }
}
