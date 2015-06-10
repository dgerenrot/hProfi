package com.websushibar.hprofpersist.dumphproftests;

import com.websushibar.hprofpersist.dumphproftests.dummyclasses.ClassAIntAndStr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A program intended to generate a sample heap dump for testing.
 * command:
 * java -agentlib:hprof=format=b,doe=y,heap=all,file=$CLASSNAME.hprof  \
 *    com/websushibar/hprofpersist/dumphproftests/$CLASSNAME
 */
public class ArrayAndListOfClassA {

    private static final int N = 13;
    public static void main(String[] args) throws IOException {

        System.out.println("Running " + ArrayAndListOfClassA.class);
        List<ClassAIntAndStr> list =
                new ArrayList<ClassAIntAndStr>();

        ClassAIntAndStr[] array = new ClassAIntAndStr[N];

        for (int i = 0; i < N; i++) {
            array[i] = new ClassAIntAndStr(42 + i - 2, "myObjOfClassA" + i);
            list.add(array[i]);
        }

    }
}
