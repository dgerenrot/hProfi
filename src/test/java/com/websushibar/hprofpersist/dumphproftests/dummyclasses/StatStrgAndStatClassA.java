package com.websushibar.hprofpersist.dumphproftests.dummyclasses;

public class StatStrgAndStatClassA {
    public static String stringStatic = "IamStaticStringNotFinal";

    private static ClassAIntAndStr classAStatic =
            new ClassAIntAndStr(45, "myStaticFinalObjOfClassA");

    public static ClassAIntAndStr getClassAStatic() {
        return classAStatic;
    }
}
