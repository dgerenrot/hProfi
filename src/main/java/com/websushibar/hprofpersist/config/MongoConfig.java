package com.websushibar.hprofpersist.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.websushibar.hprofpersist.DummyConverter;
import com.websushibar.hprofpersist.MainClass;
import com.websushibar.hprofpersist.persistence.mongo.converters.IDReadLongConverter;
import com.websushibar.hprofpersist.persistence.mongo.converters.IDWriteLongConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoRepositories("com.websushibar.hprofpersist")
public class MongoConfig extends AbstractMongoConfiguration {

    public static final String HOST = "localhost";
    public static final String HPROFI_INST_DB = "hProfiInstance";

    private List<Converter> converterList = new ArrayList<>();
    private DummyConverter dummyConverterObj;
    private MappingMongoConverter mappingMongoConverter;

    @Override
    protected String getDatabaseName() {
        return HPROFI_INST_DB;
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(HOST);
    }

    @Override
    public String getMappingBasePackage() {
        return "com.websushibar.hprofpersist";
    }


    @Bean
    @Override
    public CustomConversions customConversions() {

        if (converterList.size() == 0) {
            if (dummyConverterObj == null) {
                dummyConverterObj = new DummyConverter();
            }
            converterList.add(dummyConverterObj); // TODO : testing only!

            converterList.add(new IDWriteLongConverter());
            converterList.add(new IDReadLongConverter());
        }

        return new CustomConversions(converterList);
    }

    @Bean
    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {

        if (mappingMongoConverter == null) {
            mappingMongoConverter = super.mappingMongoConverter();
        }

        return mappingMongoConverter;
    }

    @Bean
    public DummyConverter dummyConverter() {
        if (dummyConverterObj == null) {
            dummyConverterObj = new DummyConverter();
        }

        return dummyConverterObj;
    }

    @Bean
    public MainClass mainClass() throws UnknownHostException {
        return new MainClass();
    }

}
