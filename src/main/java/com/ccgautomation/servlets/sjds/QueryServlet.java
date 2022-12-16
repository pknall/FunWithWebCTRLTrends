package com.ccgautomation.servlets.sjds;

import com.ccgautomation.utilities.JsonTools;
import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

// https://www.tutorialspoint.com/org_json/org_json_http.htm
public class QueryServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "Post");
        response.setHeader("Access-Control-Allow-Headers", "accept, content-type");
        response.setContentType("application/json");

        JSONObject jsonObject = new JsonTools().getJsonFromReader(request.getReader());


        // Work with the data using methods like...
        // int someInt = jsonObject.getInt("intParamName");
        // String someString = jsonObject.getString("stringParamName");
        // JSONObject nestedObj = jsonObject.getJSONObject("nestedObjName");
        // JSONArray arr = jsonObject.getJSONArray("arrayParamName");
        // etc...


        /*
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        Reader reader = Files.newBufferedReader(Paths.get("series.json"));
        Map<?,?> map = gson.fromJson(reader, Map.class);
        PrintWriter writer = response.getWriter();
        writer.write(gson.toJson(gson.fromJson(reader, String.class)));
        reader.close();

         */

    }
}
