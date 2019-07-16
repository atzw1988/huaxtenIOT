package com.albedo.java.modules.manage.repository;

import com.albedo.java.modules.manage.domain.GroupDevice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 10:41
 */

@Mapper
public interface GroupDeviceRepository extends BaseMapper<GroupDevice> {

    int batchInsert(List<GroupDevice> list);

}
