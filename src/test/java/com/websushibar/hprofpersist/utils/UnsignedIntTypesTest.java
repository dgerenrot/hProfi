package com.websushibar.hprofpersist.utils;

import org.junit.Test;

import static com.websushibar.hprofpersist.utils.UnsignedIntTypes.unsignedByteVal;
import static com.websushibar.hprofpersist.utils.UnsignedIntTypes.unsignedIntVal;
import static com.websushibar.hprofpersist.utils.UnsignedIntTypes.unsignedShortVal;
import static org.junit.Assert.assertEquals;

public class UnsignedIntTypesTest {

    @Test
    public void testUnsignedMaxByte() {
        byte x = (byte) 0xFF;
        assertEquals(-1, x);
        assertEquals(255, unsignedByteVal(x));
    }

    @Test
    public void testUnsignedMaxShort() {
        short x = (short) 0xFFFF;
        assertEquals(-1, x);
        assertEquals((1 << 16) - 1, unsignedShortVal(x));
    }

    @Test
    public void testUnsignedMaxInt() {
        int x = 0xFFFFFFFF;
        assertEquals(-1, x);
        assertEquals((1L << 32) - 1, unsignedIntVal(x));

        // the shift wraps up!
        assertEquals((1L << 66), 4L);
    }

}