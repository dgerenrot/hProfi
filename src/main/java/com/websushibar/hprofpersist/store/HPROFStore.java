package com.websushibar.hprofpersist.store;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;

import java.util.Collection;

public abstract class HPROFStore {

    private HPROFHeaderInfo headerInfo;

    public void addHPROFHeader(HPROFHeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

    protected void initDumpSubtagIdRegisters() {
        for (DumpSubtag t : DumpSubtag.values()) {
            if (HasId.class.isAssignableFrom(DumpSubtag.getClassByTag(t))) {

                Class<? extends HasId> tagClass = DumpSubtag.getClassByTag(t)
                                                     .asSubclass(HasId.class);
                registerIdAbleClass(tagClass);
            }
        }

//        idRegisters.put(ClassDump.class, new HashMap<IDField, HasId>());
//        idRegisters.put(InstanceDump.class, new HashMap<IDField, HasId>());
//        idRegisters.put(ObjectArrayDump.class, new HashMap<IDField, HasId>());
//        idRegisters.put(PrimitiveArrayDump.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootJavaFrame.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootJNIGlobal.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootJNILocal.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootMonitorUsed.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootNativeStack.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootStickyClass.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootThreadBlock.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootThreadObject.class, new HashMap<IDField, HasId>());
//        idRegisters.put(RootUnknown.class, new HashMap<IDField, HasId>());
    }

    protected abstract void registerIdAbleClass(Class<? extends HasId> clazz);

    public abstract void addHPROFEntry(HPROFMainEntry entry);

    public  <T extends HasId>  T getObject(IDField objId) {

        if (objId.equals(IDField.NULL_ID))
            return null;

        T retVal = lookupById(objId);

        if (retVal == null) {
            throw new IllegalArgumentException("Object Id " + objId +
                    " has no object registered under it!");
        }

        return retVal;
    }

    public <T extends HasId>  T  getObject(byte ... bytes) {
        return getObject(new IDField(bytes));
    }

    public HPROFHeaderInfo getHPROFHeaderInfo() {
        return headerInfo;
    }

//    public abstract HPROFEntity getClassDump(IDField objId)
//    public abstract HPROFEntity getObject(byte ... bytes);
//    public abstract HPROFEntity getObject(IDField objId);
//    public abstract HPROFEntity getObject(byte ... bytes);
//    public abstract HPROFEntity getObject(IDField objId);
//    public abstract HPROFEntity getObject(byte ... bytes);

    public abstract HeapSummary getHeapSummary() throws ClassCastException;

    public abstract int getNumLoadClasses();

    public abstract int getNumStringEntries();

    public abstract int getNumClassDumps();

    public abstract int getNumInstanceDumps();

    public abstract LoadClass getLoadClass(IDField id);

    public abstract LoadClass getLoadClass(long id);

    public abstract LoadClass getLoadClass(byte[] id);

    public abstract StringEntry getString(IDField id);

    public abstract StringEntry getString(long id);

    public abstract StringEntry getString(byte[] id);

    public abstract ClassDump getClassDump(IDField id);

    public abstract ClassDump getClassDump(long id);

    public abstract ClassDump getClassDump(byte[] id);

    public abstract InstanceDump getInstanceDump(IDField id);

    public abstract InstanceDump getInstanceDump(long id);

    public abstract InstanceDump getInstanceDump(byte[] id);

    public abstract Collection<InstanceDump> instDumpsByClass(IDField classId);

    public abstract Collection<LoadClass> loadClassesMatchingName(String name);

    protected abstract <T extends HasId>  T lookupById(IDField id);
}
