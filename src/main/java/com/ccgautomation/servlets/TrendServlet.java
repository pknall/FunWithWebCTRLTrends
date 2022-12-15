package com.ccgautomation.servlets;

import com.ccgautomation.trends.MyAnalogTrendProcessor;
import com.ccgautomation.trends.MyAnalogTrendSample;
import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.access.aspect.AnalogTrendSource;
import com.controlj.green.addonsupport.access.aspect.TrendSource;
import com.controlj.green.addonsupport.access.trend.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.*;

// https://grafana.com/tutorials/build-a-data-source-plugin/

// https://happycoding.io/tutorials/java-server/rest-api - Rest Servlet
// https://stackoverflow.com/questions/31657641/tomcat-restful-web-service-deployment - REST Tomcat Servlet
// http://alcshare.com/content/add-on-tutorial/trends.html
// https://github.com/alclabs/ZoneHistory/blob/master/src/main/java/com/controlj/addon/zonehistory/reports/EnvironmentalIndexProcessor.java
// https://github.com/alclabs/ZoneHistory/blob/master/src/main/java/com/controlj/addon/zonehistory/reports/EnvironmentalIndexReport.java

public class TrendServlet extends HttpServlet {

    // ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend
    private static final String ABSPATH  = "ABSPATH:1:#";
    private static String[] ids = {"ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend"};
    private static final Date startDate = new Date(1664582400000L);
    private static final Date endDate = new Date(1664668800000L);
    private static final int BAD_RESPONSE = 400;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try
        {
            String requestUrl = request.getRequestURI();
            String[] fields = requestUrl.split("/");
            response.setContentType("application/json");
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            Writer writer = response.getWriter();
            //disableCache(response);
            TrendRange range = TrendRangeFactory.byDateRange(startDate, endDate);
            List<MyAnalogTrendSample> results = doWork(Arrays.asList(ids), new MyAnalogTrendProcessor(), range);
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

    private List<MyAnalogTrendSample> doWork(final List<String> ids,
                                             final MyAnalogTrendProcessor myAnalogTrendProcessor,
                                             final TrendRange range) throws SystemException, ActionExecutionException {
        final List<MyAnalogTrendSample> results = new ArrayList<>();
        SystemConnection connection = DirectAccess.getDirectAccess().getRootSystemConnection();

        // Converted to lambda:
        // connection.runReadAction(FieldAccessFactory.newFieldAccess(), new ReadAction() {
        //    public void execute(@NotNull SystemAccess access) {
        connection.runReadAction(FieldAccessFactory.newFieldAccess(), access -> {
            Tree geo = access.getTree(SystemTree.Geographic);
            ids.stream().forEach(id -> {
                try {
                    Location location = getTreeLocationByReferenceName(geo, id);
                    TrendSource trendSource = getTrendSourceAtLocation(location);
                    TrendSource.Type type = trendSource.getType();
                    if (type == TrendSource.Type.Analog) {
                        TrendData<TrendAnalogSample> analogData = (((AnalogTrendSource) trendSource).getTrendData(range));
                        results.addAll(getTrendSources(analogData, myAnalogTrendProcessor));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        return results;
    }

    private List<MyAnalogTrendSample> getTrendSources(TrendData<TrendAnalogSample> analogData,
                                                      MyAnalogTrendProcessor myAnalogTrendProcessor){
        try {
            return analogData.process(myAnalogTrendProcessor).getSamples();
        } catch (TrendException e) {
            // Log Error
            throw new RuntimeException("Unable to process Trend Data: " + e);
        }
    }

    private TrendSource getTrendSourceAtLocation(Location location) {
        try {
            return location.getAspect(TrendSource.class);
        } catch (NoSuchAspectException e) {
            // Log Error
            throw new RuntimeException("Unable to retrieve trend source at " + location + " + : " + e);
        }
    }

    private Location getTreeLocationByReferenceName(Tree tree, String id) {
        try {
            return tree.resolve(id.trim());
        } catch (UnresolvableException e) {
            // Log Error
            throw new RuntimeException("Unable to find tree location at " + id + " : " + e);
        }
    }
}
