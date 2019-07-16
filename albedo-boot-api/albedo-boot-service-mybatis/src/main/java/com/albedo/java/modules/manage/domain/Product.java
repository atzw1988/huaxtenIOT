/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.domain;

import com.albedo.java.common.persistence.domain.GeneralEntity;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.beans.Transient;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.albedo.java.util.annotation.DictType;


import org.hibernate.validator.constraints.*;

/**
 * 产品管理Entity 产品管理
 * @author xin
 * @version 2019-03-27
 */
@TableName(value = "bus_manage_product")
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class Product extends IdEntity<Product, String> {

	private static final long serialVersionUID = 1L;
	/** F_PRODUCTNAME product_name  :  产品名称 */
	public static final String F_PRODUCTNAME = "productName";
	/** F_SQL_PRODUCTNAME product_name  :  产品名称 */
	public static final String F_SQL_PRODUCTNAME = "product_name";
	/** F_PRODUCTEDITION product_edition  :  产品版本 */
	public static final String F_PRODUCTEDITION = "productEdition";
	/** F_SQL_PRODUCTEDITION product_edition  :  产品版本 */
	public static final String F_SQL_PRODUCTEDITION = "product_edition";
	/** F_PRODUCTKEY product_key  :  productkey */
	public static final String F_PRODUCTKEY = "productKey";
	/** F_SQL_PRODUCTKEY product_key  :  productkey */
	public static final String F_SQL_PRODUCTAPPID= "product_appid";
	/** F_PRODUCTSECRET product_secret  :  产品秘钥 */
	public static final String F_PRODUCTSECRET = "productSecret";
	/** F_SQL_PRODUCTSECRET product_secret  :  产品秘钥 */
	public static final String F_SQL_PRODUCTSECRET = "product_secret";
	/** F_PRODUCTNTYPE product_ntype  :  节点类型 */
	public static final String F_PRODUCTNTYPE = "productNtype";
	/** F_SQL_PRODUCTNTYPE product_ntype  :  节点类型 */
	public static final String F_SQL_PRODUCTNTYPE = "product_ntype";
	/** F_SQL_PRODUCTNUM product_num  :  产品数量 */
	public static final String F_SQL_PRODUCTNUM = "product_num";
	/** F_PRODUCTADDTIME product_addtime  :  添加时间 */
	public static final String F_PRODUCTADDTIME = "productAddtime";
	/** F_SQL_PRODUCTADDTIME product_addtime  :  添加时间 */
	public static final String F_SQL_PRODUCTADDTIME = "product_addtime";
	/** F_PRODUCTNETMODEL product_netmodel  :  联网方式 */
	public static final String F_PRODUCTNETMODEL = "productNetmodel";
	/** F_SQL_PRODUCTNETMODEL product_netmodel  :  联网方式 */
	public static final String F_SQL_PRODUCTNETMODEL = "product_netmodel";
	/** F_PRODUCTDATAFORMAT product_dataformat  :  数据格式 */
	public static final String F_PRODUCTDATAFORMAT = "productDataformat";
	/** F_SQL_PRODUCTDATAFORMAT product_dataformat  :  数据格式 */
	public static final String F_SQL_PRODUCTDATAFORMAT = "product_dataformat";
	/** F_PRODUCTAUTHENTICATION product_authentication  :  是否id认证 */
	public static final String F_PRODUCTAUTHENTICATION = "productAuthentication";
	/** F_SQL_PRODUCTAUTHENTICATION product_authentication  :  是否id认证 */
	public static final String F_SQL_PRODUCTAUTHENTICATION = "product_authentication";
	/** F_PRODUCTCOMMENTS product_comments  :  产品描述 */
	public static final String F_PRODUCTCOMMENTS = "productComments";
	/** F_SQL_PRODUCTCOMMENTS product_comments  :  产品描述 */
	public static final String F_SQL_PRODUCTCOMMENTS = "product_comments";
	/** F_PRODUCTSTATE product_state  :  产品状态 */
	public static final String F_PRODUCTSTATE = "productState";
	/** F_SQL_PRODUCTSTATE product_state  :  产品状态 */
	public static final String F_SQL_PRODUCTSTATE = "product_state";
	/** F_PRODUCTDYREGIS product_dyregis  :  是否开启动态注册 */
	public static final String F_PRODUCTDYREGIS = "productDyregis";
	/** F_SQL_PRODUCTDYREGIS product_dyregis  :  是否开启动态注册 */
	public static final String F_SQL_PRODUCTDYREGIS = "product_dyregis";
	/** F_PRODUCTLABELINFO product_labelinfo  :  标签信息 */
	public static final String F_PRODUCTLABELINFO = "productLabelinfo";
	/** F_SQL_PRODUCTLABELINFO product_labelinfo  :  标签信息 */
	public static final String F_SQL_PRODUCTLABELINFO = "product_labelinfo";
	/** F_PRODUCTISACCESSGATEWAY product_isaccessgateway  :  是否接入网关 */
	public static final String F_PRODUCTISACCESSGATEWAY = "productIsaccessgateway";
	/** F_SQL_PRODUCTISACCESSGATEWAY product_isaccessgateway  :  是否接入网关 */
	public static final String F_SQL_PRODUCTISACCESSGATEWAY = "product_isaccessgateway";

	@NotBlank @Length(max=32)@TableId(value = "product_id",type = IdType.UUID)
    private String productId;
	@TableField(exist = false)
	private String id;

	@TableField("product_fappid")
	private String productFappid;//产品父appId

    @TableField("manufacturerid")
	private String manufacturerid;//厂商id

    @TableField("manufacturername")
	private String manufacturername;//厂商名称


	/** productName 产品名称 */@NotBlank @Length(max=50)@TableField("product_name")
	private String productName;
	/** productEdition 产品版本 */@Length(max=32)@TableField("product_edition")
	private String productEdition;
	/** productKey productkey */@NotBlank @Length(max=20)@TableField("product_appid")
	private String productAppid;
	/** productSecret 产品秘钥 */@Length(max=32)@TableField("product_secret")
	private String productSecret;
	/** productNtype 节点类型 */@Length(max=20)@TableField("product_ntype")
	private Long productNum;
	/** productAddtime 添加时间 */@TableField("product_addtime")
	private Date productAddtime;
	/** productNetmodel 联网方式 */@NotBlank @Length(max=10)@TableField("product_netmodel")
	private String productNetmodel;
	/** productDataformat 数据格式 */@Length(max=50)@TableField("product_dataformat")
	private String productDataformat;
	/** productAuthentication 是否id认证 */@TableField("product_authentication")
	private Integer productAuthentication;
	/** productComments 产品描述 */@Length(max=200)@TableField("product_comments")
	private String productComments;
	/** productState 产品状态 */@TableField("product_state")
	private Integer productState;
	/** productDyregis 是否开启动态注册 */@TableField("product_dyregis")
	private Integer productDyregis;
	/** productLabelinfo 标签信息 */@Length(max=200)@TableField("product_labelinfo")
	private String productLabelinfo;
	/** productIsaccessgateway 是否接入网关 */@TableField("product_isaccessgateway")
	private Integer productIsaccessgateway;

    /** productNtype 用户id */@Length(max=32)@TableField("product_userid")
    private String productUserid;

    /** productNtype 用户id */@Length(max=36)@TableField("product_apikey")
    private String productApikey;

    @TableField("product_subid")
    private String productSubid;//产品订阅id

    @TableField("product_callbackurl")
    private String productCallbackurl;//回调地址callbackUrl

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
