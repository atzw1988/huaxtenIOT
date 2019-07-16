package com.albedo.java.modules.manage.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/9 15:37
 * 设备管理实体类
 */

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "bus_manage_device")
public class Device  implements Serializable {

    @TableId("device_id")
    private String deviceId;//设备id/主键

    @TableField("product_id")
    private String productId;//设备产品id

    @TableField("device_name")
    private String deviceName;//deviceName

    @TableField("device_ntype")
    private String deviceNtype;//节点类型

    @TableField("device_state")
    private Integer deviceState;//状态/启用状态

    @TableField("device_lastonline")
    private Date deviceLastonline;//最后上线时间

    @TableField("device_label")
    private String deviceLabel;//设备标签

    @TableField("device_deleted")
    private Integer deviceDeleted;//逻辑删除

    @TableField("device_imei")
    private Long deviceImei;//设备IMEI号

    @TableField("device_createtime")
    private Date deviceCreatetime;//设备创建时间

    @TableField("device_iot_id")
    private String deviceIotId;//关联的物联网平台的id

    @TableField("imsi")
    private Long imsi;//设备IMSI号(移动)

    @TableField("device_otype")
    private Integer deviceOtype;//运营商标识

    @TableField(exist = false)
    private String userid;//用户id

    @TableField("device_type")
    private String deviceType;//设备类型

    @TableField("device_model")
    private String deviceModel;//设备型号

    @TableField("device_ptype")
    private String devicePtype;//设备使用协议

    @TableField("device_userid")
    private String deviceUserid;

    @TableField(exist = false)
    private String deviceAppid;//自定义字段
}
