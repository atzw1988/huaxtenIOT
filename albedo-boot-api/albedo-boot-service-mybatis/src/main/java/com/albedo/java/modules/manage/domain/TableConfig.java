package com.albedo.java.modules.manage.domain;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/6 14:39
 * <p>
 * =======================
 */
@NoArgsConstructor @ToString
@Data @AllArgsConstructor
@TableName(value = "bus_manage_conf")
public class TableConfig {

    @TableId("conf_id")
    private String confId;

    private String confProductid;

    private String confTabname;
}
