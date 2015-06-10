package com.websushibar.hprofpersist.hprofentries;

import com.websushibar.hprofpersist.hprofentries.exceptions.HPROFFormatException;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class IDFieldTest {

    @Test
    public void createId0x0FLongByteArr() {
        IDField.setSize(4);
        IDField idx0F;

        idx0F = new IDField(new byte[]{(byte)0x0f});
        assertEquals(15L, idx0F.getNumeric().longValue());

        idx0F = new IDField(new byte[]{0x00, (byte)0x0f});
        assertEquals(15L, idx0F.getNumeric().longValue());

        idx0F = new IDField(new byte[]{0x00, 0x00, 0x00, (byte)0x0f});
        assertEquals(15L, idx0F.getNumeric().longValue());
    }


    @Test
    public void createId0xFfFfFfFfLongByteArr() {
        IDField.setSize(4);
        IDField id0xFfFfFfFf;

        id0xFfFfFfFf = new IDField(new byte[]{(byte) 0xff, (byte) 0xff,
                (byte) 0xff, (byte) 0xff});
        assertEquals(0xFfFfFfFfL, id0xFfFfFfFf.getNumeric().longValue());
    }

    @Test(expected = NullPointerException.class)
    public void createWithNullBytes() {
        IDField.setSize(4);
        IDField id0xFfFfFfFf;

        id0xFfFfFfFf = new IDField(null);

    }

    @Test(expected = HPROFFormatException.class)
    public void createWithTooManyBytes() {
        IDField.setSize(1);
        IDField id0xFfFfFfFf;

        id0xFfFfFfFf = new IDField(new byte[4]);
    }


}