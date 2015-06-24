package com.websushibar.hprofpersist.runhproftests;

import com.google.common.base.Function;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.LoadClass;
import com.websushibar.hprofpersist.hprofentries.StringEntry;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.DumpSubtagEntry;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import com.websushibar.hprofpersist.hprofentries.layout.InstanceLayout;
import com.websushibar.hprofpersist.hprofentries.layout.InstanceLayoutFactory;
import com.websushibar.hprofpersist.loader.HPROFInStreamLoader;
import com.websushibar.hprofpersist.store.HPROFMemoryStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;
import static com.websushibar.hprofpersist.utils.Utils.isOfClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClassARefAndClassATest {

    //MemStoreArrayAndListOfClassATest
    public static final String USAGE_ERROR
            = "Path to hprofs and hprof file name required!";

    public static final String FILE_MISSING_ERROR
            = "Did you place the .hprof in src/test/resources/ ? File name: ";

    public static final String TEST_RES_DIR = "src\\test\\resources\\";

    public static final String ARRAY_AND_LIST_OF_CLASS_A = "ArrayAndListOfClassA.hprof";

    public static final String CLASS_A_REF_4 = "ClassARefAndClassA_4.hprof";
    public static final String CLASS_A_REF_0 = "ClassARefAndClassA.hprof";

    public static final String CLASS_UNDER_TEST = "ClassAIntAndStr";

    File hprofUnderTest;


    @Test
    public void shouldFindStringMyObjStringInARR_AND_LIST() throws IOException {
        HPROFMemoryStore store = loadMemStore(ARRAY_AND_LIST_OF_CLASS_A);

        int count = 0;

        for (Map.Entry<IDField, StringEntry> entry : store.getStringsById().entrySet()) {
            if (entry.getValue().getContent().contains("myObj")) {
                count++;
            }
        }

        assertEquals(1, count);
    }

    @Test
    public void shouldFindStringFields_0() throws IOException {
        shouldFindStringFields(CLASS_A_REF_0);
    }

    @Test
    public void shouldFindStringFields_4() throws IOException {
        shouldFindStringFields(CLASS_A_REF_4);
    }

    private void shouldFindStringFields(String fileName) throws IOException {
        HPROFMemoryStore store = loadMemStore(fileName);

        IDField stringClassId = null;
        IDField classUnderTest = null;
        IDField referringClassId = null;

        for (Map.Entry<IDField, LoadClass> entry : store.getClassesById().entrySet()) {
            StringEntry className = store.getStringsById().get(entry.getValue().getClassNameStringId());

            if (stringClassId == null &&  className.getContent().contains("java/lang/String")) {
                stringClassId = entry.getKey();
            }
            if (stringClassId == null && className.getContent().contains("java.lang.String")) {
                stringClassId = entry.getKey();
            }

            if (className.getContent().contains(CLASS_UNDER_TEST)
                    && ! className.getContent().contains("[")) {
                classUnderTest = entry.getKey();
            }
            if (referringClassId == null && className.getContent().contains("ClassAReference")) {
                referringClassId = entry.getKey();
            }
        }

        assertNotNull(referringClassId);
        assertNotNull(classUnderTest);

        InstanceDump refClInst = null;
        DumpSubtagEntry fieldEntry = null;

        Collection<InstanceDump> instDumps = store.getInstanceDumpsById().values();


//        Collection < DumpSubtagEntry > refClContains = filter(classes, idEquals(referringClassId));
//        Collection<DumpSubtagEntry> cutContains = filter(classes, idEquals(classUnderTest));

        Collection<InstanceDump> refClDumpContains = filter(instDumps, isOfClass(referringClassId));
        Collection<InstanceDump> cutDumpContains = filter(instDumps, isOfClass(classUnderTest));
        Collection <IDField> loadClassIds = transform(store.getClassesById().values(),
                new Function<LoadClass, IDField>() {
                    @Override
                    public IDField apply(LoadClass loadClass) {
                        return loadClass.getId();
                    }
                });

        assertEquals(1, refClDumpContains.size());
        assertEquals(1, cutDumpContains.size());

        Collection<ClassDump> classDumps = store.getClassDumpById().values();
        assertEquals(classDumps.size(), loadClassIds.size());
//        assertEquals(1, count);

    }

    @Test
    public void shouldFindSubtag() throws IOException {
        HPROFInStreamLoader loader
                = new HPROFInStreamLoader(new FileInputStream(hprofUnderTest));
        HPROFMemoryStore store = new HPROFMemoryStore();

        loader.loadInto(store);
        store.initDumpSubtags();

        Collection<InstanceDump> instanceDumps = store.getInstanceDumpsById().values();
        InstanceLayoutFactory factory = getLayoutFactory(store, CLASS_UNDER_TEST);

        InstanceLayout classALayout = factory.buildInstanceLayout();

        List<String> strings = new ArrayList<>();
        List<Integer> ints = new ArrayList<>();
        for (InstanceDump instDump : instanceDumps) {
            IDField stringFieldId = classALayout.getObjIdField(instDump, "stringField");
            StringEntry stringEntry = store.getObject(stringFieldId);
            int intField = classALayout.getIntField(instDump, "intField");

            strings.add(stringEntry.getContent());
            ints.add(intField);
        }

        Assert.assertNotNull(classALayout);
    }

    private  InstanceLayoutFactory getLayoutFactory(HPROFMemoryStore store, String simpleClassName) {
        Map<IDField, ClassDump> subtagEntries = store.getClassDumpById();


        for (ClassDump classDump : subtagEntries.values()) {

            LoadClass loadClass = store.getClassesById().get(classDump.getId());

            StringEntry stringEntry = store.getObject(loadClass.getClassNameStringId());

            if (stringEntry.getContent().matches("^.*\\." + simpleClassName + "$")) {
                return new InstanceLayoutFactory(store, loadClass);
            }
        }

        throw new IllegalStateException("Could not find an instance of class undfer test " + simpleClassName);
    }


    private static File loadHprofForTest(String fileName)
            throws FileNotFoundException {
        File testHprofDir = new File(TEST_RES_DIR);

        final String hprofFileName = fileName;

        File[] hprofs = testHprofDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().equals(hprofFileName);
            }
        });

        if (hprofs.length < 1) {
            throw new FileNotFoundException(FILE_MISSING_ERROR + hprofFileName);
        }

        return hprofs[0];
    }

    private static HPROFMemoryStore loadMemStore(String fileName) throws IOException {
        File hprofUnderTest =  loadHprofForTest(fileName);
        HPROFInStreamLoader loader = new HPROFInStreamLoader(
                new FileInputStream(hprofUnderTest));
        HPROFMemoryStore store = new HPROFMemoryStore();
        loader.loadInto(store);
        store.initDumpSubtags();
        return store;
    }
}
