package com.albedo.java.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**********************
 *  @author scx       *
 *  @date 2019/3/29     * 
 ***** ****************
 */
public class HttpClient {

    public static JSONObject postJson(String reqUrl, JSONObject jsonParam) throws Exception {

        DefaultHttpClient htClient = new DefaultHttpClient();

        HttpPost method = new HttpPost(reqUrl);

        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
        entity.setContentEncoding("UTF-8");
        entity.setContentType("application/json");
        method.setEntity(entity);
        HttpResponse result = htClient.execute(method);
        String resData = EntityUtils.toString(result.getEntity());
        JSONObject resJson = JSONObject.parseObject(resData);
        return resJson;
    }

    public static String post(String reqUrl, Map<String, String> reqParams) throws Exception {
        if (reqParams == null) {
            reqParams = new HashMap<>();
        }
//        System.out.println("---------请求地址：" + reqUrl);
//        System.out.println("---------请求参数：" + JSON.toJSONString(reqParams));
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(reqUrl);
        try {
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (String key : reqParams.keySet()) {
                params.add(new BasicNameValuePair(key, reqParams.get(key)));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, "UTF-8");
            /* 设置参数 */
            post.setEntity(urlEncodedFormEntity);
            //post.setHeader("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLGFjY291bnRfY2hhbmdlX3Bhc3N3b3JkLGRldmljZS1saXN0LGdlbl9nZW5TY2hlbWUsZ2VuX2dlblNjaGVtZV9kZWxldGUsZ2VuX2dlblNjaGVtZV9lZGl0LGdlbl9nZW5TY2hlbWVfbG9jayxnZW5fZ2VuU2NoZW1lX3ZpZXcsZ2VuX2dlblRhYmxlLGdlbl9nZW5UYWJsZV9kZWxldGUsZ2VuX2dlblRhYmxlX2VkaXQsZ2VuX2dlblRhYmxlX2xvY2ssZ2VuX2dlblRhYmxlX3ZpZXcscm9vdCxzbSxzd2FnZ2VyX2luZm8sc3lzLHN5c19hcmVhLHN5c19hcmVhX2RlbGV0ZSxzeXNfYXJlYV9lZGl0LHN5c19hcmVhX2xvY2ssc3lzX2FyZWFfdmlldyxzeXNfYXVkaXRzLHN5c19jb25maWd1cmF0aW9uLHN5c19kaWN0LHN5c19kaWN0X2RlbGV0ZSxzeXNfZGljdF9lZGl0LHN5c19kaWN0X2xvY2ssc3lzX2RpY3RfdmlldyxzeXNfZG9jcyxzeXNfaGVhbHRoX3ZpZXcsc3lzX2xvZ3Msc3lzX21ldHJpY3Msc3lzX21vZHVsZSxzeXNfbW9kdWxlX2RlbGV0ZSxzeXNfbW9kdWxlX2VkaXQsc3lzX21vZHVsZV9sb2NrLHN5c19tb2R1bGVfdmlldyxzeXNfb3JnLHN5c19vcmdfZGVsZXRlLHN5c19vcmdfZWRpdCxzeXNfb3JnX2xvY2ssc3lzX29yZ192aWV3LHN5c19yb2xlLHN5c19yb2xlX2RlbGV0ZSxzeXNfcm9sZV9lZGl0LHN5c19yb2xlX2xvY2ssc3lzX3JvbGVfdmlldyxzeXNfdGFza1NjaGVkdWxlSm9iLHN5c190YXNrU2NoZWR1bGVKb2JfZGVsZXRlLHN5c190YXNrU2NoZWR1bGVKb2JfZWRpdCxzeXNfdGFza1NjaGVkdWxlSm9iX2xvY2ssc3lzX3Rhc2tTY2hlZHVsZUpvYl92aWV3LHN5c191c2VyLHN5c191c2VyX2RlbGV0ZSxzeXNfdXNlcl9lZGl0LHN5c191c2VyX2xvY2ssc3lzX3VzZXJfdmlldyx0ZXN0X3Rlc3RCb29rLHRlc3RfdGVzdEJvb2tfZGVsZXRlLHRlc3RfdGVzdEJvb2tfZWRpdCx0ZXN0X3Rlc3RCb29rX2xvY2ssdGVzdF90ZXN0Qm9va192aWV3LHRlc3RfdGVzdFRyZWUsdXNlciIsImV4cCI6MTU1MzkwOTA5MX0.sKaACCqqrNfAndCxsZslo2Qu_1Hi2iH2mDaKU5R5hXgwuitbavTPp2wa5CkPOBwSfsajlfADckZ6iA1m8IBUOA");
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String returnMsg = EntityUtils.toString(entity, "UTF-8");
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            /* 释放链接 */
            post.releaseConnection();
    }
    }


    public static String get(String reqUrl, Map<String, String> reqParams) throws Exception {
        System.out.println("---------请求地址：" + reqUrl);
        System.out.println("---------请求参数：" + JSON.toJSONString(reqParams));
        return execute(reqUrl, reqParams, "GET");
    }

