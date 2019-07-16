package com.albedo.java.util;

import com.iotplatform.client.NorthApiClient;
import com.iotplatform.client.NorthApiException;
import com.iotplatform.client.dto.*;
import com.iotplatform.client.invokeapi.Authentication;
import com.iotplatform.client.invokeapi.DeviceManagement;
import com.iotplatform.client.invokeapi.SubscriptionManagement;

import java.util.Map;

/**
 * =======================
 *
 * @author scx
 * @date 2019/4/12 16:20
 * <p>
 * =======================
 * <p>
 * 电信物联网平台对接工具类
 */
public class TelecomUtil {

    /**
     * 电信平台接口IP地址
     */
    private static final String SERVER_IP = "180.101.147.89";//180.101.147.89

    /**
     * 电信平台接口端口
     */
    private static final String SERVER_PORT = "8743";

    /**
     * 数据变更订阅回调接口
     */
    private static final String CALLBACKURL = "https://www.baidu.com";


    public static NorthApiClient getClient(String appId, String secret) throws NorthApiException {
        NorthApiClient nac = new NorthApiClient();//创建应用实例
        ClientInfo ci = new ClientInfo();//设置对接基本信息实例
        ci.setAppId(appId);
        ci.setPlatformIp(SERVER_IP);
        ci.setPlatformPort(SERVER_PORT);
        ci.setSecret(secret);
        nac.setClientInfo(ci);//初始化NorthApiClient
        nac.initSSLConfig();//初始化双向认证配置
        return nac;
    }


    /**
     * 向电信物联网平台注册设备
     *
     * @param imei 设备唯一标识
     * @return RegDirectDeviceOutDTO
     * @throws NorthApiException 异常信息
     */
    public static String regTelecomDeivce(Map<String, String> paramsMap, String imei, String appid, String secret) throws Exception {
        /**设备注册开始*/
        NorthApiClient nac = getClient(appid, secret);//得到应用实例
        DeviceManagement dm = new DeviceManagement(nac);//得到设备管理实例
        Authentication aaa = new Authentication(nac);//认证实例
        AuthOutDTO aod = null;
        aod = aaa.getAuthToken();//鉴权请求
        String token = aod.getAccessToken();
        RegDirectDeviceInDTO2 rddid2 = new RegDirectDeviceInDTO2();//入参实体
        rddid2.setNodeId(imei);
        rddid2.setVerifyCode(imei);
        rddid2.setTimeout(0);
        RegDirectDeviceOutDTO rddo = dm.regDirectDevice(rddid2, appid, token);//调用方法，注册设备
        String deviceId = rddo.getDeviceId();

        /**设备注册成功后立即修改设备信息方便使用*/
        ModifyDeviceInforInDTO mdo = new ModifyDeviceInforInDTO();//修改设备实例
        mdo.setName(paramsMap.get("deviceName"));//设备名称
        mdo.setManufacturerId(paramsMap.get("MFId"));//厂商id
        mdo.setManufacturerName(paramsMap.get("MFName"));//厂商名称
        mdo.setDeviceType(paramsMap.get("deviceType"));//设备类型
        mdo.setProtocolType(paramsMap.get("protocolType"));//设备使用协议类型
        mdo.setModel(paramsMap.get("model"));
        dm.modifyDeviceInfo(mdo, deviceId, appid, token);
        return rddo.getDeviceId();
    }


    /**
     * 电信设备信息修改
     *
     * @param paramsMap
     * @throws NorthApiException 其中deviceId为注册设备时电信平台返回的id
     */
    public static void updateTelecomDeivce(Map<String, String> paramsMap) throws Exception {
        NorthApiClient nac = getClient(paramsMap.get("appid"), paramsMap.get("secret"));//得到应用实例
        Authentication aaa = new Authentication(nac);//认证实例
        AuthOutDTO aod = aaa.getAuthToken();//鉴权请求
        String token = aod.getAccessToken();
        DeviceManagement dm = new DeviceManagement(nac);
        ModifyDeviceInforInDTO mdo = new ModifyDeviceInforInDTO();
        mdo.setName(paramsMap.get("deviceName"));//设备名称
        mdo.setManufacturerId(paramsMap.get("MFId"));//厂商id
        mdo.setManufacturerName(paramsMap.get("MFName"));//厂商名称
        mdo.setDeviceType(paramsMap.get("deviceType"));//设备类型
        mdo.setProtocolType(paramsMap.get("protocolType"));//设备使用协议类型
        dm.modifyDeviceInfo(mdo, paramsMap.get("deviceId"), paramsMap.get("appid"), token);
    }


    /**
     * 删除电信设备
     *
     * @param deviceId
     * @param appId
     * @param secret
     * @throws NorthApiException
     */
    public static void delTelecomDeivce(String deviceId, String appId, String secret) throws Exception {
        NorthApiClient nac = getClient(appId, secret);//得到应用实例
        Authentication aaa = new Authentication(nac);//认证实例
        AuthOutDTO aod = aaa.getAuthToken();//鉴权请求
        String token = aod.getAccessToken();
        DeviceManagement dm = new DeviceManagement(nac);
        dm.deleteDirectDevice(deviceId, null, null, token);
    }


    /**
     * 电信设备订阅
     * @param appid
     * @param secret
     * @return
     * @throws Exception
     */
    public static String subDeviceData(String appid, String secret) throws Exception {
        //得到应用实例
        NorthApiClient nac = getClient(appid, secret);
        //得到鉴权实例
        Authentication ac = new Authentication(nac);
        //得到订阅实例
        SubscriptionManagement spmg = new SubscriptionManagement(nac);
        //鉴权请求
        AuthOutDTO aod = ac.getAuthToken();
        //得到token
        String token = aod.getAccessToken();
        //创建入参实例
        SubDeviceDataInDTO sddid = new SubDeviceDataInDTO();
        sddid.setNotifyType("deviceDataChanged");//订阅类型
        sddid.setCallbackUrl(CALLBACKURL);//订阅回调接口
        sddid.setAppId(appid);
        /**callbackUrl的所有者标识，不指定该标识可填写null。
         ownerflag为false时，表示callbackUrl的owner是授权应用。
         ownerflag为true时，表示callbackUrl的owner为被授权应用。
         * */
        String ownerFlag = "true";
        try {
            SubscriptionDTO sdto = spmg.subDeviceData(sddid, null, token);
            //返回订阅id
            return sdto.getSubscriptionId();
        } catch (Exception e) {
            throw new Exception("订阅失败："+e.toString());
        }
    }


    /**
     * 删除单个订阅信息
     * @param appid
     * @param secret
     * @param subId
     * @throws Exception
     */
    public static void delSubscribe(String appid,String secret,String subId) throws Exception{
        //得到应用实例
        NorthApiClient nac = getClient(appid, secret);
        //得到鉴权实例
        Authentication ac = new Authentication(nac);
        //得到订阅实例
        SubscriptionManagement spmg = new SubscriptionManagement(nac);
        //鉴权请求
        AuthOutDTO aod = ac.getAuthToken();
        //得到token
        String token = aod.getAccessToken();
        try {
            spmg.deleteSingleSubscription(subId,appid,token);
        }catch (Exception e){
            throw new Exception("删除订阅失败："+e.toString());
        }

    }

}
