package com.ccgautomation.servlets;

import junit.framework.TestCase;

import java.util.Arrays;

public class TrendServiceTest extends TestCase {

    // RequestURI:  /funwithwebctrltrends/trendservice/11012022/12012022/wp_ccg_fpvav-1-11/max_heating_cfm_trend
    public void testGetIdsFromStringArrayAtIndex() {
        String requestURI = "/funwithwebctrltrends/trendservice/11012022/12012022/wp_ccg_fpvav-1-11!max_heating_cfm_trend,wp_ccg_fpvav-1-11!max_heating_cfm_trend";
        String[] fields = requestURI.split("/");
        TrendService trendService = new TrendService();
        String[] results = trendService.getIdsFromStringArrayAtIndex(fields, 5);
        if (results == null) System.out.println("Results are null");
        else {
            Arrays.asList(results).stream().forEach(e -> System.out.println(e));
        }
    }
}