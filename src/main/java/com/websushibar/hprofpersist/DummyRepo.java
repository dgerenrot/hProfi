package com.websushibar.hprofpersist;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface DummyRepo extends CrudRepository<Dummy, String> {
    List<Dummy> findByAge(int age);
}
