package com.websushibar.hprofpersist.utils;

public class UnsignedIntTypes {

    public static final short MAX_UBYTE = 0xFF;
    public static final int MAX_USHORT = 0xFFFF;
    public static final long MAX_UINT = 0xFFFFFFFFL;

    public static final short SHORT2BYTE_MASK = (short)0xFF00;
    public static final int INT2SHORT_MASK = 0xFFFF0000;
    public static final long LONG2INT_MASK = 0xFFFFFFFF00000000L;

    public static short unsignedByteVal(byte x) {
        return (short)((SHORT2BYTE_MASK | x) & MAX_UBYTE);
    }
    public static int unsignedShortVal(short x) {
        return (INT2SHORT_MASK | x) & MAX_USHORT;
    }
    public static long unsignedIntVal(int x) {
        return (LONG2INT_MASK | x) & MAX_UINT;
    }
}
