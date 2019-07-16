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
 * 产品管理EntityVo 产品管理
 * @author xin
 * @version 2019-03-27
 */
@Data @ToString @NoArgsConstructor @AllArgsConstructor
public class ProductVo extends DataEntityVo<String> {

	private static final long serialVersionUID = 1L;
	/** F_PRODUCTNAME product_name  :  产品名称 */
	public static final String F_PRODUCTNAME = "productName";
	/** F_PRODUCTEDITION product_edition  :  产品版本 */
	public static final String F_PRODUCTEDITION = "productEdition";
	/** F_PRODUCTKEY product_key  :  productkey */
	public static final String F_PRODUCTAPPID = "productAppid";
	/** F_PRODUCTSECRET product_secret  :  产品秘钥 */
	public static final String F_PRODUCTSECRET = "productSecret";
	/** F_PRODUCTNTYPE product_ntype  :  节点类型 */
	public static final String F_PRODUCTNTYPE = "productNtype";
	/** F_PRODUCTADDTIME product_addtime  :  添加时间 */
	public static final String F_PRODUCTADDTIME = "productAddtime";
	/** F_PRODUCTNETMODEL product_netmodel  :  联网方式 */
	public static final String F_PRODUCTNETMODEL = "productNetmodel";
	/** F_PRODUCTDATAFORMAT product_dataformat  :  数据格式 */
	public static final String F_PRODUCTDATAFORMAT = "productDataformat";
	/** F_PRODUCTAUTHENTICATION product_authentication  :  是否id认证 */
	public static final String F_PRODUCTAUTHENTICATION = "productAuthentication";
	/** F_PRODUCTCOMMENTS product_comments  :  产品描述 */
	public static final String F_PRODUCTCOMMENTS = "productComments";
	/** F_PRODUCTSTATE product_state  :  产品状态 */
	public static final String F_PRODUCTSTATE = "productState";
	/** F_PRODUCTDYREGIS product_dyregis  :  是否开启动态注册 */
	public static final String F_PRODUCTDYREGIS = "productDyregis";
	/** F_PRODUCTLABELINFO product_labelinfo  :  标签信息 */
	public static final String F_PRODUCTLABELINFO = "productLabelinfo";
	/** F_PRODUCTISACCESSGATEWAY product_isaccessgateway  :  是否接入网关 */
	public static final String F_PRODUCTISACCESSGATEWAY = "productIsaccessgateway";


    @TableField(exist = false)
    private String id;
    @Length(max=32)@TableId(value = "product_id",type = IdType.UUID)
    private String productId;
	//columns START
	/** productName 产品名称 */
    @NotBlank @Length(max=50)
	private String productName;



    private String productFappid;//产品父appId

    private String manufacturerid;//厂商id

    private String manufacturername;//厂商名称

    @Length(max=32)
    private String productUserid;
	/** productEdition 产品版本 */
    @Length(max=32)
	private String productEdition;
	/** productAppid productAppid */
	@Length(max=32)
	private String productAppid;
	/** productSecret 产品秘钥*/
     @Length(max=32)
	private String productSecret;


	/** productNtype 节点类型 */
    @Length(max=20)
	private String productNtype;

	private Date productAddtime;
	/** productNetmodel 联网方式 */
    @NotBlank @Length(max=10)
	private String productNetmodel;
	/** productDataformat 数据格式 */
    @Length(max=50)
	private String productDataformat;
	/** productAuthentication 是否id认证 */

	private Integer productAuthentication;
	/** productComments 产品描述 */
    @Length(max=200)
	private String productComments;
	/** productState 产品状态 */

	private Integer productState;
	/** productDyregis 是否开启动态注册 */

	private Integer productDyregis;
	/** productLabelinfo 标签信息 */
    @Length(max=200)
	private String productLabelinfo;
	/** productIsaccessgateway 是否接入网关 */

	private Integer productIsaccessgateway;

	private String productCallbackurl;//回调地址callbackUrl
	//columns END

    public ProductVo(String id){
	    this.setId(id);
    }

}
