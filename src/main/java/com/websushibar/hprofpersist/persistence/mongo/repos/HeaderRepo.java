package com.websushibar.hprofpersist.persistence.mongo.repos;


import com.websushibar.hprofpersist.hprofentries.HPROFHeaderInfo;
import org.springframework.data.repository.CrudRepository;

public interface HeaderRepo extends CrudRepository<HPROFHeaderInfo, Integer> {
}
