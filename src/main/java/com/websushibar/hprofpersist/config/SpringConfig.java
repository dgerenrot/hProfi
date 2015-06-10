package com.websushibar.hprofpersist.config;


import com.mongodb.MongoClient;
import com.websushibar.hprofpersist.MainClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;



@EnableMongoRepositories("com.websushibar.hprofpersist")
@Configuration
public class SpringConfig {

    public static String HOST = "localhost";

    
    public static String TEST_COLLECTION = "dummyCollection";
    public static String TEST_DB = "tryMongoDb";
//
//    @Bean
//    public MongoOperations mongoOperations() throws UnknownHostException {
//        MongoOperations opers = new MongoTemplate(new MongoClient(HOST), TEST_DB);
//        return opers;
//    }

    @Bean
    public MongoDbFactory mongoDbFactory() throws UnknownHostException {
        return new SimpleMongoDbFactory(new MongoClient(
                HOST),TEST_DB);
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
