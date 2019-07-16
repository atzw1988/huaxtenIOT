package com.albedo.java.nbiot_api_sdk.online;

import com.albedo.java.nbiot_api_sdk.entity.CommonEntity;
import com.albedo.java.nbiot_api_sdk.utils.HttpSendCenter;
import okhttp3.Callback;
import org.json.JSONObject;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 * apiKey: the product of api-key which can be found on OneNET
 */
public class ExecuteOpe extends BasicOpe{
    public ExecuteOpe(String apiKey) {
        super(apiKey);
    }

    @Override
    public JSONObject operation(CommonEntity commonEntity, JSONObject body) {
        return HttpSendCenter.post(apiKey, commonEntity.toUrl(), body);
    }
    @Override
    public void operation(CommonEntity commonEntity, JSONObject body, Callback callback) {
        HttpSendCenter.postAsync(apiKey, commonEntity.toUrl(), body, callback);
    }
}
