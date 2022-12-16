package com.ccgautomation.servlets.sjds;

import com.ccgautomation.utilities.JsonTools;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnnotionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "Post");
        response.setHeader("Access-Control-Allow-Headers", "accept, content-type");

        JSONObject jsonObject = new JsonTools().getJsonFromReader(request.getReader());
    }
}