    public static String execute(String reqUrl, Map<String, String> reqParams, String method) throws Exception {
        return execute(reqUrl, reqParams, method, "application/x-www-form-urlencoded");
    }

    public static String execute(String reqUrl, Map<String, String> reqParams, String method, String contentType)
        throws Exception {
        String response = "";
        String invokeUrl = reqUrl;
        URL serverUrl = new URL(invokeUrl);
        HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

        conn.setReadTimeout(10000);
        conn.setRequestMethod(method);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);

        if (reqParams != null && reqParams.size() > 0) {
            conn.setRequestProperty("Cookie", reqParams.get("Cookie") + "");
            reqParams.remove("Cookie");
        }
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Content-Type", contentType);
        conn.setRequestProperty("Authorization","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImF1dGgiOiJST0xFX0FETUlOLGFjY291bnRfY2hhbmdlX3Bhc3N3b3JkLGRldmljZS1saXN0LGdlbl9nZW5TY2hlbWUsZ2VuX2dlblNjaGVtZV9kZWxldGUsZ2VuX2dlblNjaGVtZV9lZGl0LGdlbl9nZW5TY2hlbWVfbG9jayxnZW5fZ2VuU2NoZW1lX3ZpZXcsZ2VuX2dlblRhYmxlLGdlbl9nZW5UYWJsZV9kZWxldGUsZ2VuX2dlblRhYmxlX2VkaXQsZ2VuX2dlblRhYmxlX2xvY2ssZ2VuX2dlblRhYmxlX3ZpZXcscm9vdCxzbSxzd2FnZ2VyX2luZm8sc3lzLHN5c19hcmVhLHN5c19hcmVhX2RlbGV0ZSxzeXNfYXJlYV9lZGl0LHN5c19hcmVhX2xvY2ssc3lzX2FyZWFfdmlldyxzeXNfYXVkaXRzLHN5c19jb25maWd1cmF0aW9uLHN5c19kaWN0LHN5c19kaWN0X2RlbGV0ZSxzeXNfZGljdF9lZGl0LHN5c19kaWN0X2xvY2ssc3lzX2RpY3RfdmlldyxzeXNfZG9jcyxzeXNfaGVhbHRoX3ZpZXcsc3lzX2xvZ3Msc3lzX21ldHJpY3Msc3lzX21vZHVsZSxzeXNfbW9kdWxlX2RlbGV0ZSxzeXNfbW9kdWxlX2VkaXQsc3lzX21vZHVsZV9sb2NrLHN5c19tb2R1bGVfdmlldyxzeXNfb3JnLHN5c19vcmdfZGVsZXRlLHN5c19vcmdfZWRpdCxzeXNfb3JnX2xvY2ssc3lzX29yZ192aWV3LHN5c19yb2xlLHN5c19yb2xlX2RlbGV0ZSxzeXNfcm9sZV9lZGl0LHN5c19yb2xlX2xvY2ssc3lzX3JvbGVfdmlldyxzeXNfdGFza1NjaGVkdWxlSm9iLHN5c190YXNrU2NoZWR1bGVKb2JfZGVsZXRlLHN5c190YXNrU2NoZWR1bGVKb2JfZWRpdCxzeXNfdGFza1NjaGVkdWxlSm9iX2xvY2ssc3lzX3Rhc2tTY2hlZHVsZUpvYl92aWV3LHN5c191c2VyLHN5c191c2VyX2RlbGV0ZSxzeXNfdXNlcl9lZGl0LHN5c191c2VyX2xvY2ssc3lzX3VzZXJfdmlldyx0ZXN0X3Rlc3RCb29rLHRlc3RfdGVzdEJvb2tfZGVsZXRlLHRlc3RfdGVzdEJvb2tfZWRpdCx0ZXN0X3Rlc3RCb29rX2xvY2ssdGVzdF90ZXN0Qm9va192aWV3LHRlc3RfdGVzdFRyZWUsdXNlciIsImV4cCI6MTU1MzkwOTA5MX0.sKaACCqqrNfAndCxsZslo2Qu_1Hi2iH2mDaKU5R5hXgwuitbavTPp2wa5CkPOBwSfsajlfADckZ6iA1m8IBUOA");
        // 传递参数
        String content = getStringData(reqParams);
        byte[] bypes = content.toString().getBytes();
        conn.getOutputStream().write(bypes);
        conn.connect();

        InputStream is = conn.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line);
        }
        response = buffer.toString();
        conn.disconnect();
        return response;
    }

    public static String getStringData(Map<String, String> params) {
        StringBuffer content = new StringBuffer();
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.get(i);
            String value = params.get(key);
            if (value != null) {
                content.append((i == 0 ? "" : "&") + key + "=" + value);
            } else {
                content.append((i == 0 ? "" : "&") + key + "=");
            }
        }
        return content.toString();
    }

}
