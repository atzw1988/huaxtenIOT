package com.albedo.java.modules.manage.service;

import com.albedo.java.modules.manage.domain.GroupDevice;
import com.albedo.java.modules.manage.repository.GroupDeviceRepository;
import com.albedo.java.util.StringUtil;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 10:45
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class GroupDeviceService extends ServiceImpl<GroupDeviceRepository, GroupDevice> {

    @Autowired
    GroupDeviceRepository repository;


    /**
     * 批量保存分组设备信息
     * @param params
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public boolean batchSave(GroupDevice params){
        List<GroupDevice> list=new ArrayList<>();
        String groupId=params.getGroup_id();
        String[] devices=params.getDevice_id()==null?"".split(","):params.getDevice_id().split(",");
        if(devices.length>0){
            for(String s:devices){
                GroupDevice groupDevice=new GroupDevice();
                groupDevice.setGroup_id(groupId);
                groupDevice.setDevice_id(s);
                list.add(groupDevice);
            }
        }else {
            return false;
        }
        int n=repository.batchInsert(list);
        if(n==list.size())
            return true;
        else
            return false;

    }


}
