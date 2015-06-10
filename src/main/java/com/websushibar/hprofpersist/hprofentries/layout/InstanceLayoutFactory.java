package com.websushibar.hprofpersist.hprofentries.layout;

import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.LoadClass;
import com.websushibar.hprofpersist.hprofentries.StringEntry;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.ClassDump.InstanceField;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;
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

    public InstanceLayoutFactory(HPROFStore store, LoadClass loadClass) {
        this.store = store;

        classHierarchy = new ArrayList<>();
        IDField classObjId = loadClass.getClassObjId();
        classHierarchy.add(tryGetClass(classObjId));
        addAncestors();
    }

    public InstanceLayoutFactory(HPROFStore store, ClassDump classDump) {
        this.store = store;

        classHierarchy = new ArrayList<>();
        this.classHierarchy.add(classDump);
        addAncestors();
    }

    public InstanceLayoutFactory(HPROFStore store, IDField classObjId) {
        this.store = store;

        classHierarchy = new ArrayList<>();
        classHierarchy.add(tryGetClass(classObjId));
        addAncestors();
    }

    public InstanceLayout buildInstanceLayout() {
        InstanceLayout retVal = new InstanceLayout();

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
                StringEntry fieldNameEntry = store.getObject(field.getNameStringId());

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
        if (store.getObject(id) == null) {
            throw new HPROFFormatException(
                    "ObjectId for a class refers to non-existent entry!\n" + id );
        }

        try {
            return (ClassDump)store.getObject(id);
        } catch (ClassCastException e) {
            throw new HPROFFormatException(
                    "ObjectId refers to a non-ClassDump entry!\n" + id );
        }
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

}
