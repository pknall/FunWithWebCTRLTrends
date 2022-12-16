package com.ccgautomation.utilities;

import org.json.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

public class JsonTools {

    public JSONObject getJsonFromReader(BufferedReader reader) throws JSONException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        while ((line = reader.readLine()) != null) {
            jb.append(line);
        }

        JSONObject jsonObject =  HTTP.toJSONObject(jb.toString());

        return jsonObject;
    }
}
