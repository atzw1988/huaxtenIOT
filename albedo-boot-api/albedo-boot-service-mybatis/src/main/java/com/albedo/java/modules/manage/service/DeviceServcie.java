package com.albedo.java.modules.manage.service;

import com.albedo.java.modules.manage.domain.UserProduct;
import com.albedo.java.util.*;
import com.albedo.java.common.persistence.PageQuery;
import com.albedo.java.modules.manage.domain.Device;
import com.albedo.java.modules.manage.domain.Product;
import com.albedo.java.modules.manage.repository.DeviceRepository;
import com.albedo.java.util.domain.PageModel;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 17:38
 * 设备管理业务逻辑处理Service
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceServcie extends ServiceImpl<DeviceRepository, Device> {
    private static Logger logger = LoggerFactory.getLogger(DeviceServcie.class);
    private static final int CTCC = 0;//中国电信
    private static final int CMCC = 1;//中国移动
    private static final int CUCC = 2;//中国联通
    private static final int OTHER = 3;//其他运行商

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    /**
     * 获取分页信息（old）
     *
     * @param pm
     * @param device
     * @return
     */
    public PageModel<Device> getPage(PageModel<Device> pm, Device device) {
        List<Device> list = this.baseMapper.getPage(pm, device);
        pm.setData(list);
        return pm;
    }

    /**
     * 自定义查询分页信息
     *
     * @param pm
     * @param device
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageModel<Device> getPageInfoById(PageModel<Device> pm, Device device) {
        List<Device> list = this.baseMapper.getPageInfoById(new PageQuery<>(pm, null), device);
        pm.setData(list);
        pm.setRecordsTotal(list.size());
        return pm;
    }


    /**
     * 向物联网平台注册设备并保存设备信息
     *
     * @param device
     * @throws Exception
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveDevice(Device device) throws Exception {
        //获取注册设备的运营商类型
        int type = device.getDeviceOtype() == null ? -1 : device.getDeviceOtype();
        //根据当前产品fappid查找该产品的秘钥
        Product newProduct = findFatherApp(device.getDeviceAppid());
        if (newProduct == null) {//如果产品不存在，发生错误，返回错误信息
            return false;
        }
        //获得我方创建的产品id
        String fappid = newProduct.getProductAppid();
        Map<String, String> map = new HashMap<>();
        switch (type) {
            case CTCC:
                map.put("deviceName", device.getDeviceName());
                map.put("MFId", newProduct.getManufacturerid());
                map.put("MFName", newProduct.getManufacturername());
                map.put("deviceType", device.getDeviceType());
                map.put("protocolType", device.getDevicePtype());
                map.put("model", device.getDeviceModel());
                String deviceId = TelecomUtil.regTelecomDeivce(map, device.getDeviceImei().toString(), fappid, newProduct.getProductSecret());
                if (deviceId == null) {
                    return false;
                }
                device.setDeviceId(deviceId);
                device.setDeviceIotId(deviceId);
                return insertInfo(device);
            case CMCC:
                map.put("apiKey", newProduct.getProductApikey());//设备访问权限
                map.put("title", device.getDeviceName());//设备名称
                map.put("imei", device.getDeviceImei().toString());
                map.put("imsi", device.getImsi().toString());
                String devId = CMCCNBUtil.createDevice(map);
                if (devId == null)
                    return false;
                device.setDeviceId(devId);
                device.setDeviceIotId(devId);
                return insertInfo(device);
            default:
                return false;

        }

    }


    /**
     * 修改电信设备信息
     *
     * @param device
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDevice(Device device) {
        try {
            Product product = findFatherApp(device.getDeviceAppid());
            Map<String, String> map = new HashMap<>();
            map.put("deviceName", device.getDeviceName());
            map.put("MFId", product.getManufacturerid());
            map.put("MFName", product.getManufacturername());
            map.put("deviceType", device.getDeviceType());
            map.put("protocolType", device.getDevicePtype());
            map.put("appid", product.getProductAppid());
            map.put("secret", product.getProductSecret());
            map.put("deviceId", device.getDeviceId());
            TelecomUtil.updateTelecomDeivce(map);
            this.baseMapper.updateById(device);
            return true;
        } catch (Exception e) {
            logger.error("updateDevice：", e);
            return false;
        }

    }


    /**
     * 删除设备
     *
     * @param deviceId
     * @param delFlag
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDevice(String deviceId, Integer delFlag) {
        //通过deviceId查找设备的appId
        String appid = findAppId(deviceId);
        if (appid == null)
            return false;
        //查找父appid
        Product product = findFatherApp(appid);
        try {
            switch (delFlag) {
                case CTCC:
                    TelecomUtil.delTelecomDeivce(deviceId, product.getProductAppid(), product.getProductSecret());
                    this.baseMapper.deleteById(deviceId);
                    return true;
                case CMCC:
                    boolean b = CMCCNBUtil.deleteDevice(product.getProductApikey(), deviceId);
                    if (!b)
                        return false;
                    this.baseMapper.deleteById(deviceId);
                    return true;
                default:
                    return false;
            }

        } catch (Exception e) {
            logger.error("deleteDevice：", e);
            return false;
        }

    }


    /**
     * 批量删除设备信息
     * @param ids
     * @param flag
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDelete(String[] ids,int flag){
        for(String id:ids){
          boolean b=deleteDevice(id,flag);
          if(!b)
              return false;
        }
        return true;
    }


    private Product findFatherApp(String appid) {
        //查询设备父appId
        String fappid = productService.findFatherAppId(appid);
        //根据当前产品fappid查找该产品的秘钥
        Product newProduct = productService.findInfoByAppId(fappid);
        return newProduct;
    }


    /**
     * 根据deviceId查找该设备所属的产品appid
     *
     * @param deviceId
     * @return
     */
    public String findAppId(String deviceId) {
        try {
            Device device = new Device();
            device.setDeviceId(deviceId);
            device = this.baseMapper.selectById(device);
            String productId = device.getProductId();
            Product product = productService.selectById(productId);
            if (product == null)
                return null;
            else
                return product.getProductAppid();
        } catch (Exception e) {
            logger.error("findProductId error：", e);
            return null;
        }


    }


    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    public boolean judgeToken(String token) {
        String[] strs = token.split(",");
        long time = Long.parseLong(strs[1]);
        //获取当前时间戳（毫秒）
        long currentTime = System.currentTimeMillis();
        if (currentTime - time > 0) {
            return true;
        }
        return false;
    }


    /**
     * 保存设备信息
     *
     * @param device
     * @return
     */
    public boolean insertInfo(Device device) {
        Integer integer = this.baseMapper.insert(device);
        if (integer != null && integer > 0) {
            return true;
        }
        return false;
    }


    /**
     * 根据deviceid查询该设备所属产品和imei号和所属用户
     * @param diviceid
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public Device findProductId(String diviceid){
        return this.baseMapper.findProductId(diviceid);
    }


    /**
     * 接口数据订阅推送
     * @param data
     */
    @Async("taskExecutor")
    public synchronized void send(String data){
        try {
            JSONObject jsonObject=new JSONObject(data);
            Map<String,String> map=new HashMap<>();
            String deviceId=jsonObject.getString("deviceId");
            Device device=this.findProductId(deviceId);//查询设备所属产品和所属用户
            String keyName=device.getProductId()+"-"+device.getDeviceUserid();//Redis key
            String callbackUrl=JedisUtil.getUserStr(keyName);
            if(StringUtil.isBlank(callbackUrl)){
                UserProduct userProduct=new UserProduct();
                userProduct.setUproProductid(device.getProductId());
                userProduct.setUproUserid(device.getDeviceUserid());
                callbackUrl= userProductService.findUrl(userProduct);//查询回调地址（数据推送）
                JedisUtil.putUser(keyName,callbackUrl);
            }
            map.put("datas",jsonObject.getString("Datas"));
            map.put("time",jsonObject.getString("Time"));
            map.put("deviceid",jsonObject.getString("deviceId"));
            System.out.println("=======================我开始推送了=========================");
            HttpClient.post(callbackUrl,map);//推送
            System.out.println("=======================推送完毕 等待接收=========================");
        }catch (Exception e){
            logger.error("send data error,function:send  : ",e);
        }

    }


    @Async(value = "taskExecutor")
    public void Test1(String i){

        try {
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getName()+"--"+i +"Test1时间："+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }catch (Exception e){

        }
    }


}
