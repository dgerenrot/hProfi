package com.websushibar.hprofpersist.persistence.mongo;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.store.HPROFStore;


/**
 * Nothing in this class has been implemented!
 */
public class MongoStorage extends HPROFStore {

    @Override
    public void addHPROFHeader(HPROFHeaderInfo headerInfo) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public void addHPROFEntry(HPROFMainEntry entry) {

        throw new RuntimeException("Not implemented!");
    }

    public HPROFHeaderInfo getHPROFHeaderInfo() {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public <T extends HasId> T getObject(IDField objId) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public <T extends HasId> T getObject(byte... bytes) {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public HeapSummary getHeapSummary() {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    protected <T extends HasId> T lookupById(IDField id) {
        throw new RuntimeException("Not implemented!");
    }

}
