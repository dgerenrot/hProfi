package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import org.springframework.data.repository.CrudRepository;

public interface InstanceDumpRepo
        extends CrudRepository<InstanceDump, IDField> {

    InstanceDump findByObjId(IDField id);
}
