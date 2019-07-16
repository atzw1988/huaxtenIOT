/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { Injectable } from '@angular/core'
import { HttpClient } from "@angular/common/http";
import { CTX } from "../../../../../app.constants"
import { DataService } from "../../../../../shared/base/service/data.service";
import { PublicService } from "../../../../../shared/base/service/public.service";
import { Group } from "./group.model";


@Injectable()
export class GroupService extends DataService<Group> {

    constructor(
        protected http: HttpClient,
        protected publicService: PublicService) {
        super(http, publicService.getServiceCtx('manage_group') + '/manage/group')
    }

}
