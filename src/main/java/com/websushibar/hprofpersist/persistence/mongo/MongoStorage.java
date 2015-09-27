package com.websushibar.hprofpersist.persistence.mongo;

import com.websushibar.hprofpersist.hprofentries.*;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.*;
import com.websushibar.hprofpersist.persistence.mongo.repos.*;
import com.websushibar.hprofpersist.store.HPROFStore;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


/**
 * Nothing in this class has been implemented!
 */

@Component
public class MongoStorage extends HPROFStore implements InitializingBean{

    private Map<Class<? extends HasId>,
                CrudRepository<? extends HasId, IDField>> idRegisters
            = new HashMap<>();

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ClassDumpRepo classDumpRepo;

    @Autowired
    InstanceDumpRepo instanceDumpRepo;

    @Autowired
    ObjectArrayDumpRepo objArrDumpRepo;

    @Autowired
    PrimitiveArrayDumpRepo primArrDumpRepo;

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
        Collection<String> names = new HashSet<>();

        for (Tag t : Tag.values()) {
            names.add(Tag.getClassByTag(t).getSimpleName());
        }
        for (DumpSubtag t : DumpSubtag.values()) {
            names.add(DumpSubtag.getClassByTag(t).getSimpleName());
        }

        Collection<String> collNames = new ArrayList<>(mongoTemplate.getCollectionNames());

        for (String collName : collNames) {
            String cnLC = collName.toLowerCase();
            for (String name : names) {
                if (cnLC.endsWith(name.toLowerCase())) {
                    mongoTemplate.dropCollection(collName);
                    break;
                }
            }
        }
    }

    @Override
    protected void initDumpSubtagIdRegisters() {
        idRegisters.put(ClassDump.class, classDumpRepo);
        idRegisters.put(InstanceDump.class, instanceDumpRepo);
        idRegisters.put(ObjectArrayDump.class, objArrDumpRepo);
        idRegisters.put(PrimitiveArrayDump.class, primArrDumpRepo);
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
        // headerRepo.save(headerInfo); // TODO : not yet
    }

    @Override
    public void addHPROFEntry(HPROFMainEntry entry) {

        mongoTemplate.save(entry);

        if (entry instanceof AbstractHeapDumpEntity) {
            AbstractHeapDumpEntity entity = (AbstractHeapDumpEntity) entry;
            for (DumpSubtagEntry e : entity.getSubtagEntries()) {
                mongoTemplate.save(e);
            }
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
    public HeapSummary getHeapSummary() {
        throw new RuntimeException("Not implemented!");
    }


    // TODO : lots of implementations to add!

    @Override
    public int getNumLoadClasses() {
        return getNumDumpsFor(LoadClass.class);
    }

    @Override
    public int getNumStringEntries() {
        return getNumDumpsFor(StringEntry.class);
    }

    @Override
    public int getNumClassDumps() {
        return getNumDumpsFor(ClassDump.class);
    }

    @Override
    public int getNumInstanceDumps() {
        return getNumDumpsFor(InstanceDump.class);
    }

    @Override
    public int getNumObjectArrayDumps() {
        return getNumDumpsFor(ObjectArrayDump.class);
    }

    @Override
    public int getNumPrimitiveArrayDumps() {
        return getNumDumpsFor(PrimitiveArrayDump.class);
    }

    @Override
    public int getNumDumpsFor(Class<? extends HasId> clazz) {
        CrudRepository<?, ?> repo = idRegisters.get(clazz);

        return (int) (repo != null ? repo.count() : 0);
    }

    @Override
    public LoadClass getLoadClass(IDField id) {
        return loadClassRepo.findOne(id);
    }

    @Override
    public LoadClass getLoadClass(long id) {
        return loadClassRepo.findOne(new IDField(id));
    }

    @Override
    public LoadClass getLoadClass(byte[] id) {
        return loadClassRepo.findOne(new IDField(id));
    }

    @Override
    public StringEntry getString(IDField id)   {
        return stringEntryRepo.findOne(id);
    }

    @Override
    public StringEntry getString(long id) {
        return stringEntryRepo.findOne(new IDField(id));
    }

    @Override
    public StringEntry getString(byte[] id) {
        return stringEntryRepo.findOne(new IDField(id));
    }

    @Override
    public ClassDump getClassDump(IDField id)   {
        return classDumpRepo.findByObjId(id);
    }

    @Override
    public ClassDump getClassDump(long id) {
        return classDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public ClassDump getClassDump(byte[] id) {
        return classDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public InstanceDump getInstanceDump(IDField id)   {
        return instanceDumpRepo.findByObjId(id);
    }

    @Override
    public InstanceDump getInstanceDump(long id) {
        return instanceDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public InstanceDump getInstanceDump(byte[] id) {
        return instanceDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public ObjectArrayDump getObjectArrayDump(IDField id) {
        return objArrDumpRepo.findByObjId(id);
    }

    @Override
    public ObjectArrayDump getObjectArrayDump(long id) {
        return objArrDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public ObjectArrayDump getObjectArrayDump(byte[] id) {
        return objArrDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public PrimitiveArrayDump getPrimitiveArrayDump(IDField id)  {
        return primArrDumpRepo.findByObjId(id);
    }

    @Override
    public PrimitiveArrayDump getPrimitiveArrayDump(long id) {
        return primArrDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public PrimitiveArrayDump getPrimitiveArrayDump(byte[] id) {
        return primArrDumpRepo.findByObjId(new IDField(id));
    }

    @Override
    public Collection<InstanceDump> instDumps(IDField classId)  {
        List<InstanceDump> retVal = mongoTemplate.find(query(where("classObjId").is(classId.getNumeric())),
                                                        InstanceDump.class);
        return retVal;
    }

    @Override
    public Collection<LoadClass> loadClassesMatchingRE(String name)  {
        HashSet<LoadClass> retVal = new HashSet<>();

        for (LoadClass loadClass : loadClassRepo.findAll()) {
            StringEntry stringEntry = getString(loadClass.getClassNameStringId());

            if (stringEntry.getContent().matches(name)) {
                retVal.add(loadClass);
            }
        }

        return retVal;

    }

    public <T extends HasId> CrudRepository<T, IDField> getRepoFor(Class<T> clazz) {
        return (CrudRepository<T, IDField>)idRegisters.get(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        idRegisters.put(StringEntry.class, stringEntryRepo);
        idRegisters.put(LoadClass.class, loadClassRepo);
        initDumpSubtagIdRegisters();

    }
}
