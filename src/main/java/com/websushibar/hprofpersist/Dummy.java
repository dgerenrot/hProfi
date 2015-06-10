package com.websushibar.hprofpersist;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tryMongoDb")
public class Dummy {

    private String name;
    private Integer age;

    public Dummy(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Id
    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
