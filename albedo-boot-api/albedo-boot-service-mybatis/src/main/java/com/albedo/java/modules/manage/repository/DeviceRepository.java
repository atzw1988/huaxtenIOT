package com.albedo.java.modules.manage.repository;

import com.albedo.java.modules.manage.domain.Device;
import com.albedo.java.util.domain.PageModel;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 16:55
 * 设备管理Repository
 */

@Repository
public interface DeviceRepository extends BaseMapper<Device> {

    List<Device> getPage(PageModel<Device> pm, Device device);

    List<Device> getPageInfoById(Page<Device> page, @Param("device")Device device);

    Device findProductId(@Param("deviceid")String deviceid);
}
