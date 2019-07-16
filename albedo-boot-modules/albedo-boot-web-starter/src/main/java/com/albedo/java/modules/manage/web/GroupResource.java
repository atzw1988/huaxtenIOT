/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.web;

import com.albedo.java.common.security.SecurityUtil;
import com.albedo.java.modules.manage.service.GroupDeviceService;
import com.albedo.java.util.StringUtil;
import com.google.common.collect.Lists;
import com.albedo.java.modules.manage.domain.vo.GroupVo;
import com.albedo.java.modules.manage.service.GroupService;
import com.albedo.java.util.JsonUtil;
import com.albedo.java.util.domain.Globals;
import com.albedo.java.util.domain.PageModel;
import com.albedo.java.web.rest.ResultBuilder;
import com.albedo.java.web.rest.base.DataVoResource;
import com.alibaba.fastjson.JSON;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 分组信息管理Controller 分组信息管理
 * @author admin
 * @version 2019-04-04
 */
@Controller
@RequestMapping(value = "${albedo.adminPath}/manage/group")
public class GroupResource extends DataVoResource<GroupService, GroupVo> {

    public GroupResource(GroupService service) {
        super(service);
    }

    @Autowired
    private HttpServletRequest request;//请求链接

    @Autowired
     GroupDeviceService groupDeviceService;


	/**
	 * GET / : get all group.
	 *
	 * @param pm
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and with body all group
	 */
	@GetMapping(value = "/")
	@Timed
	public ResponseEntity getPage(PageModel pm) {
	    service.findPage(pm, SecurityUtil.dataScopeFilter());
		JSON json = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
		return ResultBuilder.buildObject(json);
	}




	/**
	 * POST / : 保存用户信息
	 *
	 * @param groupVo the HTTP group.
	 */
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public ResponseEntity save(@Valid @RequestBody GroupVo groupVo) {
		log.debug("REST request to save GroupForm : {}", groupVo);
		service.save(groupVo);
		return ResultBuilder.buildOk("保存分组信息管理成功");

	}

	/**
	 *
	 * 删除分组信息
	 * @param ids the id of the group to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping(value = "/{ids:" + Globals.LOGIN_REGEX
			+ "}")
	@Timed
	public ResponseEntity delete(@PathVariable String ids) {
		log.debug("REST request to delete Group: {}", ids);
		service.deleteBatchIds(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("删除分组信息管理成功");
	}
	/**
	 * lock //:id : lockOrUnLock the "id" Group.
	 *上锁解锁
	 * @param ids the id of the group to lock
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@PutMapping(value = "/{ids:" + Globals.LOGIN_REGEX + "}")
	@Timed
	public ResponseEntity lockOrUnLock(@PathVariable String ids) {
		log.debug("REST request to lockOrUnLock Group: {}", ids);
		service.lockOrUnLock(Lists.newArrayList(ids.split(StringUtil.SPLIT_DEFAULT)));
		return ResultBuilder.buildOk("操作分组信息管理成功");
	}



    /** Third party access to paging information
     * 第三方用户获取分组分页细信息
     * @param pm 分页模型
     * @return
     */

    @GetMapping(value = "/getMyPage")
    @Timed
    public ResponseEntity getMyPage(PageModel pm){
        String loginId=request.getParameter("loginId");
        if(loginId==null || loginId.equals("")){
            return ResultBuilder.buildFailed("暂无权限访问！");
        }
        service.findPage(pm, SecurityUtil.thirdApiFilter(loginId));
        JSON json = JsonUtil.getInstance().setRecurrenceStr().toJsonObject(pm);
        return ResultBuilder.buildObject(json);
    }


}
