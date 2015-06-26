package com.websushibar.hprofpersist.persistence.mongo;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
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


    // TODO : lots of implementations to add!

    @Override
    public int getNumLoadClasses() {
        return 0;
    }

    @Override
    public int getNumStringEntries() {
        return 0;
    }

    @Override
    public int getNumClassDumps() {
        return 0;
    }

    @Override
    public int getNumInstanceDumps() {
        return 0;
    }

    @Override
    public LoadClass getLoadClass(IDField id) {
        return null;
    }

    @Override
    public LoadClass getLoadClass(long id) {
        return null;
    }

    @Override
    public LoadClass getLoadClass(byte[] id) {
        return null;
    }

    @Override
    public StringEntry getString(IDField id) {
        return null;
    }

    @Override
    public StringEntry getString(long id) {
        return null;
    }

    @Override
    public StringEntry getString(byte[] id) {
        return null;
    }

    @Override
    public ClassDump getClassDump(IDField id) {
        return null;
    }

    @Override
    public ClassDump getClassDump(long id) {
        return null;
    }

    @Override
    public ClassDump getClassDump(byte[] id) {
        return null;
    }

    @Override
    public InstanceDump getInstanceDump(IDField id) {
        return null;
    }

    @Override
    public InstanceDump getInstanceDump(long id) {
        return null;
    }

    @Override
    public InstanceDump getInstanceDump(byte[] id) {
        return null;
    }

    @Override
    protected <T extends HasId> T lookupById(IDField id) {
        throw new RuntimeException("Not implemented!");
    }

}
