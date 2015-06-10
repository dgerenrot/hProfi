package com.websushibar.hprofpersist.dumphproftests.dummyclasses;

public class ClassAIntAndStr {
    private int intField;
    private String stringField;

    public ClassAIntAndStr(int intField, String stringField) {
        this.intField = intField;
        this.stringField = stringField;
    }

    public String toString() {
        return stringField + " " + intField;
    }
}
