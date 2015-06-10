/**
 * <p>
 * Programs intended to generate sample heap dumps for testing.
 * Command:
 * </p>
 * <pre>
 *   java -agentlib:hprof=format=b,doe=y,heap=all,file=$CLASSNAME.hprof  \
 *         com/websushibar/hprofpersist/dumphproftests/$CLASSNAME
 * </pre>
 */
package com.websushibar.hprofpersist.dumphproftests;