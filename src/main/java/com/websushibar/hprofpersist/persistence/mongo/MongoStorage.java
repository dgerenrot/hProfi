package com.websushibar.hprofpersist.persistence.mongo;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import com.websushibar.hprofpersist.persistence.mongo.repos.*;
import com.websushibar.hprofpersist.store.HPROFStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Nothing in this class has been implemented!
 */

@Component
public class MongoStorage extends HPROFStore {

    private Map<Class<? extends HasId>,
                CrudRepository<? extends HasId, IDField>> idRegisters
            = new HashMap<>();

    @Autowired
    ClassDumpRepo classDumpRepo;

    @Autowired
    InstanceDumpRepo instanceDumpRepo;

    @Autowired
    LoadClassRepo loadClassRepo;

    @Autowired
    StringEntryRepo stringEntryRepo;

    @Autowired
    HeaderRepo headerRepo;

    /**
     * Remove everything from the collections.
     */
    public void reset() {
        classDumpRepo.deleteAll();
        instanceDumpRepo.deleteAll();
        loadClassRepo.deleteAll();
        stringEntryRepo.deleteAll();
        headerRepo.deleteAll();
    }

    @Override
    protected void initDumpSubtagIdRegisters() {
        idRegisters.put(ClassDump.class, classDumpRepo);
        idRegisters.put(InstanceDump.class, instanceDumpRepo);
    }

    // TODO : implement!
    @Override
    protected void registerIdAbleClass(Class<? extends HasId> clazz) {
    }


    @Override
    public void addHPROFHeader(HPROFHeaderInfo headerInfo) {
        super.addHPROFHeader(headerInfo);

        // TODO : all these assumptions of only one repo in the dump!
        headerRepo.deleteAll();
        headerRepo.save(headerInfo);
    }

    @Override
    public void addHPROFEntry(HPROFMainEntry entry) {

        if (entry instanceof LoadClass) {
            loadClassRepo.save((LoadClass) entry);

        } else if (entry instanceof StringEntry) {
            stringEntryRepo.save((StringEntry) entry);
        }
    }

    @Override
    public HPROFHeaderInfo getHPROFHeaderInfo() {

        if (super.getHPROFHeaderInfo() != null) {
            return super.getHPROFHeaderInfo();
        }

        Iterator<HPROFHeaderInfo> iter = headerRepo.findAll().iterator();

        if (iter.hasNext()) {
            super.addHPROFHeader(iter.next());
        }

        return super.getHPROFHeaderInfo();
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
    public Collection<InstanceDump> instDumps(IDField classId)  {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    public Collection<LoadClass> loadClassesMatchingName(String name)  {
        throw new RuntimeException("Not implemented!");
    }

    @Override
    protected <T extends HasId> T lookupById(IDField id) {
        throw new RuntimeException("Not implemented!");
    }

}
