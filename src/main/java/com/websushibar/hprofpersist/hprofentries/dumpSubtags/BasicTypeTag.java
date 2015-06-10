package com.websushibar.hprofpersist.hprofentries.dumpSubtags;

import com.google.common.primitives.*;
import com.websushibar.hprofpersist.hprofentries.IDField;
import com.websushibar.hprofpersist.loader.DataInputStreamWrapper;

import java.io.IOException;
import java.util.HashMap;

public enum BasicTypeTag {

    OBJECT(2), // Byte size unkown until we actually parse an HPROF
    BOOLEAN(4, 1),
    CHAR(5, Chars.BYTES),
    FLOAT(6, Floats.BYTES),
    DOUBLE(7, Doubles.BYTES),
    BYTE(8, 1),
    SHORT(9, Shorts.BYTES),
    INT(10, Ints.BYTES),
    LONG(11, Longs.BYTES);

    private byte code;
    private int size;

    private static HashMap<Integer, BasicTypeTag> byValue
            = new HashMap<Integer, BasicTypeTag>();

    static {
        for (BasicTypeTag bt : values()) {
            byValue.put((int)bt.getCode(), bt);
        }
    }

    BasicTypeTag(int code) {
        this.code = (byte) code;
    }

    BasicTypeTag(int code, int size) {
        this(code);
        this.size = size;
    }

    public static BasicTypeTag readBasicTypeTag(DataInputStreamWrapper l)
        throws IOException {

        byte typeByte = l.readByte();
        return getByValue(typeByte);
    }

    public static Object readEntryValue(DataInputStreamWrapper l, BasicTypeTag type)
            throws IOException {

        switch (type) {
            case CHAR:
                return l.readChar();

            case BOOLEAN:
                return l.readBoolean();

            case BYTE:
                return l.readByte();

            case SHORT:
                return l.readShort();

            case INT:
                return l.readInt();

            case LONG:
                return l.readLong();

            case FLOAT:
                return l.readFloat();

            case DOUBLE:
                return l.readDouble();

            case OBJECT:
                return l.readId(); // TODO : U sure?

            default:
                 throw new IllegalStateException("Unknown basic type " + type);
        }
    }

    public byte getCode() {
        return code;
    }


    public int getSize() {
        if (this.equals(OBJECT)) {
            return IDField.getSize();
        }

        return size;
    }

    public static BasicTypeTag getByValue(byte value) {

        if (!byValue.keySet().contains((int)value)) {
            throw new IllegalArgumentException("Byte " + value
                    + " does not correspond to HPROF basic type!");
        }

        return byValue.get((int)value);
    }
}
