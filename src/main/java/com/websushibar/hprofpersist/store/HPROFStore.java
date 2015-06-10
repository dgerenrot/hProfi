package com.websushibar.hprofpersist.store;

import com.websushibar.hprofpersist.hprofentries.*;

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

    protected abstract <T extends HasId>  T lookupById(IDField id);
}
