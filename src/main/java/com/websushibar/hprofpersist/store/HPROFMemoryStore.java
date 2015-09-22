 package com.websushibar.hprofpersist.store;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.*;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;

import java.util.*;

import static com.google.common.collect.Collections2.filter;
import static com.websushibar.hprofpersist.utils.Utils.isOfClass;

 public class HPROFMemoryStore extends HPROFStore {

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

    @Override
    protected void registerIdAbleClass(Class<? extends HasId> clazz) {
        idRegisters.put(clazz, new HashMap<IDField, HasId>());
    }

    @SuppressWarnings(value = "unchecked")
    @Override
    public void addHPROFEntry(HPROFMainEntry entry) {
        Tag tag = entry.getTag();

        Map<IDField, ? extends HasId> quald = new HashMap();
        registerIdAbleClass(ClassDump.class);

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


     /**
      *
      * We need to call this after the store has been loaded.
      */
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

    // TODO : multiple HPROF dumps in one .hprof file???

    @Override
    public int getNumLoadClasses() {
        return classesById.size();
    }

    @Override
    public int getNumStringEntries() {
        return stringsById.size();
    }

    @Override
    public int getNumClassDumps() {
        return getStorage(ClassDump.class).size();
    }

    @Override
    public int getNumInstanceDumps() {
        return getStorage(InstanceDump.class).size();
    }

    @Override
    public int getNumObjectArrayDumps() {
     return getStorage(ObjectArrayDump.class).size();
    }

    @Override
    public int getNumPrimitiveArrayDumps() {
     return getStorage(PrimitiveArrayDump.class).size();
    }

     @Override
    public int getNumDumpsFor(Class<? extends HasId> clazz) { return getStorage(clazz).size(); }

    @Override
    public LoadClass getLoadClass(IDField id) {
        return classesById.get(id);
    }

    @Override
    public LoadClass getLoadClass(long id) {
        return classesById.get(new IDField(id));
    }

    @Override
    public LoadClass getLoadClass(byte[] id) {
        return classesById.get(new IDField(id));
    }


    @Override
    public StringEntry getString(IDField id) {
        return stringsById.get(id);
    }

    @Override
    public StringEntry getString(long id) {
        return stringsById.get(new IDField(id));
    }

    @Override
    public StringEntry getString(byte[] id) {
        return stringsById.get(new IDField(id));
    }


    @Override
    public ClassDump getClassDump(IDField id) {
        return getClassDumpsById().get(id);
    }

    @Override
    public ClassDump getClassDump(long id) {
        return getClassDumpsById().get(new IDField(id));
    }

    @Override
    public ClassDump getClassDump(byte[] id) {
        return getClassDumpsById().get(id);
    }


    @Override
    public InstanceDump getInstanceDump(IDField id) {
        return getInstanceDumpsById().get(id);
    }

    @Override
    public InstanceDump getInstanceDump(long id) {
        return getInstanceDumpsById().get(new IDField(id));
    }

    @Override
    public InstanceDump getInstanceDump(byte[] id) {
        return getInstanceDumpsById().get(id);
    }

     @Override
     public ObjectArrayDump getObjectArrayDump(IDField id) {
         return (ObjectArrayDump)idRegisters.get(ObjectArrayDump.class).get(id);
     }

     @Override
     public ObjectArrayDump getObjectArrayDump(long id) {
         return (ObjectArrayDump)idRegisters.get(ObjectArrayDump.class).get(new IDField(id));
     }

     @Override
     public ObjectArrayDump getObjectArrayDump(byte[] id) {
         return (ObjectArrayDump)idRegisters.get(ObjectArrayDump.class).get(new IDField(id));
     }

     @Override
     public PrimitiveArrayDump getPrimitiveArrayDump(IDField id) {
         return (PrimitiveArrayDump)idRegisters.get(PrimitiveArrayDump.class).get(id);
     }

     @Override
     public PrimitiveArrayDump getPrimitiveArrayDump(long id) {
         return (PrimitiveArrayDump)idRegisters.get(PrimitiveArrayDump.class).get(new IDField(id));     }

     @Override
     public PrimitiveArrayDump getPrimitiveArrayDump(byte[] id) {
         return (PrimitiveArrayDump)idRegisters.get(PrimitiveArrayDump.class).get(new IDField(id));
     }

     @Override
    public Collection<InstanceDump> instDumps(IDField classId) {
        return filter(getInstanceDumpsById().values(), isOfClass(classId));
    }

    @Override
    public Collection<LoadClass> loadClassesMatchingRE(String regExp) {
        HashSet<LoadClass> retVal = new HashSet<>();

        for (LoadClass loadClass : classesById.values()) {
             StringEntry stringEntry = getString(loadClass.getClassNameStringId());

             if (stringEntry.getContent().matches(regExp)) {
                 retVal.add(loadClass);
             }
        }

         return retVal;
    }


     // TODO : StackFrames, Theads, ArrayDumps, and all other HasIds?

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

    public Map<IDField, ClassDump> getClassDumpsById() {
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
