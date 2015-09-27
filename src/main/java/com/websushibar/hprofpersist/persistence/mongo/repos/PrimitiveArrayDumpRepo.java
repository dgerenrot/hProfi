package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.PrimitiveArrayDump;
import org.springframework.data.repository.CrudRepository;

public interface PrimitiveArrayDumpRepo extends CrudRepository<PrimitiveArrayDump, IDField> {
    PrimitiveArrayDump findByObjId(IDField id);
}
