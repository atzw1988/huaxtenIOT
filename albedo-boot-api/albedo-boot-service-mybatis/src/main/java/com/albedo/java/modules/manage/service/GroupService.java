/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.albedo.java.common.persistence.service.DataVoService;
import com.albedo.java.modules.manage.domain.Group;
import com.albedo.java.modules.manage.domain.vo.GroupVo;
import com.albedo.java.modules.manage.repository.GroupRepository;

/**
 * 分组信息管理Service 分组信息管理
 * @author admin
 * @version 2019-04-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupService extends DataVoService<GroupRepository, Group, String, GroupVo>{



}
