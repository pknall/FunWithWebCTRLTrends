package com.ccgautomation.servlets;

import junit.framework.TestCase;

public class TrendServiceTest extends TestCase {

    // wp_ccg_fpvav-1-11/max_heating_cfm_trend,wp_ccg_fpvav-1-11/min_occupied_cfm_trend
    // RequestURI:  /funwithwebctrltrends/trendservice/11012022/12012022/wp_ccg_fpvav-1-11/max_heating_cfm_trend
    public void testGetIdsFromStringArrayAtIndex() {
        String requestURI = "/funwithwebctrltrends/trendservice/11012022/12012022/wp_ccg_fpvav-1-11!max_heating_cfm_trend,wp_ccg_fpvav-1-11!max_heating_cfm_trend";
        String[] fields = requestURI.split("/");
        TrendService trendService = new TrendService();
        String[] results = trendService.getTrendIdFromStringArrayAtIndecies(fields, 5);
        assertEquals(1, results.length);
        assertEquals("wp_ccg_fpvav-1-11/max_heating_cfm_trend", results[0]);
    }
}