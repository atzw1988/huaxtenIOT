/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.domain;

import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.albedo.java.util.PublicUtil;
import com.baomidou.mybatisplus.annotations.*;
import com.albedo.java.util.annotation.SearchField;
import com.albedo.java.common.persistence.domain.IdEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.*;

/**
 * 分组信息管理Entity 分组信息管理
 * @author admin
 * @version 2019-04-04
 */
@TableName(value = "bus_manage_group")
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class Group extends IdEntity<Group, String> {

	private static final long serialVersionUID = 1L;

    public static final String F_PRODUCTID = "productId";

	/** F_GROUPNAME group_name  :  分组名称 */
	public static final String F_GROUPNAME = "groupName";
	/** F_SQL_GROUPNAME group_name  :  分组名称 */
	public static final String F_SQL_GROUPNAME = "group_name";
	/** F_GROUPADDTIME group_addtime  :  添加时间 */
	public static final String F_GROUPADDTIME = "groupAddtime";
	/** F_SQL_GROUPADDTIME group_addtime  :  添加时间 */
	public static final String F_SQL_GROUPADDTIME = "group_addtime";
	/** F_GROUPDELETED group_deleted  :  逻辑删除 */
	public static final String F_GROUPDELETED = "groupDeleted";
	/** F_SQL_GROUPDELETED group_deleted  :  逻辑删除 */
	public static final String F_SQL_GROUPDELETED = "group_deleted";

    @NotBlank @Length(max=32)@TableId(value = "group_id",type = IdType.UUID)
	private String groupId;
    @TableField(exist = false)
    private String id;
	//columns START
	/** groupName 分组名称 */@NotBlank @Length(max=50)@TableField("group_name")
	private String groupName;
	/** groupAddtime 添加时间 */@TableField("group_addtime")
	private Date groupAddtime;
	/** groupDeleted 逻辑删除 */@TableField("group_deleted")
	private Integer groupDeleted;

	@TableField(exist = false)
    protected String description;
	//columns END

	@Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
