package com.websushibar.hprofpersist.config;


import com.mongodb.MongoClient;
import com.websushibar.hprofpersist.MainClass;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.net.UnknownHostException;


//@Configuration
//@EnableMongoRepositories("com.websushibar.hprofpersist")
public class SpringConfig {

    public static final String HOST = "localhost";

    public static final String HPROFI_INST_DB = "hProfiInstance";

    private MongoDbFactory dbFactory;
    private MongoTemplate template;

//
//    @Bean
//    public MongoOperations mongoOperations() throws UnknownHostException {
//        MongoOperations opers = new MongoTemplate(new MongoClient(HOST), HPROFI_INST_DB);
//        return opers;
//    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        if (dbFactory == null) {
            dbFactory =  new SimpleMongoDbFactory(new MongoClient(
                    HOST), HPROFI_INST_DB);
        }

        return dbFactory;
    }

    @Bean
    public MongoTemplate mongoTemplate() throws UnknownHostException {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public MainClass mainClass() throws UnknownHostException {
        return new MainClass();
    }
}
