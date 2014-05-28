package com.devreed.homeaway.Util;

/**
 * Created by rreed on 5/24/2014.
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonTranslator {
    private static final Logger logger = Logger.getLogger(JsonTranslator.class);

    public static String toJson(Object obj){
        String json = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(obj);
        } catch (IOException e) {
            logger.error("Json translation error. " + e.getMessage());
            return "Json translation error";
        }

        return json;
    }

    public static Map<String, String> readString(String str){
        // quick and dirty way to get attributes from json.
        // more robust service would map directly to business objects
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(str, new TypeReference<HashMap<String,String>>(){} );
        } catch(Exception e){
            e.printStackTrace();
            return new HashMap<String, String>();
        }
    }
}
