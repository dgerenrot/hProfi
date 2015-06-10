package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;

public class StackFrame extends HPROFMainEntry implements HasId{

    public static final int NO_LINE_INFO = 0;
    public static final int LOCATION_UNKNOWN = -1;
    public static final int COMPILED_METHOD = -2;
    public static final int NATIVE_METHOD = 3;

    private IDField stackFrameId;
    private IDField methodNameStringId;
    private IDField methodSignatureStringId;
    private IDField sourceFileNameStringId;
    private int classSerialNo;
    private int lineNo;

    public IDField getStackFrameId() {
        return stackFrameId;
    }

    @Override
    public IDField getId() {
        return getStackFrameId();
    }

    public IDField getMethodNameStringId() {
        return methodNameStringId;
    }

    public IDField getMethodSignatureStringId() {
        return methodSignatureStringId;
    }

    public IDField getSourceFileNameStringId() {
        return sourceFileNameStringId;
    }

    public int getClassSerialNo() {
        return classSerialNo;
    }

    public int getLineNo() {
        return lineNo;
    }

    @Override
    public void readBody(DataInputStreamWrapper l) throws IOException {

        stackFrameId = l.readId();
        methodNameStringId = l.readId();
        methodSignatureStringId = l.readId();
        sourceFileNameStringId = l.readId();
        classSerialNo = l.readInt();
        lineNo = l.readInt();
    }
}
