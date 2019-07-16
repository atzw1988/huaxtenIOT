package com.albedo.java.util;

import com.albedo.java.nbiot_api_sdk.entity.Device;
import com.albedo.java.nbiot_api_sdk.online.CreateDeviceOpe;
import com.albedo.java.nbiot_api_sdk.utils.HttpSendCenter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * =======================
 *
 * @author scx
 * @date 2019/4/18 15:55
 * 中国移动物联网设备操作工具类
 * =======================
 */
public class CMCCNBUtil {
    private static final Logger logger = LoggerFactory.getLogger(CMCCNBUtil.class);


    /**
     * 设备添加/设备创建
     *
     * @param map
     * @return
     */
    public static String createDevice(Map<String, String> map) {
        try {
            //创建设备管理实例
            CreateDeviceOpe deviceOpe = new CreateDeviceOpe(map.get("apiKey"));
            Device device = new Device(map.get("title"), map.get("imei"), map.get("imsi"));
            //向移动平台发送创建设备请求
            JSONObject jsonObject = deviceOpe.operation(device, device.toJsonObject());
            if (jsonObject.get("errno").toString().equals("0")) {
                return jsonObject.getJSONObject("data").getString("device_id");
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("CMCCNBUtil.createDevice error:", e);
            return null;
        }

    }


    /**
     * 删除移动设备
     *
     * @param apiKey
     * @param deviceId
     */
    public static boolean deleteDevice(String apiKey, String deviceId) {
        JSONObject jsonObject = HttpSendCenter.delete(apiKey, "http://api.heclouds.com/devices/" + deviceId);
        if (jsonObject.get("errno").toString().equals("0"))
            return true;
        return false;
    }


}
