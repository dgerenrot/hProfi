package com.websushibar.hprofpersist.persistence.mongo.repos;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.LoadClass;
import org.springframework.data.repository.CrudRepository;

public interface LoadClassRepo
        extends CrudRepository<LoadClass, IDField> {
    LoadClass findByClassObjId(IDField id);
}
