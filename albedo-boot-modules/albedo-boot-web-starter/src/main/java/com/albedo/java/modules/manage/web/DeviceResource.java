package com.albedo.java.modules.manage.web;

import com.albedo.java.common.security.SecurityUtil;
import com.albedo.java.modules.manage.domain.Device;
import com.albedo.java.modules.manage.service.DeviceServcie;
import com.albedo.java.util.JsonUtil;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.BaseResource;
import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/10 15:03
 * 设备管理controller
 */

@Controller
@RequestMapping(value = "${albedo.adminPath}/manage/device")
public class DeviceResource extends BaseResource {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private DeviceServcie servcie;

    /**
     * 获取分页信息(根据传入的loginId和productId) 可用于第三方接入
     *
     * @param pm
     * @return
     */
    @GetMapping(value = "/getPage")
    @Timed
    public ResponseEntity findPage(PageModel pm) {
        String loginId = request.getParameter("loginId");
        String productId = request.getParameter("productId");
        if (productId == null || productId.equals("")) {
            return ResultBuilder.buildFailed("产品id为空，请求失败！");
        }
        Device device = new Device();
        device.setProductId(productId);
        device.setUserid(SecurityUtil.getReqUserId(loginId));
        pm = servcie.getPageInfoById(pm, device);
        JSON json = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
        return ResultBuilder.buildObject(json);
    }

    /**
     * 保存设备信息
     * <p>
     * device必有deviceOtype（运营商类型）
     */
    @PostMapping("/saveInfo")
    @Timed
    public ResponseEntity saveInfo(@Valid @RequestBody Device device) {
        //deviceOtype:运营商类型，0-代表电信 1-代表移动 2-代表联通 3-代表其他
        int type = device.getDeviceOtype() == null ? -1 : device.getDeviceOtype();
        //如果没有填写运营商类型，返回错误信息。
        if (type == -1) {
            return ResultBuilder.buildFailed("保存失败，请选择设备类型!");
        }
        try {
            boolean b = servcie.saveDevice(device);
            if (b)
                return ResultBuilder.buildOk("保存成功!");
            else
                return ResultBuilder.buildFailed("保存失败，请重试!");
        } catch (Exception e) {
            log.error("设备保存异常：", e);
            return ResultBuilder.buildFailed("保存失败，程序执行出现异常,请重试!");
        }
    }


    /**
     * 更新电信设备信息
     *
     * @param device 入参实体
     * @return
     */
    @PutMapping("/updateInfo")
    @Timed
    public ResponseEntity updateInfo(@Valid @RequestBody Device device) {
        //未传运营商类型返回错误信息
        int type = device.getDeviceType() == null ? -1 : device.getDeviceOtype();
        if (type == -1) {
            return ResultBuilder.buildFailed("修改失败，请选择设备类型!");
        }
        boolean b = servcie.updateDevice(device);
        if (b)
            return ResultBuilder.buildOk("修改成功!");
        else
            return ResultBuilder.buildFailed("修改失败，请重试!");

    }


    /**
     * 删除设备信息
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteDevice/{ids}")
    @Timed
    public ResponseEntity deleteDevice(@PathVariable("ids") String ids) {
        if (ids == null || ids.equals("")) {
            return ResultBuilder.buildFailed("删除失败，传入需要删除的设备id");
        }
        String[] strings = ids.split(",");
        if (strings.length == 1) {
            return ResultBuilder.buildFailed("删除失败，请传入设备运营商标识");
        }
        boolean b = servcie.deleteDevice(strings[0], Integer.parseInt(strings[1]));
        if (b)
            return ResultBuilder.buildOk("删除成功!");
        else
            return ResultBuilder.buildFailed("删除失败，请重试!");

    }


    /**
     * 批量删除电信设备
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batchdx")
    @Timed
    public ResponseEntity batchDeldx(@RequestBody String[] ids) {
        if (ids == null || ids.length == 0)
            return ResultBuilder.buildFailed("删除失败，值为空!");
        boolean b = servcie.batchDelete(ids, 0);
        if (!b)
            return ResultBuilder.buildFailed("删除失败!");
        return ResultBuilder.buildOk("删除成功!");

    }

    /**
     * 批量删除移动设备
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/batchyd")
    @Timed
    public ResponseEntity batchDelyd(@RequestBody String[] ids) {
        if (ids == null || ids.length == 0)
            return ResultBuilder.buildFailed("删除失败，值为空!");
        boolean b = servcie.batchDelete(ids, 1);
        if (!b)
            return ResultBuilder.buildFailed("删除失败!");
        return ResultBuilder.buildOk("删除成功!");
    }


}
