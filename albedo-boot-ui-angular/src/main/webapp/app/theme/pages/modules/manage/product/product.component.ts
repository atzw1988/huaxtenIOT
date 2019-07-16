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
    selector: ".manage-product-list.page-list",
    templateUrl: "./product.component.html",
    encapsulation: ViewEncapsulation.None,
})
export class ProductComponent implements AfterViewInit {

    ctx: any
    constructor(
        private _script: ScriptLoaderService,
        private router: ActivatedRoute,
        private principal: Principal,
        private publicService: PublicService) {
        this.ctx = publicService.getServiceCtx('manage_product')

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
                        url: thisCtx + '/manage/product/',
                    },
                },
                pageSize: 10,
            },
            // columns definition
            columns: [
                {
                    title: '主键id', field: 'productId'
                    , width: 110, sortable: 'asc', overflow: 'visible', template: function(row) {
                        return thisPrincipal.hasAnyAuthorityDirectOne("manage_product_edit") ? ('<a href="#/manage/product/form/' + row.id + '" class="m-link" title="点击编辑产品管理">' + row.productId + '</a>') : row.productId;

                    },
                },
                {
                    title: '产品名称', field: 'productName'
                },
                {
                    title: '产品版本', field: 'productEdition'
                },
                {
                    title: 'productkey', field: 'productKey'
                },
                {
                    title: '产品秘钥', field: 'productSecret'
                },
                {
                    title: '节点类型', field: 'productNtype'
                },
                {
                    title: '产品数量', field: 'productNum'
                },
                {
                    title: '添加时间', field: 'productAddtime'
                },
                {
                    title: '联网方式', field: 'productNetmodel'
                },
                {
                    title: '数据格式', field: 'productDataformat'
                },
                {
                    title: '是否id认证', field: 'productAuthentication'
                },
                {
                    title: '产品描述', field: 'productComments'
                },
                {
                    title: '产品状态', field: 'productState'
                },
                {
                    title: '是否开启动态注册', field: 'productDyregis'
                },
                {
                    title: '标签信息', field: 'productLabelinfo'
                },
                {
                    title: '是否接入网关', field: 'productIsaccessgateway'
                },
            ],
        };
        if (thisPrincipal.hasAnyAuthorityDirect(["manage_product_edit", "manage_product_lock", "manage_product_delete"])) {
            options.columns.push({
                field: 'Actions',
                width: 110,
                title: '操作',
                sortable: false,
                overflow: 'visible',
                template: function(row) {
                    var template = '';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_product_edit"))
                        template += '<a href="#/manage/product/form/' + row.id + '" class="m-portlet__nav-link btn m-btn m-btn--hover-accent m-btn--icon m-btn--icon-only m-btn--pill" title="编辑">\
                            \<i class="la la-edit"></i>\
                            \</a>';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_product_lock"))
                        template += '<a href="javascript:void(0)" class="m-portlet__nav-link btn m-btn m-btn--hover-warning m-btn--icon m-btn--icon-only m-btn--pill confirm" title="' + (row.status == "正常" ? "锁定" : "解锁") + '产品管理"\
                     data-table-id="#data-table-product" data-method="put"  data-title="你确认要操作选中的产品管理吗？" data-url="' + thisCtx + '/manage/product/' + row.id + '">\
                            \<i class="la la-'+ (row.status == "正常" ? "unlock-alt" : "unlock") + '"></i>\
                            \</a>';
                    if (thisPrincipal.hasAnyAuthorityDirectOne("manage_product_delete"))
                        template += '<a  href="javascript:void(0)" class="m-portlet__nav-link btn m-btn m-btn--hover-danger m-btn--icon m-btn--icon-only m-btn--pill confirm" title="删除"\
                               data-table-id="#data-table-product" data-method="delete"  data-title="你确认要删除选中的产品管理吗？" data-url="' + thisCtx + '/manage/product/' + row.id + '">\
                            \<i class="la la-trash"></i>\
                            \</a>';
                    return template;
                },
            })
        }
        albedoList.initTable($('#data-table-product'), $('#product-search-form'), options);
        albedoList.init();
        albedoForm.init();
    }



}
