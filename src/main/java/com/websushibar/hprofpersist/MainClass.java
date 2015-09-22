package com.websushibar.hprofpersist;

import com.websushibar.hprofpersist.persistence.mongo.repos.ClassDumpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static java.lang.System.in;
import static java.lang.System.out;
import static java.util.Arrays.asList;


@Component
public class MainClass {

    private static Dummy[] DUMMY_DATA =
            new Dummy[] {
                    new Dummy ("Ted", 10),
                    new Dummy ("Bob", 20),
                    new Dummy ("Dud", 11),
                    new Dummy  ("Hun", 19)
            };

    public static AnnotationConfigApplicationContext context;

    @Autowired
    public DummyRepo dummyRepo;

    @Autowired
    public ClassDumpRepo classDumpRepo;

    public static void main(String[] args)
            throws IOException {

        context = new AnnotationConfigApplicationContext();

        context.scan("com.websushibar.hprofpersist");
        context.refresh();

        MainClass test = context.getBean(MainClass.class);

//        DummyConverter dc = context.getBean(DummyConverter.class);
//        MappingMongoConverter mmc = context.getBean(MappingMongoConverter.class);
//        dc.setMappingMongoConverter(mmc);


         test.doMain(args);
    }


    private void doMain(String[] args) throws IOException {

//        MongoOperations opers = context.getBean(MongoOperations.class);

//        for (Dummy dummy : DUMMY_DATA) {
//            opers.insert(dummy, TEST_COLLECTION);
//        }

        dummyRepo.save(asList(DUMMY_DATA));
        List<Dummy> aged11 = dummyRepo.findByAge(11);
        out.println("Aged 11 : " + aged11.size());

        out.println("Press a key");
        in.read();

        dummyRepo.delete("Dud");
        aged11 = dummyRepo.findByAge(11);

        out.println("Aged 11 : " + aged11.size());
        out.println("Press a key");

        in.read();

        out.println("all done");
    }

}
