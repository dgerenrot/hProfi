package com.websushibar.hprofpersist.store;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.*;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HPROFMemoryStore extends HPROFStore{

    private Map<Class<? extends HasId>, Map<IDField, ? extends HasId>> idRegisters
        = new HashMap<>();

    private Map<IDField, StringEntry> stringsById = new HashMap<>();
    private Map<IDField, StartThread> threadsById = new HashMap<>();
    private Map<IDField, StackFrame> stackFramesById = new HashMap<>();


    // The LoadClass object map will be stored separately, as entries'
    // objectId's  duplicate those of classDumps.
    private Map<IDField, LoadClass> classesById = new HashMap<>();

    private Map<Tag, Collection<HPROFMainEntry>> byTag
            = new HashMap<>();

    private Map<Long, HPROFEntity> byLocation = new HashMap<>();

    public HPROFMemoryStore() {
        idRegisters.put(StringEntry.class, stringsById);
        idRegisters.put(StackFrame.class, stackFramesById);
        idRegisters.put(StartThread.class, threadsById);
        initDumpSubtagIdRegisters();
    }


    private void addForClass(Class<? extends HasId> clazz) {

    }

    private void initDumpSubtagIdRegisters() {
        idRegisters.put(ClassDump.class, new HashMap<IDField, HasId>());
        idRegisters.put(InstanceDump.class, new HashMap<IDField, HasId>());
        idRegisters.put(ObjectArrayDump.class, new HashMap<IDField, HasId>());
        idRegisters.put(PrimitiveArrayDump.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootJavaFrame.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootJNIGlobal.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootJNILocal.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootMonitorUsed.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootNativeStack.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootStickyClass.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootThreadBlock.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootThreadObject.class, new HashMap<IDField, HasId>());
        idRegisters.put(RootUnknown.class, new HashMap<IDField, HasId>());
    }


    @SuppressWarnings(value = "unchecked")
    @Override
    public void addHPROFEntry(HPROFMainEntry entry) {
        Tag tag = entry.getTag();

        Map<IDField, ? extends HasId> quald = new HashMap();
        idRegisters.put(ClassDump.class, new HashMap<IDField, HasId>());

        if (byTag.get(tag) == null) {
            byTag.put(tag, new ArrayList<HPROFMainEntry>());
        }

        if (idRegisters.get(entry.getClass()) != null) {
            HasId hasId = (HasId) entry;

            ((Map)idRegisters.get(entry.getClass())).put((hasId).getId(), hasId);
        }

        if (entry.getClass().equals(LoadClass.class)) {
            classesById.put(((HasId)entry).getId(), (LoadClass) entry);
        }

        byTag.get(tag).add(entry);
        byLocation.put(entry.getStartsAtByte(), entry);
    }

    @Override
    public HeapSummary getHeapSummary() throws ClassCastException {
        try {
            if (byTag.get(Tag.HEAP_SUMMARY) == null
                    || !byTag.get(Tag.HEAP_SUMMARY).iterator().hasNext()) {
                throw new HPROFFormatException("No heap summary");
            }

            return (HeapSummary)byTag.get(Tag.HEAP_SUMMARY).iterator().next();
        } catch (ClassCastException e) {
            throw new ClassCastException("Object of wrong type stored under HeapSummary tag!"
                    + byTag.get(Tag.HEAP_SUMMARY));
        }
    }

    @Override
    protected <T extends HasId> T lookupById(IDField id) {
        for (Map<IDField, ? extends HasId> map : idRegisters.values()) {
            if (map == classesById)
                continue;
            if (map.keySet().contains(id)) {
                try {
                    // And what if one of these types extends the other someday?
                    return (T) map.get(id);
                }catch (ClassCastException e) {
                    continue;
                }
            }
        }

        return null;
    }

    public void initDumpSubtags() {
        if (byTag.get(Tag.HEAP_DUMP) ==  null) {
            return;
        }

        for (HPROFMainEntry entry : byTag.get(Tag.HEAP_DUMP)) {
            HeapDump heapDump = (HeapDump)entry;

            for (DumpSubtagEntry subtag : heapDump.getSubtagEntries()) {
                byLocation.put(subtag.getStartsAtByte(), subtag);
                ((Map)idRegisters.get(subtag.getClass())).put(subtag.getId(), subtag);
            }
        }
    }

    /**
     *  Finds the LoadClass entry corresponding to java.lang.Object and
     *  initializes the LoadClass's static field.
     *
     * @return true if ObjectClass has been initialized by the end of the function call.
     */
    public boolean initObjectClass() {
        if (LoadClass.getObjectLoadClass() != null)
            return true;

        for (IDField key : classesById.keySet()) {
            LoadClass lc = classesById.get(key);
            if (lc == null)
                continue;

            StringEntry se = stringsById.get(lc.getClassNameStringId());
            if (se.equals(LoadClass.OBJECT_CLASS)) {
                LoadClass.setObjectLoadClass(lc);
                return true;
            }
        }

        return false;
    }

    public Map<IDField, LoadClass> getClassesById() {
        return classesById;
    }

    public Map<IDField, StringEntry> getStringsById() {
        return stringsById;
    }

    public Map<IDField, StartThread> getThreadsById() {
        return threadsById;
    }

    public Map<IDField, StackFrame> getStackFramesById() {
        return stackFramesById;
    }

    public Map<IDField, InstanceDump> getInstanceDumpsById() {
        return getStorage(InstanceDump.class);
    }

    public Map<IDField, ClassDump> getClassDumpById() {
        return getStorage(ClassDump.class);
    }

    public <T extends HasId> Map<IDField, T> getStorage(Class<T> clazz) {
        return (Map) idRegisters.get(clazz);
    }

    public <T extends HasId> Map<IDField, T> getStorage(Tag tag) {
        return (Map) idRegisters.get(Tag.getClassByTag(tag));
    }

    public Map<Long, HPROFEntity> getByLocationRegister() {
        return byLocation;
    }

}
