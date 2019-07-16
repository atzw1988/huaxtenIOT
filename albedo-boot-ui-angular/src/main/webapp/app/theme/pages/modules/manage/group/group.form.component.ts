/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
import { AfterViewInit, Component, OnDestroy, OnInit } from '@angular/core';
import { CTX } from "../../../../../app.constants";
import { ActivatedRoute } from "@angular/router";
import { Group } from "./group.model";
import { GroupService } from "./group.service";
import { PublicService } from "../../../../../shared/base/service/public.service";

@Component({
    selector: ".manage-group-form.page-form",
    templateUrl: "./group.form.component.html"
})
export class GroupFormComponent implements AfterViewInit {

    group: Group;
    routeData: any;
    ctx: any;
    id: any;

    private afterViewInit = false;
    private afterLoad = false;
    constructor(
        private activatedRoute: ActivatedRoute,
        private groupService: GroupService,
        private publicService: PublicService) {
        this.ctx = publicService.getServiceCtx('manage_group')
        this.group = new Group();
        this.routeData = this.activatedRoute.params.subscribe((params) => {
            this.id = params['id'];
            if (this.id) {
                this.groupService.find(this.id).subscribe((data) => {
                    this.group = data;
                    albedoForm.setData("#group-save-form", this.group);
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

        var groupId = this.group.id, thisCtx = this.ctx
        albedoForm.initValidate($("#group-save-form"), {
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
