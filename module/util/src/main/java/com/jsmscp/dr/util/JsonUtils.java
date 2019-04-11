package com.jsmscp.dr.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class JsonUtils {


    public static <T> String toJsonString(T obj) {
        ObjectMapper mapper  = new ObjectMapper();
        String result = null;

        try {
            result = mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        ObjectMapper mapper  = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        T obj = null;
        try {
            obj = mapper.readValue(jsonStr, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static <T> List<T> parseList(String jsonStr, Class<T[]> clazz) {
        ObjectMapper mapper  = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<T> obj = null;
        try {
            obj = Arrays.asList(mapper.readValue(jsonStr, clazz));
        } catch ( IOException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static Map<String, Object> json2map(String strJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, Object> map = mapper.readValue(strJson, Map.class);
            return map;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }
}
