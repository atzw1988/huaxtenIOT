/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { Data } from "../../../../../shared/base/model/data.model";

export class Group extends Data {

    /** groupName 分组名称 */
    public groupName?: string
    /** groupAddtime 添加时间 */
    public groupAddtime?: string
    /** groupDeleted 逻辑删除 */
    public groupDeleted?: number

}
