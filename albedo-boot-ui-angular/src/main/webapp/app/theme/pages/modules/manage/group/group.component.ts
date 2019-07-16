/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { AfterViewInit, Component, ViewEncapsulation } from '@angular/core';
import { ScriptLoaderService } from "../../../../../shared/base/service/script-loader.service";
import { DATA_STATUS } from "../../../../../app.constants";
import { ActivatedRoute } from "@angular/router";
import { Principal } from "../../../../../auth/_services/principal.service";
import { PublicService } from "../../../../../shared/base/service/public.service";
@Component({
    selector: ".manage-group-list.page-list",
    templateUrl: "./group.component.html",
    encapsulation: ViewEncapsulation.None,
})
export class GroupComponent implements AfterViewInit {

    ctx: any
    constructor(
        private _script: ScriptLoaderService,
        private router: ActivatedRoute,
        private principal: Principal,
        private publicService: PublicService) {
        this.ctx = publicService.getServiceCtx('manage_group')

    }

    ngAfterViewInit() {
        this.initTable()
    }

    initTable() {
        var thisPrincipal = this.principal, thisCtx = this.ctx
        var options = {
            data: {
                source: {
                    read: {
                        method: 'GET',
                        url: thisCtx + '/manage/group/',
                    },
                },
                pageSize: 10,
            },
            // columns definition
            columns: [
                {
                    title: '主键id/分组id', field: 'groupId'
                    , width: 110, sortable: 'asc', overflow: 'visible', template: function(row) {
                        return thisPrincipal.hasAnyAuthorityDirectOne("manage_group_edit") ? ('<a href="#/manage/group/form/' + row.id + '" class="m-link" title="点击编辑分组信息管理">' + row.groupId + '</a>') : row.groupId;

                    },
                },
                {
                    title: '分组名称', field: 'groupName'
                },
                {
                    title: '添加时间', field: 'groupAddtime'
                },
                {
                    title: '逻辑删除', field: 'groupDeleted'
                },
            ],
        };
        if (thisPrincipal.hasAnyAuthorityDirect(["manage_group_edit", "manage_group_lock", "manage_group_delete"])) {
            options.columns.push({
                field: 'Actions',
                width: 110,
                title: '操作',
                sortable: false,
                overflow: 'visible',
                template: function(row) {
                    var template = '';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_group_edit"))
                        template += '<a href="#/manage/group/form/' + row.id + '" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑">\
                            \<i class="la la-edit"></i>\
                            \</a>';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_group_lock"))
                        template += '<a href="javascript:void(0)" class="m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill confirm" title="' + (row.status == "正常" ? "锁定" : "解锁") + '分组信息管理"\
                     data-table-id="#data-table-group" data-method="put"  data-title="你确认要操作选中的分组信息管理吗？" data-url="' + thisCtx + '/manage/group/' + row.id + '">\
                            \<i class="la la-'+ (row.status == "正常" ? "unlock-alt" : "unlock") + '"></i>\
                            \</a>';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_group_delete"))
                        template += '<a  href="javascript:void(0)" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill confirm" title="删除"\
                               data-table-id="#data-table-group" data-method="delete"  data-title="你确认要删除选中的分组信息管理吗？" data-url="' + thisCtx + '/manage/group/' + row.id + '">\
                            \<i class="la la-trash"></i>\
                            \</a>';
                    return template;
                },
            })
        }
        albedoList.initTable($('#data-table-group'), $('#group-search-form'), options);
        albedoList.init();
        albedoForm.init();
    }



}
