package com.ccgautomation.servlets;

import com.ccgautomation.trends.MyAnalogTrendProcessor;
import com.ccgautomation.trends.MyAnalogTrendSample;
import com.ccgautomation.utilities.DateTools;
import com.ccgautomation.utilities.TrendTools;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// https://grafana.com/tutorials/build-a-data-source-plugin/

// https://happycoding.io/tutorials/java-server/rest-api - Rest Servlet
// https://stackoverflow.com/questions/31657641/tomcat-restful-web-service-deployment - REST Tomcat Servlet
// http://alcshare.com/content/add-on-tutorial/trends.html
// https://github.com/alclabs/ZoneHistory/blob/master/src/main/java/com/controlj/addon/zonehistory/reports/EnvironmentalIndexProcessor.java
// https://github.com/alclabs/ZoneHistory/blob/master/src/main/java/com/controlj/addon/zonehistory/reports/EnvironmentalIndexReport.java
// https://www.javaexercise.com/java/list-of-timezone-id-of-all-countries-in-java
// ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend

public class TrendService extends HttpServlet {

    private static final int startDateIndex = 1;
    private static final int endDateIndex = 2;
    private static final int trendIdListIndex = 3;
    private static final String defaultTimeZone = "EST";
    private static final String ABSPATH  = "ABSPATH:1:#";
    private static String[] ids = {"ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend"};
    private static final Date startDate = new Date(1664582400000L);
    private static final Date endDate = new Date(1664668800000L);
    private static final int BAD_RESPONSE = 400;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            DateTools dateTools = new DateTools(TimeZone.getTimeZone(defaultTimeZone));
            String requestUrl = request.getRequestURI();

            String[] fields = requestUrl.split("/");
            Date startDate = convertStringToDate(dateTools, fields, startDateIndex);
            Date endDate = convertStringToDate(dateTools, fields, endDateIndex);
            String[] ids = fields[trendIdListIndex].split(",");

            response.setContentType("application/json");
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Writer writer = response.getWriter();
            //disableCache(response);
            List<MyAnalogTrendSample> results = new TrendTools().doWork(Arrays.asList(ids), new MyAnalogTrendProcessor(), startDate, endDate);
            writer.write(gson.toJson(results));
        }
        catch (Exception e)
        {
            response.setContentType("text/plain");
            if (!response.isCommitted()) {
                response.sendError(BAD_RESPONSE, e.getMessage());
            }
        }
    }

    private Date convertStringToDate(DateTools dateTools, String[] fields) {
        try {
            return dateTools.convertStringToDate(dateString);
        }
        catch(ParseException ex) {
            //TODO Log Exception
            return null;
        }
    }
}
