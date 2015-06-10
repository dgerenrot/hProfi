package com.websushibar.hprofpersist.dumphproftests.dummyclasses;

public class ClassBStrgAndAdd3Integers {

    private String stringField;
    private Integer[] threeIntegers;

    public ClassBStrgAndAdd3Integers() {
        stringField = "IamString";
        threeIntegers = new Integer[] {42, 43, 44};
    }

    public String getStringField() {
        return stringField;
    }

    public Integer[] getThreeIntegers() {
        return threeIntegers;
    }
}
