package com.albedo.java.modules.manage.web;

import com.albedo.java.modules.manage.domain.GroupDevice;
import com.albedo.java.modules.manage.service.GroupDeviceService;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.BaseResource;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 11:01
 * 分组设备信息
 */

@Controller
@RequestMapping(value = "${albedo.adminPath}/manage/devicegroup")
public class GroupDeviceResource extends BaseResource {

   @Autowired
    GroupDeviceService service;

    /**
     * 分组设备信息保存
     * @param device
     */
   @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
   @Timed
   public ResponseEntity saveData(@Valid @RequestBody GroupDevice device){
       boolean b= service.batchSave(device);
       System.out.println("输出东西===================："+b);
       return ResultBuilder.buildOk(b);
   }

}
