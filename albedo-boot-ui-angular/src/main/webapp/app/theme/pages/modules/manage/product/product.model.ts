/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { Data } from "../../../../../shared/base/model/data.model";

export class Product extends Data {

    /** productName 产品名称 */
    public productName?: string
    /** productEdition 产品版本 */
    public productEdition?: string
    /** productKey productkey */
    public productKey?: string
    /** productSecret 产品秘钥 */
    public productSecret?: string
    /** productNtype 节点类型 */
    public productNtype?: string
    /** productNum 产品数量 */
    public productNum?: string
    /** productAddtime 添加时间 */
    public productAddtime?: string
    /** productNetmodel 联网方式 */
    public productNetmodel?: string
    /** productDataformat 数据格式 */
    public productDataformat?: string
    /** productAuthentication 是否id认证 */
    public productAuthentication?: number
    /** productComments 产品描述 */
    public productComments?: string
    /** productState 产品状态 */
    public productState?: number
    /** productDyregis 是否开启动态注册 */
    public productDyregis?: number
    /** productLabelinfo 标签信息 */
    public productLabelinfo?: string
    /** productIsaccessgateway 是否接入网关 */
    public productIsaccessgateway?: number

}
