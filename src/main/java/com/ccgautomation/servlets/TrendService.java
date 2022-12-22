package com.ccgautomation.servlets;

import com.ccgautomation.trends.MyAnalogTrendProcessor;
import com.ccgautomation.trends.MyAnalogTrendSample;
import com.ccgautomation.utilities.*;
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

    private final Logger logThis = new Logger(TrendService.class.getName());
    private final int START_DATE_INDEX = 3;
    private final int END_DATE_INDEX = 4;
    private final int TREND_ID_INDEX = 5;
    private final String DEFAULT_TIME_ZONE = "EST";
    private final String DEFAULT_DATE_PATTERN = "MMddyyyy";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        logThis.trace("Entering TrendService");
        String requestURI = request.getRequestURI();
        logThis.trace("RequestURI: " + requestURI);
        String[] requestURIArray = requestURI.split("/");
        Date startDate = getDateFromStringArrayAtIndex(requestURIArray, START_DATE_INDEX);
        Date endDate = getDateFromStringArrayAtIndex(requestURIArray, END_DATE_INDEX);
        String[] ids = getIdsFromStringArrayAtIndex(requestURIArray, TREND_ID_INDEX);
        logThis.trace("Provided Values: " + startDate + " : " + endDate + " : " + new StringTools().toCSV(ids));
        List<MyAnalogTrendSample> results = getTrendResults(ids, startDate, endDate);
        logThis.trace("Results returned: " + results.size());
        writeResultsToResponse(results, response);
        logThis.trace("Leaving TrendService");
    }

    private List<MyAnalogTrendSample> getTrendResults(String[] ids, Date startDate, Date endDate) {
        logThis.trace("Entering TrendService.getTrendResults");
        List<MyAnalogTrendSample> results = null;
        try {
            results = new TrendTools().doWork(Arrays.asList(ids), new MyAnalogTrendProcessor(), startDate, endDate);
        } catch (SystemException e) {
            logThis.error("TrendService.getTrendResults: " + e.getMessage());

        } catch (ActionExecutionException e) {
            logThis.error("TrendService.getTrendResults: " + e.getMessage());
        }
        logThis.trace("Exiting TrendService.getTrendResults");
        return results;
    }

    private void writeResultsToResponse(List<MyAnalogTrendSample> results, HttpServletResponse response) {
        Writer writer = null;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            logThis.error("TrendService.writeResultsToResponse: Problem with getWriter(): " + e.getMessage());
        }

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // Disable Browser Cache for HTTP 1.1.
        //response.setHeader("Pragma", "no-cache"); // Disable Browser Cache for HTTP 1.0.
        //response.setDateHeader("Expires", 0); // Disable Browser Cache for Proxies.

        if ((results == null) || (results.size() == 0)) {
            response.setContentType("plain/text");
            try {
                writer.write("No results returned.");
            } catch (IOException e) {
                logThis.error("TrendService.writeResultsToResponse: Problem with writer.write(No results returned): " + e.getMessage());
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
                logThis.error("TrendService.writeResultsToResponse: Problem with writer.write(toJson()): " + e.getMessage());
            }
        }
    }

    private Date getDateFromStringArrayAtIndex(String[] fields, int index) {
        DateTools dateTools = new DateTools(DEFAULT_DATE_PATTERN, TimeZone.getTimeZone(DEFAULT_TIME_ZONE));
        try {
            return dateTools.convertStringToDate(fields[index]);
        }
        catch(ParseException e) {
            logThis.error("TrendService.getDateFromStringArrayAtIndex: " + e.getMessage());
            return null;
        }
    }

    protected String[] getIdsFromStringArrayAtIndex(String[] fields, int index) {
        String[] results = null;
        try {
            String value = fields[5];
            value = value.replace("!", "/");
            results = value.split(",");
        }
        catch (Exception e) {
            logThis.error("TrendService.getIdsFromStringArrayAtIndex: " + e.getMessage());
            return null;
        }
        return results;
    }
}
