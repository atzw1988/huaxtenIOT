package com.albedo.java.modules.manage.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 10:29
 *分组设备实体
 */

@TableName(value = "bus_manage_groupdevice")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GroupDevice implements Serializable {

    private String group_id;//分组id

    private String device_id;//分组设备


}
