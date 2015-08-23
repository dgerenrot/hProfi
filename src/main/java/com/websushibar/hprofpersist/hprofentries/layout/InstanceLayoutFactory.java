package com.websushibar.hprofpersist.hprofentries.layout;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.LoadClass;
import com.websushibar.hprofpersist.hprofentries.StringEntry;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump.InstanceField;
import com.websushibar.hprofpersist.store.HPROFStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class InstanceLayoutFactory {

    protected HPROFStore store;

    protected List<ClassDump> classHierarchy;

    protected HashMap<String, Integer> byteOffsetsByFieldName;
    protected HashMap<String, BasicTypeTag> typesByFieldName;

    protected IDField classObjId;
    protected ClassDump classDump;
    protected LoadClass loadClass;

    public InstanceLayoutFactory(HPROFStore store, LoadClass loadClass) {
        this.store = store;

        classHierarchy = new ArrayList<>();

        this.loadClass = loadClass;
        this.classObjId = loadClass.getClassObjId();
        this.classDump = store.getClassDump(classObjId);

        classHierarchy.add(classDump);
        addAncestors();
    }

    public InstanceLayoutFactory(HPROFStore store, ClassDump classDump) {
        this.store = store;

        classHierarchy = new ArrayList<>();

        this.classObjId = classDump.getId();
        this.classDump = classDump;
        this.loadClass = store.getLoadClass(classObjId);

        this.classHierarchy.add(classDump);
        addAncestors();
    }

    public InstanceLayoutFactory(HPROFStore store, IDField classObjId) {
        this.store = store;

        classHierarchy = new ArrayList<>();

        this.classObjId = classObjId;
        this.classDump = store.getClassDump(classObjId);
        this.loadClass = store.getLoadClass(classObjId);

        this.classHierarchy.add(classDump);
        addAncestors();
    }

    public InstanceLayout buildInstanceLayout() {
        InstanceLayout retVal = new InstanceLayout();

        retVal.setClassDump(classDump);
        retVal.setLoadClass(loadClass);
        retVal.setClassObjId(classObjId);

        InstanceLayout currLayout = retVal;

        for (int i = 0; i < classHierarchy.size(); i++) {

            if (i > 0) {
                InstanceLayout parent = new InstanceLayout();
                currLayout.setParentLayout(parent);
                currLayout = parent;
            }

            ClassDump currClass = classHierarchy.get(i);
            Collection<InstanceField> instanceFields = currClass.getInstanceFields();

            int currOffset = 0;
            for (InstanceField field : instanceFields) {
                StringEntry fieldNameEntry = store.getString(field.getNameStringId());

                currLayout.offsetByField.put(fieldNameEntry.getContent(), currOffset);
                currOffset += field.getTypeOfEntry().getSize();

                currLayout.baseTypeByField.put(fieldNameEntry.getContent(),
                                               field.getTypeOfEntry());

                currLayout.length += field.getTypeOfEntry().getSize();
            }
        }

        return retVal;
    }

    protected ClassDump tryGetClass(IDField id) {
        return store.getClassDump(id);
    }

    protected void addAncestors() {
        if (classHierarchy.size() == 0)
            return;

        while (true) {
            ClassDump curr = classHierarchy.get(classHierarchy.size() - 1);
            IDField parentId = curr.getSuperClassObjId();
            if (parentId == null || parentId.getNumeric() == 0)
                break;
            ClassDump parent = tryGetClass(parentId);
            classHierarchy.add(parent);
        }
    }

    public HPROFStore getHPROFStore() {
        return store;
    }


    public IDField getClassObjId() {
        return classObjId;
    }

    public ClassDump getClassDump() {
        return classDump;
    }

    public LoadClass getLoadClass() {
        return loadClass;
    }

}
