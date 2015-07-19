package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import org.springframework.data.repository.CrudRepository;

public interface ClassDumpRepo
        extends CrudRepository<ClassDump, IDField> {

    ClassDump findById(IDField id);
}
