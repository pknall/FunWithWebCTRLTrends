package com.ccgautomation.utilities;

import junit.framework.TestCase;

public class StringToolsTest extends TestCase {

    public void testToCSV() {
        StringTools stringTools = new StringTools();
        System.out.println(stringTools.toCSV("1,2,3,4,5,6".split(",")));
    }
}