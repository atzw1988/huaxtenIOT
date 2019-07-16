/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { CTX } from "../../../../../app.constants";
import { ActivatedRoute } from "@angular/router";
import { Product } from "./product.model";
import { ProductService } from "./product.service";
import { PublicService } from "../../../../../shared/base/service/public.service";

@Component({
    selector: ".manage-product-form.page-form",
    templateUrl: "./product.form.component.html"
})
export class ProductFormComponent implements AfterViewInit {

    product: Product;
    routeData: any;
    ctx: any;
    id: any;

    private afterViewInit = false;
    private afterLoad = false;
    constructor(
        private activatedRoute: ActivatedRoute,
        private productService: ProductService,
        private publicService: PublicService) {
        this.ctx = publicService.getServiceCtx('manage_product')
        this.product = new Product();
        this.routeData = this.activatedRoute.params.subscribe((params) => {
            this.id = params['id'];
            if (this.id) {
                this.productService.find(this.id).subscribe((data) => {
                    this.product = data;
                    albedoForm.setData("#product-save-form", this.product);
                    this.afterLoad = true;
                    this.initForm();
                });
            } else {
                this.afterLoad = true;
                this.initForm();
            }
        });
    }


    ngAfterViewInit() {
        this.afterViewInit = true;
        this.initForm();
    }

    initForm() {
        if (!this.afterViewInit || !this.afterLoad) return;

        var productId = this.product.id, thisCtx = this.ctx
        albedoForm.initValidate($("#product-save-form"), {
            // define validation rules
            rules: {
            },
            messages: {
            }
        })
        albedoForm.init();
        albedoForm.initSave();

    }



}
