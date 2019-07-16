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
 * =======================
 *
 * @author scx
 * @date 2019/5/5 15:21
 * 通用数据存储实体
 * =======================
 */
@TableName(value = "default_conf")
@Data @ToString
@NoArgsConstructor
@AllArgsConstructor
public class Currency implements Serializable  {

    /**主键id*/
    @TableId("inf_id")
    private String  infId;

    /**设备id*/
    private String  infDeviceid;

    /**设备信息*/
    private String  infData;

    /**添加时间*/
    private Date infAddtime;

    /**表名*/
    @TableField(exist = false)
    private String tabName;


}
