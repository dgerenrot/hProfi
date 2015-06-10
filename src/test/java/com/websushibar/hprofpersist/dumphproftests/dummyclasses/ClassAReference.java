package com.websushibar.hprofpersist.dumphproftests.dummyclasses;

public class ClassAReference {
    private ClassAIntAndStr classAIntAndStr;



    public ClassAIntAndStr getClassAIntAndStr() {
        return classAIntAndStr;
    }

    public void setClassAIntAndStr(ClassAIntAndStr classAIntAndStr) {
        this.classAIntAndStr = classAIntAndStr;
    }

    public ClassAReference(ClassAIntAndStr classAIntAndStr) {
        this.classAIntAndStr = classAIntAndStr;
    }


}
