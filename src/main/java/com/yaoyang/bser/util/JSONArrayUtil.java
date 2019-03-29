package com.yaoyang.bser.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yaoyang.bser.base.ApiResult;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JSONArray 工具类
 *
 * @author yaoyang
 * @date 2018-03-39
 */
public class JSONArrayUtil {

    public static JSONArray toJSONArray(JSONArray jsonArray) {
        JSONArray array = new JSONArray();
        if (jsonArray == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("x", 0);
            jsonObject.put("y", 0);
            array.add(jsonObject);
        } else {
            for (Object son:jsonArray){
                JSONObject jsonObject= new JSONObject();
                JSONArray sonArray=JSONArray.fromObject(son);
                if(sonArray.size()>0){
                    jsonObject.put("x",sonArray.get(0));
                    jsonObject.put("y",sonArray.get(1));
                }else {
                    jsonObject.put("x", 0);
                    jsonObject.put("y", 0);
                }
                array.add(jsonObject);
            }
        }
        return array;
    }

}
