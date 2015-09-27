package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import com.websushibar.hprofpersist.hprofentries.IDField;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;


@Document
public class InstanceDump  extends AbstractObjDump {

    private IDField classObjId;
    private int nBytesSigned;
    byte[] values;

    public IDField getClassObjId() {
        return classObjId;
    }

    public int getnBytesSigned() {
        return nBytesSigned;
    }

    // TODO : need to load these correctly!
    public byte[] getValues() {
        return values;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        super.readBody(l);

        classObjId = l.readId();
        nBytesSigned = l.readInt();
        values = new byte[nBytesSigned];

        for (int i = 0; i < nBytesSigned; i++) {
            values[i] = l.readByte();
        }
    }
}
