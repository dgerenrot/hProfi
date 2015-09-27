package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ObjectArrayDump;
import org.springframework.data.repository.CrudRepository;

public interface ObjectArrayDumpRepo extends CrudRepository<ObjectArrayDump, IDField> {
    ObjectArrayDump findByObjId(IDField id);
}
