/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.domain.vo;

import com.albedo.java.vo.base.DataEntityVo;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 分组信息管理EntityVo 分组信息管理
 * @author admin
 * @version 2019-04-04
 */
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class GroupVo extends DataEntityVo<String> {

	private static final long serialVersionUID = 1L;

    public static final String DEVICEID = "deviceId";

    public static final String F_PRODUCTID = "productId";
	/** F_GROUPNAME group_name  :  分组名称 */
	public static final String F_GROUPNAME = "groupName";
	/** F_GROUPADDTIME group_addtime  :  添加时间 */
	public static final String F_GROUPADDTIME = "groupAddtime";
	/** F_GROUPDELETED group_deleted  :  逻辑删除 */
	public static final String F_GROUPDELETED = "groupDeleted";

	//columns START
    @Length(max=32)@TableId(value = "group_id",type = IdType.UUID)
    private String groupId;
    @TableField(exist = false)
    private String id;
    @TableField(exist = false)
    private String  deviceId;
	/** groupName 分组名称 */
 @NotBlank @Length(max=50)
	private String groupName;
	/** groupAddtime 添加时间 */
 
	private Date groupAddtime;
	/** groupDeleted 逻辑删除 */
 
	private Integer groupDeleted;
	//columns END

    public GroupVo(String id){
	    this.setId(id);
    }

}
