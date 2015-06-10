package com.websushibar.hprofpersist.hprofentries.layout;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.BasicTypeTag;
import com.websushibar.hprofpersist.hprofentries.dumpSubtags.InstanceDump;
import org.apache.commons.lang3.ArrayUtils;

import java.util.HashMap;
import java.util.Map;

public class InstanceLayout {

    private int offset;
    protected int length;

    protected Map<String, Integer> offsetByField = new HashMap<>();
    protected Map<String, BasicTypeTag> baseTypeByField = new HashMap<>();
    protected InstanceLayout parentLayout;

    protected InstanceLayout() {
    }


    public IDField getObjIdField(InstanceDump dump, String field) {

        if (!offsetByField.keySet().contains(field)) {
            if (parentLayout == null)
                throw new IllegalArgumentException("Field not found " + field);
            return parentLayout.getObjIdField(dump, field);
        }

        checkFieldTypeOrException(field, BasicTypeTag.OBJECT);

        byte[] idBytes = ArrayUtils.subarray(dump.getValues(),
                offset + offsetByField.get(field),
                IDField.getSize());

        return IDField.fromByteArray(idBytes);
    }

    public Long getLongField(InstanceDump dump, String field) {

        if (!offsetByField.keySet().contains(field)) {
            if (parentLayout == null)
                throw new IllegalArgumentException("Field not found " + field);

            return parentLayout.getLongField(dump, field);
        }

        checkFieldTypeOrException(field, BasicTypeTag.LONG);

        byte[] bytes = ArrayUtils.subarray(dump.getValues(),
                offset + offsetByField.get(field),
                Longs.BYTES);

        return Longs.fromByteArray(bytes);
    }

    public Integer getIntField(InstanceDump dump, String field) {

        if (!offsetByField.keySet().contains(field)) {
            if (parentLayout == null)
                throw new IllegalArgumentException("Field not found " + field);

            return parentLayout.getIntField(dump, field);
        }

        checkFieldTypeOrException(field, BasicTypeTag.INT);

        byte[] bytes = ArrayUtils.subarray(dump.getValues(),
                offset + offsetByField.get(field),
                Ints.BYTES);

        return Ints.fromByteArray(bytes);
    }


    protected void checkFieldTypeOrException(String field, BasicTypeTag typeTag)
            throws IllegalArgumentException {
        if (!baseTypeByField.get(field).equals(typeTag))
            throw new IllegalArgumentException("Field " + field + " not of " + typeTag + " type!");
    }

    protected void setParentLayout(InstanceLayout parentLayout) {
        this.parentLayout = parentLayout;
        offset = parentLayout.length;
    }

    public int getStartPos() { return offset; }

    public int getEndPos() {return offset + length; }

    public int getOffset() {
        return offset;
    }

    public int getLength() {
        return length;
    }

}
