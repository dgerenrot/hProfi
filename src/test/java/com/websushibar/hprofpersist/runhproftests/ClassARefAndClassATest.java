package com.websushibar.hprofpersist.runhproftests;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.LoadClass;
import com.websushibar.hprofpersist.hprofentries.StringEntry;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import com.websushibar.hprofpersist.hprofentries.layout.InstanceLayout;
import com.websushibar.hprofpersist.hprofentries.layout.InstanceLayoutFactory;
import com.websushibar.hprofpersist.loader.HPROFInStreamLoader;
import com.websushibar.hprofpersist.store.HPROFMemoryStore;
import com.websushibar.hprofpersist.store.HPROFStore;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.Collection;

import static org.junit.Assert.*;

public class ClassARefAndClassATest {

    //MemStoreArrayAndListOfClassATest

    public static final String USAGE_ERROR
            = "Path to hprofs and hprof file name required!";

    public static final String FILE_MISSING_ERROR
            = "Did you place the .hprof in src/test/resources/ ? File name: ";

    public static final String TEST_RES_DIR = "src\\test\\resources\\";

    public static final String ARRAY_AND_LIST_OF_CLASS_A = "ArrayAndListOfClassA.hprof";

    /////////////////////////////////////////////////////////////////////////////////
    // Some of these hprofs have been generated by visual vm. Others
    // via java -agentlib command line :
    //    java -agentlib:hprof=format=b,doe=y,heap=all,file=$CLASSNAME.hprof  \
    //            *    com/websushibar/hprofpersist/dumphproftests/$CLASSNAME

    public static final String CLASS_A_REF_VIS_VM_4 = "ClassARefAndClassA_VisVM_4.hprof";

    // This is a bad dump file. InstanceDump byte[] values are empty.
    public static final String CLASS_A_REF_AGENTLIB_0 = "ClassARefAndClassA_Agentlib_0.hprof";

    public static final String CLASS_A_NAME = "ClassAIntAndStr";

    File hprofUnderTest;


//    @Test
//    public void shouldFindStringMyObjStringInARR_AND_LIST() throws IOException {
//        HPROFStore store = loadMemStore(ARRAY_AND_LIST_OF_CLASS_A);
//
//        int count = 0;
//
//        for (Map.Entry<IDField, StringEntry> entry : store.getStringsById().entrySet()) {
//            if (entry.getValue().getContent().contains("myObj")) {
//                count++;
//            }
//        }
//
//        assertEquals(1, count);
//    }


//    @Test
//    public void ObjOfClassARefShouldHaveRefToClassA_Agtlib_0() throws IOException {
//        ObjOfClassARefShouldHaveRefToClassA(CLASS_A_REF_AGENTLIB_0);
//    }

    @Test
    public void ObjOfClassARefShouldHaveRefToClassA_VisVM_4() throws IOException {
        ObjOfClassARefShouldHaveRefToClassA(CLASS_A_REF_VIS_VM_4);
    }

    private void ObjOfClassARefShouldHaveRefToClassA(String fileName) throws IOException {
        HPROFStore store = loadMemStore(fileName);

        IDField classUnderTestId = null;
        IDField referringClassId = null;

        Collection<LoadClass> collRefClass = store.loadClassesMatchingName("ClassAReference");

        assertEquals(1, collRefClass.size());
        referringClassId = collRefClass.iterator().next().getId();

        Collection<LoadClass> collClassA = store.loadClassesMatchingName(CLASS_A_NAME);

        for (LoadClass lc : collClassA) {
            StringEntry className = store.getString(lc.getClassNameStringId());

            if (!className.getContent().contains("[")) {
                classUnderTestId = lc.getId();
                break;
            }
        }

        assertNotNull(referringClassId);
        assertNotNull(classUnderTestId);

        Collection<InstanceDump> refClDumpContains = store.instDumps(referringClassId);
        Collection<InstanceDump> cutDumpContains = store.instDumps(classUnderTestId);

        assertEquals(1, refClDumpContains.size());
        assertEquals(1, cutDumpContains.size());

        InstanceDump refClDump = refClDumpContains.iterator().next();
        InstanceDump cutDump = cutDumpContains.iterator().next();

        assertArrayEquals(refClDump.getValues(), cutDump.getId().getBytes());
    }

    @Test
    public void classDumpsAndLoadClassIdsHaveSameSize_Agtlib_0() throws IOException {
        classDumpsAndLoadClassIdsHaveSameSize(CLASS_A_REF_AGENTLIB_0);
    }

    @Test
    public void classDumpsAndLoadClassIdsHaveSameSize_VisVM_4() throws IOException {
        classDumpsAndLoadClassIdsHaveSameSize(CLASS_A_REF_VIS_VM_4);
    }

    private void classDumpsAndLoadClassIdsHaveSameSize(String fileName) throws IOException {
        HPROFStore store = loadMemStore(fileName);
        assertEquals(store.getNumClassDumps(), store.getNumLoadClasses());
    }

    @Test
    public void shouldContainStringClassId_Agtlib_0() throws IOException {
        shouldContainStringClassId(CLASS_A_REF_AGENTLIB_0);
    }

    @Test
    public void shouldContainStringClassId_VisVM_4() throws IOException {
        shouldContainStringClassId(CLASS_A_REF_VIS_VM_4);
    }

    private void shouldContainStringClassId(String fileName) throws IOException {
        HPROFStore store = loadMemStore(fileName);

        Collection<LoadClass> coll = store.loadClassesMatchingName("lang.String");

        assertEquals(1, coll.size());

        IDField stringClassId = coll.iterator().next().getClassNameStringId();

        assertNotNull(stringClassId);
    }

    @Test
    public void shouldFindSubtag() throws IOException {
        HPROFStore store = loadMemStore(CLASS_A_REF_VIS_VM_4);

        InstanceLayoutFactory factory = getLayoutFactory(store, CLASS_A_NAME);

        InstanceLayout classALayout = factory.buildInstanceLayout();

        Assert.assertNotNull(classALayout);

        Collection<InstanceDump> instanceDumps = store.instDumps(classALayout.getClassObjId());

        assertEquals(1, instanceDumps.size());

        InstanceDump instDump = instanceDumps.iterator().next();

        IDField stringFieldId = classALayout.getObjIdField(instDump, "stringField");

        // Strings are laid out using reference to primitive arrays of characters
        // InstanceDump stringDump = store.getInstanceDump(stringFieldId);

        int intValue = classALayout.getIntField(instDump, "intField");

        //ByteBuffer byteBuff = ByteBuffer.wrap(stringDump.getValues());


        //String stringValue = StandardCharsets.ISO_8859_1.decode(byteBuff)
        //                                                 .toString();

        assertEquals(42, intValue);
    }

    private  InstanceLayoutFactory getLayoutFactory(HPROFStore store, String simpleClassName) {

        Collection<LoadClass> lcs = store.loadClassesMatchingName(simpleClassName);
        if (!lcs.isEmpty()) {
            return new InstanceLayoutFactory(store, lcs.iterator().next());
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

    private static HPROFStore loadMemStore(String fileName) throws IOException {
        File hprofUnderTest =  loadHprofForTest(fileName);
        HPROFInStreamLoader loader = new HPROFInStreamLoader(
                new FileInputStream(hprofUnderTest));
        HPROFMemoryStore store = new HPROFMemoryStore();
        loader.loadInto(store);
        store.initDumpSubtags();
        return store;
    }
}
