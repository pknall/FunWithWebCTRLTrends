package com.ccgautomation.servlets;

import com.controlj.green.addonsupport.InvalidConnectionRequestException;
import com.controlj.green.addonsupport.access.*;
import com.controlj.green.addonsupport.access.aspect.AnalogTrendSource;
import com.controlj.green.addonsupport.access.aspect.DigitalTrendSource;
import com.controlj.green.addonsupport.access.aspect.TrendSource;
import com.controlj.green.addonsupport.access.trend.*;
import org.jetbrains.annotations.NotNull;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

public class TrendServlet extends HttpServlet {

    private static final String[] ids = {"ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend"};
    private static final Date startDate = new Date(1664582400000L);
    private static final Date endDate = new Date(1664668800000L);
    private static final int BAD_RESPONSE = 400;
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try
        {
            doWork(request, response);
        }
        catch (ServletException e)
        {
            if (!response.isCommitted()) {
                response.sendError(BAD_RESPONSE, e.getMessage());
            }
        }
    }

    private void doWork(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/plain");
        //disableCache(response);

        final StringBuilder sb = new StringBuilder();
        Writer writer = response.getWriter();

        SystemConnection connection;
        connection = DirectAccess.getDirectAccess().getRootSystemConnection();
        try {
            connection.runReadAction(FieldAccessFactory.newFieldAccess(), new ReadAction() {
                public void execute(@NotNull SystemAccess access) throws Exception {
                    Tree geo = access.getTree(SystemTree.Geographic);
                    TrendRange range = TrendRangeFactory.byDateRange(startDate, endDate);

                    for (int i = 0; i < ids.length; i++) {
                        String id = ids[i].trim();
                        TrendData<? extends TrendSample> data = null;

                        try {
                            Location loc = geo.resolve(id);
                            TrendSource ts = loc.getAspect(TrendSource.class);
                            TrendSource.Type type = ts.getType();
                            data = null;

                            if (type==TrendSource.Type.Analog) {
                                data = (((AnalogTrendSource) ts).getTrendData(range));
                            }
                            else if (type==TrendSource.Type.Digital) {
                                data = (((DigitalTrendSource) ts).getTrendData(range));
                            }

                            Iterator<? extends TrendSample> it = data.getSamples();
                            while(it.hasNext()) {
                                TrendSample sample = it.next();
                                sb.append(id);
                                if (sample.getType() == TrendType.DATA) {
                                    sb.append(",");
                                    sb.append(dateFormatter.format(sample.getTime()));
                                    sb.append(",");
                                    if (sample instanceof TrendAnalogSample) {
                                        sb.append((TrendAnalogSample)sample);
                                    }
                                    if (sample instanceof TrendDigitalSample) {
                                        sb.append((TrendDigitalSample)sample);
                                    }
                                    sb.append("\r\n");
                                }
                            }
                        }
                        catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
            });
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        writer.write(sb.toString());
    }

}
