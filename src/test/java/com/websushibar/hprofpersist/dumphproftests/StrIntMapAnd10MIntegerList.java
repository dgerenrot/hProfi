package com.websushibar.hprofpersist.dumphproftests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

public class StrIntMapAnd10MIntegerList {

    private static final int PERIOD = 5000;
    private static final int N = 10000000;


    // @Test
    public void testCoreDump() throws InterruptedException {

        out.println("Starting. . .");
        assertTrue("Starting. . . ", true);

        List<Integer> ints = new ArrayList<Integer>();

        HashMap<String, Integer> intMap = new HashMap<String, Integer>();

        Random rng = new Random();

        for (int i = 1; i < N; i++) {
            ints.add(rng.nextInt(i));
        }

        int count = 1;
        for (int i : ints) {
            intMap.put(i + "_", i * i);
            if (count++ % PERIOD == 0) {
                Thread.sleep(100);
                assertTrue("running. . . ", true);
            }
        }
    }
}