package com.ccgautomation.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/*
 * Form used to test TrendService
 *
 * Should this be a JSP file?
 */

public class TrendServlet extends HttpServlet {

    private static String[] ids = {"ABSPATH:1:#wp_ccg_fpvav-1-11/max_heating_cfm_trend"};
    private static final Date startDate = new Date(1664582400000L);
    private static final Date endDate = new Date(1664668800000L);
    private static final int BAD_RESPONSE = 400;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<html>\r\n");
        stringBuilder.append("<head>\n");
        stringBuilder.append("<title>Trend Client</title>\n");
        //stringBuilder.append("<script src=\"/js/GetTrends.js\" type=\"text/javascript\"></script>");
        stringBuilder.append("</head>\n");
        stringBuilder.append("<body>\n");
        stringBuilder.append("<form name='trendForm' method='post' action=''>\n");
        stringBuilder.append("Trend List: <input type='text' name='trendList' id='trendList' value='wp_ccg_fpvav-1-11/max_heating_cfm_trend' /><br />\n");
        stringBuilder.append("Start Date: <input type='text' name='startDate' id='startDate' value='11012022' /><br />\n");
        stringBuilder.append("End Date: <input type='text' name='endDate' id='endDate' value='12012022' /><br />\n");
        stringBuilder.append("<button type='button' onclick='getTrends();'>Click Me!</button><br />\n");
        stringBuilder.append("<hr />\n");
        stringBuilder.append("</form>\n");
        stringBuilder.append("<textarea ='text' id='trendData' value='blank' rows='10' columns='10'></textarea>\n");
        stringBuilder.append("</body>\n");
        stringBuilder.append("</html>\n");
        stringBuilder.append("<script type=\"text/javascript\">\n");
        stringBuilder.append("function getTrends() {\n");
        //stringBuilder.append("alert(\"I've been pressed\");\n");
        stringBuilder.append("var xhr = new XMLHttpRequest();\n");
        //stringBuilder.append("alert(\"1\");\n");
        stringBuilder.append("var startDate = document.getElementById(\"startDate\").value;\n");
        //stringBuilder.append("alert(\"2\");\n");
        stringBuilder.append("var endDate = document.getElementById(\"endDate\").value;\n");
        //stringBuilder.append("alert(\"3\");\n");
        stringBuilder.append("var trendId = document.getElementById(\"trendList\").value;\n");
        stringBuilder.append("trendId = trendId.replace(\"/\", \"!\");\n");
        stringBuilder.append("var webUrl = \"trendservice/\" + startDate + \"/\" + endDate + \"/\" + trendId;");
                //" + startDate + \"/\" + endDate + \"/\" + trend + \"");
        //stringBuilder.append("alert(webUrl);");
        //stringBuilder.append("alert(\"4\");\n");
        stringBuilder.append("xhr.onreadystatechange = function() {\n");
        //stringBuilder.append("alert(\"5\");\n");
        stringBuilder.append("if (xhr.readyState == 4) {\n");
        //stringBuilder.append("alert(\"6\");\n");
        stringBuilder.append("var result = xhr.responseText;\n");
        //stringBuilder.append("alert(\"7\");\n");
        stringBuilder.append("document.getElementById(\"trendData\").value = result;\n");
        //stringBuilder.append("alert(result);\n");
        stringBuilder.append("}\n");
        //stringBuilder.append("alert(\"9: \" + xhr.readyState)\n");
        stringBuilder.append("}\n");
        //stringBuilder.append("alert(\"10\");\n");

        stringBuilder.append("xhr.open(\"POST\",webUrl);");

        //stringBuilder.append("alert(\"11\");\n");
        stringBuilder.append("xhr.send(null);\n");

        //stringBuilder.append("alert(\"12\");\n");
        stringBuilder.append("}\n");
        stringBuilder.append("</script>\n");

        response.getWriter().write(stringBuilder.toString());
    }
}
