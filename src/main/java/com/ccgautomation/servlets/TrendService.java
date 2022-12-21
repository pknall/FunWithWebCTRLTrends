package com.ccgautomation.servlets;

import com.ccgautomation.trends.MyAnalogTrendProcessor;
import com.ccgautomation.trends.MyAnalogTrendSample;
import com.ccgautomation.utilities.DateTools;
import com.ccgautomation.utilities.TrendTools;
import com.controlj.green.addonsupport.access.ActionExecutionException;
import com.controlj.green.addonsupport.access.SystemException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
// https://stackoverflow.com/questions/3413036/http-response-caching

public class TrendService extends HttpServlet {

    private static final int startDateIndex = 1;
    private static final int endDateIndex = 2;
    private static final int trendIdListIndex = 3;
    private static final String defaultTimeZone = "EST";
    private static final String DEFAULT_DATE_PATTERN = "MMddyyyy";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        String[] requestURIArray = requestURI.split("/");
        Date startDate = getDateFromStringArrayAtIndex(requestURIArray, startDateIndex);
        Date endDate = getDateFromStringArrayAtIndex(requestURIArray, endDateIndex);
        String[] ids = getIdsFromStringArrayAtIndex(requestURIArray, trendIdListIndex);
        List<MyAnalogTrendSample> results = getTrendResults(ids, startDate, endDate);
        writeResultsToResponse(results, response);
    }

    private List<MyAnalogTrendSample> getTrendResults(String[] ids, Date startDate, Date endDate) {
        List<MyAnalogTrendSample> results = null;
        try {
            results = new TrendTools().doWork(Arrays.asList(ids), new MyAnalogTrendProcessor(), startDate, endDate);
        } catch (SystemException e) {
            //TODO Log Exception
            // throw new RuntimeException(e);
        } catch (ActionExecutionException e) {
            //TODO Log Exception
            // throw new RuntimeException(e);
        }
        return results;
    }

    private void writeResultsToResponse(List<MyAnalogTrendSample> results, HttpServletResponse response) {
        Writer writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            //TODO Log Exception
            //throw new RuntimeException(e);
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // Disable Browser Cache for HTTP 1.1.
        //response.setHeader("Pragma", "no-cache"); // Disable Browser Cache for HTTP 1.0.
        //response.setDateHeader("Expires", 0); // Disable Browser Cache for Proxies.

        if ((results == null) || (results.size() == 0)) {
            response.setContentType("plain/text");
            try {
                writer.write("No results returned.");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }

        response.setContentType("application/json");
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        if (writer != null) {
            try {
                writer.write(gson.toJson(results));
            } catch (IOException e) {
                //TODO Log Exception
                //throw new RuntimeException(e);
            }
        }
    }

    private Date getDateFromStringArrayAtIndex(String[] fields, int index) {
        DateTools dateTools = new DateTools(DEFAULT_DATE_PATTERN, TimeZone.getTimeZone(defaultTimeZone));
        try {
            return dateTools.convertStringToDate(fields[index]);
        }
        catch(ParseException ex) {
            //TODO Log Exception
            return null;
        }
    }

    private String[] getIdsFromStringArrayAtIndex(String[] fields, int index) {
        try {
            return fields[index].split(",");
        }
        catch (Exception ex) {
            return null;
        }
    }
}
