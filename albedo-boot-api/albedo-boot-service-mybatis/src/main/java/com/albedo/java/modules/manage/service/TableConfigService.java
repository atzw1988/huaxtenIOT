package com.albedo.java.modules.manage.service;

import com.albedo.java.modules.manage.domain.TableConfig;
import com.albedo.java.modules.manage.repository.TableConfigRepository;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/6 14:49
 * <p>
 * =======================
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class TableConfigService extends ServiceImpl<TableConfigRepository, TableConfig> {





}
