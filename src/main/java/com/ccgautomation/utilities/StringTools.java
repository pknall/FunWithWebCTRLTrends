package com.ccgautomation.utilities;

import java.util.Arrays;

public class StringTools {

    public String toCSV(String[] values) {
        StringBuilder sb = new StringBuilder();
        Arrays.asList(values).stream().forEach(e -> { sb.append(e); sb.append(",");});
        return sb.toString().substring(0, sb.toString().length()-1);
    }
}
