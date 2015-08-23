package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

import static com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag.readBasicTypeTag;
import static com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag.readEntryValue;


public class PrimitiveArrayDump extends AbstractArrayDump {

    private BasicTypeTag typeTag;

    private Object[] values;

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        super.readBody(l);

        typeTag = readBasicTypeTag(l);

        values = new Object[getnElementsSigned()];

        for (int i = 0; i < getnElementsSigned(); i++) {
            values[i] = readEntryValue(l, typeTag);
        }
    }

    public BasicTypeTag getTypeTag() {
        return typeTag;
    }

    public Object get(int i) {
        return values[i];
    }
}
