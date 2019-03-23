package com.yaoyang.bser.util;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    private Logger logger = LoggerFactory.getLogger(getClass());


    public String doGet(String url) {
        return doGet(url, 20000);
    }

    public String doGet(String url, String charset) {
        return doGet(url, 3000, 20000, charset);
    }


    public String doGet(String url, int readTimeout) {
        return this.doGet(url, 3000, readTimeout);
    }


    public String doGet(String url, int connTimeout, int readTimeout) {
        return this.doGet(url, connTimeout, readTimeout, null);
    }

    public static String httpGetMethod(String url, Map<String, String> params)
            throws IOException {
        OkHttpClient client = new OkHttpClient();
        StringBuffer paramStr = new StringBuffer();
        paramStr.append("?");
        params.keySet().stream().forEach(key -> {
            paramStr.append(key + "=" + params.get(key) + "&");
        });
        paramStr.setLength(paramStr.length() - 1);
        Request request = new Request.Builder().url(url + paramStr.toString())
                .get().build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public String doGetByUTF8(String url, int connTimeout, int readTimeout) {
        return this.doGet(url, connTimeout, readTimeout, "UTF-8");
    }

    public String doGet(String url, int connTimeout, int readTimeout, String charset) {
        HttpClient client = new HttpClient();
        client.getHttpConnectionManager().getParams().setConnectionTimeout(connTimeout);
        client.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
        String res = null;
        // Create a method instance.
        GetMethod method = null;
        try {
            method = new GetMethod(url);
            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, false));
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
            // Execute the method.
            int statusCode = client.executeMethod(method);
            if (statusCode == HttpStatus.SC_OK) {
                BufferedReader reader;
                if (charset == null) {
                    reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
                } else {
                    reader = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), charset));
                }
                StringBuffer stringBuffer = new StringBuffer();
                String str = "";
                while ((str = reader.readLine()) != null) {
                    stringBuffer.append(str);
                }
                res = stringBuffer.toString();
            } else {
                logger.info("Response Code: " + statusCode);
            }
        } catch (Exception e) {
            logger.error("url=" + url + "\r\n", e);
        } finally {
            if (method != null) {
                method.releaseConnection();
            }
        }

        return res;
    }

    public String doPost(String url, Map<String, String> params) {
        return doPost(url, params, "", 20000);
    }

    public String doPost(String url, Map<String, String> params, String charset) {
        return doPost(url, params, charset, 20000);
    }


    public String doPost(String url, Map<String, String> params, String charset, int readTimeout) {
        HttpClient httpClient = new HttpClient();
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);
        httpClient.getHttpConnectionManager().getParams().setSoTimeout(readTimeout);
        PostMethod post = new PostMethod(url);
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> key : params.entrySet()) {
            NameValuePair e = new NameValuePair(key.getKey(), key.getValue());
            nvps.add(e);
        }
        NameValuePair[] nameValues = new NameValuePair[params.size()];
        nameValues = nvps.toArray(nameValues);
        post.setRequestBody(nameValues);
        if (StringUtil.isNullOrEmpty(charset)) {
            post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, charset);
        }

        try {
            httpClient.executeMethod(post);
            // 获取二进制的byte流
            byte[] b = post.getResponseBody();
            String str = new String(b, "UTF-8");

            return str;
        } catch (Exception e) {
            logger.error("url=" + url + "\r\n", e);
            return null;
        } finally {
            post.releaseConnection();
        }
    }

    public String doPost(String url, String jsonString) {
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);

        try {

            StringRequestEntity entity = new StringRequestEntity(jsonString, "application/json", "UTF-8");

            post.setRequestEntity(entity);

            httpClient.executeMethod(post);
            // 获取二进制的byte流
            byte[] b = post.getResponseBody();
            String str = new String(b, "UTF-8");

            return str;
        } catch (Exception e) {
            logger.error("", e);

            return null;
        } finally {
            post.releaseConnection();
        }
    }
    public String doPost(String url, String string, String type) {
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);

        try {

            StringRequestEntity entity = new StringRequestEntity(string, "application/"+type, "UTF-8");

            post.setRequestEntity(entity);

            httpClient.executeMethod(post);
            // 获取二进制的byte流
            byte[] b = post.getResponseBody();
            String str = new String(b, "UTF-8");

            return str;
        } catch (Exception e) {
            logger.error("", e);

            return null;
        } finally {
            post.releaseConnection();
        }
    }

    public static String postRequest(String url, Map<String, String> params, HttpMethodParams attachParams) {
        HttpClient client = new HttpClient();
        client.setConnectionTimeout(5000);
        PostMethod method = new PostMethod(url);
        method.setParams(attachParams);
//		Set<String> keys = params.keySet();
        for (Map.Entry<String, String> key : params.entrySet()) {
            method.setParameter(key.getKey(), key.getValue());
        }
        try {
            client.executeMethod(method);
            return new String(method.getResponseBody(), "UTF-8");
        } catch (Exception e) {
            return "";
        }
    }

    private static RequestConfig defaultRequestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .setStaleConnectionCheckEnabled(true)
            .build();

    public static String httpPostSend(String url,String params,String cookie){
        CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
        HttpPost httppost = new HttpPost(url);
        httppost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httppost.addHeader("Cookie",cookie);
        httppost.addHeader("User-Agent","Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1 MicroMessenger/6.5.7 Language/zh_CN");
        String result="";
        try {
            StringEntity stringEntity = new StringEntity(params,"UTF-8");
            httppost.setEntity(stringEntity);
            CloseableHttpResponse response = httpclient.execute(httppost);
            if(response.getStatusLine().getStatusCode()==200){
                org.apache.http.HttpEntity entity = response.getEntity();
                if(entity!=null){
                    result = EntityUtils.toString(entity,"UTF-8");
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
