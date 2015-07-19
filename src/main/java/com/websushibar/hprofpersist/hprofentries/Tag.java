package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;

import java.util.EnumMap;
import java.util.HashMap;

public enum Tag {
    STRING(0x01),
    LOAD_CLASS(0x02),
    UNLOAD_CLASS(0x03),
    STACK_FRAME(0x04),
    STACK_TRACE(0x05),
    ALLOC_SITES(0x06),
    HEAP_SUMMARY(0x07),
    START_THREAD(0x0A),
    END_THREAD(0x0B),
    HEAP_DUMP(0x0C),
    HEAP_DUMP_SEGMENT(0x1C),
    HEAP_DUMP_END(0x2C),
    CPU_SAMPLES(0x0D),

    CONTROL_SETTINGS(0x0E);

    private static HashMap<Class<? extends HPROFMainEntry>, Tag> tagByClass
            = new HashMap<Class<? extends HPROFMainEntry>, Tag>();

    private static HashMap<Integer, Tag> tagByCode
            = new HashMap<Integer, Tag>();

    private static EnumMap<Tag, Class<? extends HPROFMainEntry>> classByTag
            = new EnumMap<Tag, Class<? extends HPROFMainEntry>>(Tag.class);

    private int code;

    private Class<? extends HPROFMainEntry> hprofClazz;

    static
    {
        classByTag.put(STRING, StringEntry.class);
        classByTag.put(LOAD_CLASS, LoadClass.class);
        classByTag.put(UNLOAD_CLASS, UnloadClass.class);
        classByTag.put(STACK_FRAME, StackFrame.class);
        classByTag.put(STACK_TRACE, StackTrace.class);
        classByTag.put(ALLOC_SITES, AllocSites.class);
        classByTag.put(HEAP_SUMMARY, HeapSummary.class);
        classByTag.put(START_THREAD, StartThread.class);
        classByTag.put(END_THREAD, EndThread.class);
        classByTag.put(HEAP_DUMP, HeapDump.class);
        classByTag.put(HEAP_DUMP_SEGMENT, HeapDumpSegment.class);
        classByTag.put(HEAP_DUMP_END, HeapDumpEnd.class);
        classByTag.put(CPU_SAMPLES, CpuSamples.class);
        classByTag.put(CONTROL_SETTINGS, ControlSettings.class);

        // TODO ???????
        for (Tag t : Tag.values()) {
            tagByCode.put(t.getCode(), t);
        }
        for (Tag t : classByTag.keySet()) {
            tagByClass.put(classByTag.get(t), t);
        }

    }


    public static Tag getTag(Class<? extends HPROFMainEntry> c) {
        return tagByClass.get(c);
    }

    public static Tag getTag(int code) {
        if (!tagByCode.containsKey(code)) {
            throw new HPROFFormatException("Bad tag code " + code);
        }
        return tagByCode.get(code);
    }

    public static Class<? extends HPROFMainEntry> getClassByTag(Tag t) {
        return classByTag.get(t);
    }

    public int getCode() {
        return code;
    }

    private Tag(int code) {

        this.code = code;
        // tagByCode.put(code, this);
    }

    public Class<? extends HPROFMainEntry> getHPROFClazz() {
        return hprofClazz;
    }

    void setHPROFClazz(Class<? extends HPROFMainEntry> clazz) {
        this.hprofClazz = clazz;
    }
}
