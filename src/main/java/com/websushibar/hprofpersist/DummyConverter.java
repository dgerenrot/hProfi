package com.websushibar.hprofpersist;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.AbstractMongoConverter;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;

import static org.springframework.util.Assert.notNull;

@WritingConverter
public class DummyConverter implements Converter<Dummy, DBObject>,
        InitializingBean, ApplicationContextAware {

    MappingMongoConverter mappingMongoConverter;

    private ApplicationContext applicationContext;

    @Override
    public DBObject convert(Dummy source) {
        DBObject dbo = new BasicDBObject();
        mappingMongoConverter.write(source, dbo);
        dbo.put("_id", "labdAt " + System.currentTimeMillis());


        return dbo;
    }

    public MappingMongoConverter getMappingMongoConverter() {
        return mappingMongoConverter;
    }

    public void setMappingMongoConverter(MappingMongoConverter mappingMongoConverter) {
        this.mappingMongoConverter = mappingMongoConverter;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        MongoDbFactory dbFactory = applicationContext.getBean(MongoDbFactory.class);
        notNull(dbFactory);
        DbRefResolver resolver = new DefaultDbRefResolver(dbFactory);
        MappingMongoConverter converter = (MappingMongoConverter)
                applicationContext.getBean(AbstractMongoConverter.class);

        //notNull(converter);
        MappingMongoConverter duplicate = new MappingMongoConverter(resolver, converter.getMappingContext());
        mappingMongoConverter = duplicate;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

//        MongoDbFactory dbFactory = applicationContext.getBean(MongoDbFactory.class);
//        notNull(dbFactory);
//        DbRefResolver resolver = new DefaultDbRefResolver(dbFactory);
//        MappingMongoConverter converter = (MappingMongoConverter)
//                applicationContext.getBean(AbstractMongoConverter.class);
//
//        notNull(converter);
//        MappingMongoConverter duplicate = new MappingMongoConverter(resolver, converter.getMappingContext());
//        mappingMongoConverter = duplicate;
    }
}
