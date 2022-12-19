package com.ccgautomation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
 * Form used to test TrendService
 */

public class TrendServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<title>Trend Client</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");
        stringBuilder.append("<form name='trendForm' method='post' action=''>");
        stringBuilder.append("Trend List: <input type='text' name='trendList' id='trendList' /><br />");
        stringBuilder.append("Start Date: <input type='text' name='startDate' id='startDate' /><br />");
        stringBuilder.append("End Date: <input type='text' name='endDate' id='endDate' /><br />");
        stringBuilder.append("<button type='submit' value='Get Trends' /><br />");
        stringBuilder.append("<hr /><br />");
        stringBuilder.append("</form>");
        stringBuilder.append("<div id='data'></div>");
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");

        response.getWriter().write(stringBuilder.toString());
    }
}
