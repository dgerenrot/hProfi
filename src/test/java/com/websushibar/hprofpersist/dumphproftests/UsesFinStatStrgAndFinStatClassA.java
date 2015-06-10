package com.websushibar.hprofpersist.dumphproftests;


public class UsesFinStatStrgAndFinStatClassA {

    public static void main(String[] args) {
        System.out.println("Running " + UsesFinStatStrgAndFinStatClassA.class);

        UsesFinStatStrgAndFinStatClassA u42 = new UsesFinStatStrgAndFinStatClassA() {
            public String name = "StringIAmU42";
        };

        UsesFinStatStrgAndFinStatClassA u43 = new UsesFinStatStrgAndFinStatClassA() {
            public String name = "StringIAmU43";

            public String getName() {return name;}
        };



        // System.in.read();
    }
}
