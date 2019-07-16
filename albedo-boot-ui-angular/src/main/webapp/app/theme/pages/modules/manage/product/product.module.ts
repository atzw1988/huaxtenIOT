/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";
import { ProductComponent } from "./product.component";
import { routeChilds } from "../../../../api.routing.module";
import { AlbedoBootSharedModule } from "../../../../../shared/shared.module";
import { LayoutModule } from "../../../../layouts/layout.module";
import { ProductService } from "./product.service";
import { ProductFormComponent } from "./product.form.component";

const routesManageProduct = [
    {
        path: "manage/product/list",
        component: ProductComponent
    },
    {
        path: "manage/product/form",
        component: ProductFormComponent
    },
    {
        path: "manage/product/form/:id",
        component: ProductFormComponent
    },
];

routeChilds.push(...routesManageProduct)


@NgModule({
    imports: [
        AlbedoBootSharedModule,
        CommonModule,
        RouterModule,
        LayoutModule
    ], exports: [
        ProductComponent,
    ], entryComponents: [
        ProductComponent,
    ], declarations: [
        ProductComponent,
        ProductFormComponent,
    ], providers: [
        ProductService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ProductModule {


}
