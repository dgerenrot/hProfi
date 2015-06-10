package com.websushibar.hprofpersist.hprofentries;

import com.google.common.primitives.Longs;
import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;

import java.io.Serializable;
import java.nio.ByteBuffer;


public class IDField implements Serializable {

    public static final int LONG_BYTES = 8;

    protected static final byte[][] PAD_BYTE_ARR = {
            new byte[0],
            new byte[1],
            new byte[2],
            new byte[3],
            new byte[4],
            new byte[5],
            new byte[6],
            new byte[7]
    };

    public static IDField NULL_ID;

    private static Integer size;
    protected static byte[] leftPadding;

    private byte[] bytes;

    public IDField(byte[] bytes) {
        if (bytes == null) {
            throw new NullPointerException("bytes == null in" + getClass().getName());
        }

        if (bytes.length > size) {
            throw new HPROFFormatException("Byte array too large: " + bytes.length
                                            + "Expected size " + size);
        }

        this.bytes = new byte[size];

        System.arraycopy( bytes, 0, this.bytes, this.bytes.length - bytes.length, bytes.length);
    }

    public IDField(long number) {
        bytes = new byte[size];
        System.arraycopy(Longs.toByteArray(number), Longs.BYTES - size, bytes, 0, size);
    }


    public static int getSize() {
        return IDField.size;
    }

    public static IDField fromByteArray(byte[] bytes) {
        return new IDField(bytes);
    }

    public Long getNumeric() {
        ByteBuffer bb = ByteBuffer
                .allocate(LONG_BYTES)
                .put(PAD_BYTE_ARR[LONG_BYTES - bytes.length])
                .put(bytes);
        bb.position(0);
        return bb.getLong();
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return getNumeric().toString();
    }

    @Override
    public int hashCode() {
        return getNumeric().hashCode();
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof IDField))
            return false;
        return this.getNumeric().equals(((IDField)other).getNumeric());
    }

    /**
     *
     * @param size
     */
    public static void setSize(int size) {
        IDField.size = size;
        leftPadding = new byte[LONG_BYTES - size];

        NULL_ID = fromByteArray(new byte[size]);
    }
}
