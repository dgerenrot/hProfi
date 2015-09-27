package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.IOException;

@Document
public class LoadClass extends HPROFMainEntry
        implements HasId{

    public static String OBJECT_CLASS = "java.lang.Object";

    public static LoadClass objectLoadClass;

    private int serialNo;

    private IDField classObjId;
    private int stackTraceSerialNo;
    private IDField classNameStringId;

    public int getSerialNo() {
        return serialNo;
    }

    public IDField getClassObjId() {
        return classObjId;
    }
    public IDField getId() { return getClassObjId(); }

    public int getStackTraceSerialNo() {
        return stackTraceSerialNo;
    }

    public IDField getClassNameStringId() {
        return classNameStringId;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {
        serialNo = l.readInt();
        classObjId = l.readId();
        stackTraceSerialNo = l.readInt();
        classNameStringId = l.readId();
    }

    public static LoadClass getObjectLoadClass() {
        return objectLoadClass;
    }

    /**
     * The application may need to be able to find the LoadClass entry
     * corresponding to java.lang.Object. This is a setter. It WILL change
     * the objectLoadClass on multiple calls.
     *
     * @param objectLoadClass
     */
    public static void setObjectLoadClass(LoadClass objectLoadClass) {
        LoadClass.objectLoadClass = objectLoadClass;
    }

}
