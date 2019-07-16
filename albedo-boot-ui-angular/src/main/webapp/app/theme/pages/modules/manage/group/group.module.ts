/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from "@angular/router";
import { GroupComponent } from "./group.component";
import { routeChilds } from "../../../../api.routing.module";
import { AlbedoBootSharedModule } from "../../../../../shared/shared.module";
import { LayoutModule } from "../../../../layouts/layout.module";
import { GroupService } from "./group.service";
import { GroupFormComponent } from "./group.form.component";

const routesManageGroup = [
    {
        path: "manage/group/list",
        component: GroupComponent
    },
    {
        path: "manage/group/form",
        component: GroupFormComponent
    },
    {
        path: "manage/group/form/:id",
        component: GroupFormComponent
    },
];

routeChilds.push(...routesManageGroup)


@NgModule({
    imports: [
        AlbedoBootSharedModule,
        CommonModule,
        RouterModule,
        LayoutModule
    ], exports: [
        GroupComponent,
    ], entryComponents: [
        GroupComponent,
    ], declarations: [
        GroupComponent,
        GroupFormComponent,
    ], providers: [
        GroupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GroupModule {


}
