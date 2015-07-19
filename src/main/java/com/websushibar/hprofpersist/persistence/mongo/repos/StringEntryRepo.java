package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.StringEntry;
import org.springframework.data.repository.CrudRepository;

public interface StringEntryRepo
        extends CrudRepository<StringEntry, IDField> {

    StringEntry findById(IDField id);
}
