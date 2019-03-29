package com.yaoyang.bser.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.yaoyang.bser.base.ApiResult;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Cookie 工具类
 *
 * @author yaoyang
 * @date 2019-03-23
 */
public class CookieUtil {

    public static JSONObject cookieToJson(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        JSONObject jsonObject = new JSONObject();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                jsonObject.put(cookie.getName(), cookie.getValue());
                System.out.println("cookie.getName():"+cookie.getName());
                System.out.println("cookie.getValue():"+cookie.getValue());
            }
        }
        return jsonObject;
    }

}
