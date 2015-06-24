package com.websushibar.hprofpersist.dumphproftests;

import com.websushibar.hprofpersist.dumphproftests.dummyclasses.ClassAIntAndStr;
import com.websushibar.hprofpersist.dumphproftests.dummyclasses.ClassAReference;


/**
 * A program intended to generate a sample heap dump for testing.
 * command:
 * java -agentlib:hprof=format=b,doe=y,heap=all,file=$CLASSNAME.hprof  \
 *    com/websushibar/hprofpersist/dumphproftests/$CLASSNAME
 */
public class ClassARefAndClassA {

    public static void main(String[] args) {
        ClassAIntAndStr ca1 = new ClassAIntAndStr(42, "classARefName");
        ClassAReference car = new ClassAReference(ca1);

        for (int i = 0; i < 500; i++) {

            System.out.println(car.getClassAIntAndStr() + "");
            System.out.println(car.getClassAIntAndStr().hashCode() + "");

                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {

                }
        }

    }
}
