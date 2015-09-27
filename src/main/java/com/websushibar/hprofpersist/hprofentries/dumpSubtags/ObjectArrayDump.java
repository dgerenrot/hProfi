package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import com.websushibar.hprofpersist.hprofentries.IDField;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Document
public class ObjectArrayDump extends AbstractArrayDump {
    private IDField arrayClassObjId;

    private List<IDField> arrayElements;

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        super.readBody(l);

        arrayClassObjId = l.readId();
        arrayElements = new ArrayList<IDField>(getnElementsSigned());

        for (int i = 0; i < getnElementsSigned(); i++) {
            IDField id = l.readId();
            arrayElements.add(id);
        }
         // TODO
    }

    @Override
    public Object get(int i) {
        return arrayElements.get(i);
    }

    public IDField getArrayClassObjId() {
        return arrayClassObjId;
    }
}
