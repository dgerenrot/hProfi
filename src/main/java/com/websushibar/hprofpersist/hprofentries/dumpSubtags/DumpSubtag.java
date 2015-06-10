package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import java.util.EnumMap;
import java.util.HashMap;

public enum DumpSubtag {
    ROOT_UNKNOWN(0xFF),
    ROOT_JNI_GLOBAL(0x01),
    ROOT_JNI_LOCAL(0x02),
    ROOT_JAVA_FRAME(0x03),
    ROOT_NATIVE_STACK(0x04),
    ROOT_STICKY_CLASS(0x05),
    ROOT_THREAD_BLOCK(0x06),
    ROOT_MONITOR_USED(0x07),
    ROOT_THREAD_OBJECT(0x08),
    CLASS_DUMP(0x20),
    INSTANCE_DUMP(0x21),
    OBJECT_ARRAY_DUMP(0x22),
    PRIMITIVE_ARRAY_DUMP(0x23);

    private final int code;

    DumpSubtag(int code) {
        this.code = code;
    }

    private static HashMap<Class<? extends DumpSubtagEntry>, DumpSubtag> subTagByClass
            = new HashMap<Class<? extends DumpSubtagEntry>, DumpSubtag>();

    private static HashMap<Integer, DumpSubtag> subTagByCode
            = new HashMap<Integer, DumpSubtag>();

    private static EnumMap<DumpSubtag, Class<? extends DumpSubtagEntry>> classBySubTag
            = new EnumMap<DumpSubtag, Class<? extends DumpSubtagEntry>>(DumpSubtag.class);


    static {

        classBySubTag.put(ROOT_UNKNOWN, RootUnknown.class);
        classBySubTag.put(ROOT_JNI_GLOBAL, RootJNIGlobal.class);
        classBySubTag.put(ROOT_JNI_LOCAL, RootJNILocal.class);
        classBySubTag.put(ROOT_JAVA_FRAME, RootJavaFrame.class);
        classBySubTag.put(ROOT_NATIVE_STACK, RootNativeStack.class);
        classBySubTag.put(ROOT_STICKY_CLASS, RootStickyClass.class);
        classBySubTag.put(ROOT_THREAD_BLOCK, RootThreadBlock.class);
        classBySubTag.put(ROOT_MONITOR_USED, RootMonitorUsed.class);
        classBySubTag.put(ROOT_THREAD_OBJECT, RootThreadObject.class);
        classBySubTag.put(CLASS_DUMP, ClassDump.class);
        classBySubTag.put(INSTANCE_DUMP, InstanceDump.class);
        classBySubTag.put(OBJECT_ARRAY_DUMP, ObjectArrayDump.class);
        classBySubTag.put(PRIMITIVE_ARRAY_DUMP, PrimitiveArrayDump.class);

        for (DumpSubtag st : DumpSubtag.values()) {
            subTagByCode.put(st.getCode(), st);
        }

        for (DumpSubtag st : classBySubTag.keySet()) {
            subTagByClass.put(classBySubTag.get(st), st);
        }
    }
    public static DumpSubtag getTag(Class<? extends DumpSubtagEntry> c) {
        return subTagByClass.get(c);
    }

    public static DumpSubtag getTag(byte code) {
        int intCode = 0xFF & code;
        return subTagByCode.get(intCode);
    }

    public static Class<? extends DumpSubtagEntry> getClassByTag(DumpSubtag t) {
        return classBySubTag.get(t);
    }


    public int getCode() {
        return code;
    }
}

