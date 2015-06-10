package com.websushibar.hprofpersist.dumphproftests.dummyclasses;


public class StrgAndArr3Ints {

    private String stringField;
    private int[] threePrimIntsArr;

    public StrgAndArr3Ints() {
        stringField = "StringA";
        threePrimIntsArr = new int[] {42, 43, 44};
    }

    public String getStringField() {
        return stringField;
    }

    public int[] getThreePrimIntsArr() {
        return threePrimIntsArr;
    }
}
