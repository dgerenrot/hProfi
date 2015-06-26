package com.websushibar.hprofpersist.store;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;

public abstract class HPROFStore {

    private HPROFHeaderInfo headerInfo;

    public void addHPROFHeader(HPROFHeaderInfo headerInfo) {
        this.headerInfo = headerInfo;
    }

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


    protected abstract <T extends HasId>  T lookupById(IDField id);
}
