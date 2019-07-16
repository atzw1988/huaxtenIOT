/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.web;

import com.albedo.java.common.security.SecurityUtil;
import com.albedo.java.modules.manage.domain.Product;
import com.albedo.java.modules.manage.service.CurrencyService;
import com.albedo.java.modules.manage.service.DeviceServcie;
import com.albedo.java.util.rabbitmq.MsgProducer;
import com.albedo.java.util.*;
import com.albedo.java.util.annotation.ThirdAPIFilter;
import com.google.common.collect.Lists;
import com.albedo.java.modules.manage.domain.vo.ProductVo;
import com.albedo.java.modules.manage.service.ProductService;
import com.albedo.java.util.domain.Globals;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.DataVoResource;
import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 产品管理业务Controller
 *
 * @author xin
 * @version 2019-03-27
 */
@Controller
@RequestMapping(value = "${albedo.adminPath}/manage/product")
public class ProductResource extends DataVoResource<ProductService, ProductVo> {

    public ProductResource(ProductService service) {
        super(service);
    }

    @Autowired
    HttpServletRequest request;

    @Autowired
    MsgProducer msgProducer;

    @Autowired
    DeviceServcie deviceServcie;

    @Autowired
    CurrencyService currencyService;

    /**
     * GET / : 分页查询所有产品信息
     *
     * @param pm the pagination information
     * @return the ResponseEntity with status 200 (OK) and with body all product
     */

    @GetMapping(value = "/")
    @Timed
    public ResponseEntity getPage(PageModel pm) {
        service.findPage(pm, SecurityUtil.dataScopeFilter());
        JSON json = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
        return ResultBuilder.buildObject(json);
    }


    /**
     * 自定义分页查询（必须传入loginId）
     *
     * @param pm
     * @return
     */
    @GetMapping(value = "/getMyPage")
    @Timed
    public ResponseEntity getMyPage(PageModel pm) {
        String loginId = request.getParameter("loginId");
        if (loginId == null || loginId.equals(""))
            return ResultBuilder.buildFailed("权限不足");
        PageModel<ProductVo> p = service.selectProductPage(pm, loginId);
        JSON json = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(p);
        return ResultBuilder.buildObject(json);
    }


    /**
     * POST / : 保存/插入/更新 表单信息
     *
     * @param productVo the HTTP product
     */
    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity save(@Valid @RequestBody ProductVo productVo) {
        log.debug("REST request to save ProductForm : {}", productVo);
        boolean b = service.saveProduct(productVo);
        if (b) {
            return ResultBuilder.buildOk("保存产品管理成功");
        } else {
            return ResultBuilder.buildFailed("保存失败!");
        }


    }

    /**
     * DELETE //:id : 通过productId删除产品信息
     *
     * @param ids the id of the product to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping(value = "/del/{ids}")
    @Timed
    public ResponseEntity delete(@PathVariable("ids") String ids) {
        log.debug("REST request to delete Product: {}", ids);
        service.deleteBatchIds(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
        return ResultBuilder.buildOk("删除产品管理成功");
    }

    /**
     * lock //:id : 启用产品或禁用产品
     *
     * @param ids the id of the product to lock
     * @return the ResponseEntity with status 200 (OK)
     */
    @PutMapping(value = "/{ids:" + Globals.LOGIN_REGEX + "}")
    @Timed
    public ResponseEntity lockOrUnLock(@PathVariable String ids) {
        log.debug("REST request to lockOrUnLock Product: {}", ids);
        service.lockOrUnLock(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
        return ResultBuilder.buildOk("操作产品管理成功");
    }


    /**
     * 电信产品订阅信息
     *
     * @param productVo(只需传入appid即可)
     * @return
     */
    @PostMapping("/subTelecom")
    @Timed
    public ResponseEntity subTelecom(@RequestBody ProductVo productVo) {
        String appid = productVo.getProductAppid();
        String callbackUrl = productVo.getProductCallbackurl();
        boolean b = service.subTelecom(appid, callbackUrl);
        if (b)
            return ResultBuilder.buildOk("订阅成功！");
        else
            return ResultBuilder.buildFailed("订阅失败！");
    }


    /**
     * 更新产品的appid和app_secret和fappid
     * @param productVo
     * @return
     */
    @PostMapping("/updateAppid")
    @Timed
    public ResponseEntity updateAppid(@RequestBody ProductVo productVo) {
        boolean b = service.updateAppid(productVo);
        if (b)
            return ResultBuilder.buildOk("更新成功");
        else
            return ResultBuilder.buildFailed("更新失败");
    }


    @RequestMapping("send")
    public String send(HttpServletRequest request) {
        System.out.print("=======到这里了==========");
        String name = request.getParameter("admin");
        Map<String, String> param = new HashMap<>();
        param.put("userId", "9527");
        param.put("amount", "9.99");
        param.put("appid", "1112");
        param.put("productId", "9885544154");
        param.put("secretKey", "mysecret123456");
        try {
            Product product = new Product();
            product.setProductAppid("1112");
            String postResult = HttpClient.post("http://localhost:8066/api/manage/product/checkSign", SignUtil.sign(param, service.selectSecret(product)));
            return postResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }


    /**
     * 模拟服务的API接口
     *
     * @param request
     * @return
     */
    //@ThirdAPIFilter("send")
    @PostMapping("/checkSign")
    public void checkSign(HttpServletRequest request) {
        //从request中获取参数列表，转成map
        Map<String, String[]> params = request.getParameterMap();
//        Map<String, String> map = SignUtil.toVerifyMap(params, false);
//        System.out.println(map.toString());
//        //String secretKey =  map.get("secretKey");
//        Product product = new Product();
//        product.setProductAppid("1112");
//        String secret = service.selectSecret(product);
        System.out.println("====第三方接收数据:" + params.toString());
    }


    /**
     * 接收物联网平台推送信息
     *
     * @param callabckData
     */
    @RequestMapping("/receive")
    @Timed
    public void receiveData(@RequestBody String callabckData) {
        service.receiveData(callabckData);
    }

    @RequestMapping("/threadTest")
    public void threadTest(String tabName) {
           /* for (int i = 0; i < 50; i++) {
                service.Test(i);
                service.Test1(i);
            }*/
        currencyService.Test(tabName);

    }


}
