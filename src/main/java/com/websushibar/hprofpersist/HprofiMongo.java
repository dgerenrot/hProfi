package com.websushibar.hprofpersist;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import com.websushibar.hprofpersist.loader.HPROFInStreamLoader;
import com.websushibar.hprofpersist.persistence.mongo.repos.InstanceDumpRepo;
import com.websushibar.hprofpersist.store.HPROFMemoryStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

@Component
public class HprofiMongo {

    public static AnnotationConfigApplicationContext context;

    protected static String hprofFile;

    @Autowired
    public InstanceDumpRepo dumpRepo;

    public static void main(String[] args)
            throws IOException {

        cmdLineParamsInit(args);

        context = new AnnotationConfigApplicationContext();
        context.scan("com.websushibar.hprofpersist");
        context.refresh();

        InputStream is = new FileInputStream(hprofFile);

        HprofiMongo mainObj = context.getBean(HprofiMongo.class);

        if (mainObj.dumpRepo == null) {
            throw new RuntimeException("Damn!!! >:[");
        }

        mainObj.loadIntoMongo(is);
    }

    public void loadIntoMongo(InputStream is)
            throws IOException {
        HPROFInStreamLoader loader = new HPROFInStreamLoader(is);
//        HPROFStore store = new MongoStorage();
        HPROFMemoryStore store = new HPROFMemoryStore();
        loader.loadInto(store);

        ((HPROFMemoryStore)store).initDumpSubtags();

        Iterator<IDField> iter = store.getStorage(InstanceDump.class).keySet().iterator();


        for (int i = 0; i < 2; i++) {

            IDField id = iter.next();
            InstanceDump instDump = store.getStorage(InstanceDump.class).get(id);
            dumpRepo.save(instDump);
        }

    }

    protected static void cmdLineParamsInit(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("Supply the filename!");

        hprofFile = args[0];
    }

}
