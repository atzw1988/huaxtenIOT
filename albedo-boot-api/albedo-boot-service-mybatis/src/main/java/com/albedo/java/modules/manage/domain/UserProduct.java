package com.albedo.java.modules.manage.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/8 13:51
 * <p>用户产品配置表
 * =======================
 */

@Data @ToString @AllArgsConstructor @NoArgsConstructor
@TableName(value = "bus_manage_upro")
public class UserProduct implements Serializable {

    /**产品id*/
    @TableId("upro_productid")
    private  String uproProductid;

    /**用户id*/
    @TableId("upro_userid")
    private  String uproUserid;

    /**回调地址*/
    private  String uproCburl;
}
