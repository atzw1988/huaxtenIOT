/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.service;

import com.albedo.java.common.persistence.PageQuery;
import com.albedo.java.util.StringUtil;
import com.albedo.java.util.TelecomUtil;
import com.albedo.java.util.UUID32;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.util.rabbitmq.MsgProducer;
import com.albedo.java.vo.base.GeneralEntityVo;
import com.alibaba.fastjson.JSON;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.common.persistence.service.DataVoService;
import com.albedo.java.modules.manage.domain.Product;
import com.albedo.java.modules.manage.domain.vo.ProductVo;
import com.albedo.java.modules.manage.repository.ProductRepository;
import org.springframework.util.StringUtils;
import java.util.*;

/**
 * 产品管理Service 产品管理
 *
 * @author xin
 * @version 2019-03-27
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService extends DataVoService<ProductRepository, Product, String, ProductVo> {

    @Autowired
    MsgProducer msgProducer;

    @Autowired
    UserProductService userProductService;


    /**
     * 根据appid查询产品秘钥
     */
    @Transactional(readOnly = true)
    public String selectSecret(Product product) {
        Product result = this.repository.selectSecret(product);
        if (result != null) {
            return result.getProductSecret();
        }
        return null;
    }


    /**
     * 自定义分页查询产品信息
     *
     * @param page
     * @param loginId
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public PageModel<ProductVo> selectProductPage(PageModel<ProductVo> page, String loginId) {
        List<Product> list = this.repository.selectProductPage(new PageQuery(page, null), loginId);
        List<ProductVo> result = new ArrayList<>();
        for (Product li : list) {
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(li, productVo);
            result.add(productVo);
        }
        page.setData(result);
        page.setRecordsTotal(result.size());
        return page;
    }

    /**
     * 通过productId查找父appid
     *
     * @param appid
     * @return
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public String findFatherAppId(String appid) {
        String fAppId = this.repository.findFatherAppId(appid);
        return fAppId;
    }

    /**
     * 根据appid 查询产品信息
     *
     * @param appid
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public Product findInfoByAppId(String appid) {
        return this.repository.findInfoByAppId(appid);
    }


    /**
     * 订阅电信推送接口
     *
     * @param appid
     * @return
     */
    public boolean subTelecom(String appid, String callbackUrl) {
        Product product = this.findInfoByAppId(appid);
        if (StringUtils.isEmpty(product)) {
            return false;
        }
        String secret = product.getProductSecret();
        if (StringUtil.isBlank(secret)) {
            return false;
        }
        try {
            String subId = TelecomUtil.subDeviceData(appid, secret);
            updateByAppid(appid, subId, callbackUrl);
            return true;
        } catch (Exception e) {
            log.error("注册设备service：", e);
            return false;
        }

    }


    /**
     * 更新产品订阅信息
     *
     * @param appid
     * @param subid
     * @param callbackUrl
     */
    public void updateByAppid(String appid, String subid, String callbackUrl) {
        Product product = new Product();
        product.setProductAppid(appid);
        product.setProductSubid(subid);
        product.setProductCallbackurl(callbackUrl);
        this.repository.updateByAppid(product);
    }


    /**
     * 查找订阅回调地址
     * @param productId
     * @return
     */
//    public String findProductId(String productId){
//        return  this.repository.findproductId(productId);
//    }


    /**
     * 接收运营商物联网平台推送数据
     *
     * @param callabckData
     */
    public void receiveData(String callabckData) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isNotBlank(callabckData)) {
            JSONObject jsonObject = new JSONObject(callabckData);
            String data = jsonObject.getJSONObject("service").getJSONObject("data").get("Datas").toString();
            String deviceId = jsonObject.get("deviceId").toString();
            String time = jsonObject.getJSONObject("service").get("eventTime").toString();
            map.put("Datas", data);
            map.put("deviceId", deviceId);
            map.put("Time", time);
        }

        String newDatas = JSON.toJSONString(map);
        int i = 0;
        while (i <= 100) {
            msgProducer.send(newDatas + "----------" + i);
            i++;
        }
    }


    /**
     * 根据产品id查询订阅回调地址
     *
     * @param prodcutid
     * @return
     */
    public Product findUrl(String prodcutid) {
        return this.repository.findUrl(prodcutid);
    }


    /**
     * 创建产品
     *
     * @param productVo
     * @return
     */
    public synchronized boolean saveProduct(ProductVo productVo) {
        String uuid=UUID32.getUUID();//生成uuid32
        productVo.setProductAppid(UUID32.getUUID());//设置appid
        if (StringUtil.isNotBlank(productVo.getProductFappid())) {//创建的产品为系统现有的产品
            Product product = this.findInfoByAppId(productVo.getProductFappid());
            if (StringUtils.isEmpty(product)) {
                return false;
            }
            productVo.setProductSecret(UUID32.getUUID());//生成app_secret秘钥
        }else {
            productVo.setProductFappid(uuid);
            productVo.setStatus(GeneralEntityVo.FLAG_UNABLE);//锁定 产品需要审核
        }
        try {
            this.save(productVo);
            return true;
        }catch (Exception e){
            log.error("保存产品信息",e);
            return false;
        }

    }


    /**（运营商物联网平台注册产品后相关信息更新进入）
     * 更新产品的appid和app_secret和fappid
     * @param productVo
     * @return
     */
    public boolean updateAppid(ProductVo productVo){
        if(StringUtils.isEmpty(productVo)){
            return false;
        }
        Product product=new Product();
        try {
            this.copyVoToBean(productVo,product);
            this.repository.updateAppid(product);
            return true;
        }catch (Exception e){
            log.error("appid和secret更新失败",e);
            return false;
        }


    }


}
