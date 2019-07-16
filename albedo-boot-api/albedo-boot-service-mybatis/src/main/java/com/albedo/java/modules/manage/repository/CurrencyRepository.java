package com.albedo.java.modules.manage.repository;

import com.albedo.java.modules.manage.domain.Currency;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/5 16:20
 * <p> 通用数据Repository
 * =======================
 */

@Repository
public interface CurrencyRepository extends BaseMapper<Currency> {

    /**根据表名查询信息*/
    List<Currency> queryTable(@Param("tabName")String tabName);

    /**根据表名插入信息*/
    Integer saveData(@Param("currency") Currency currency,@Param("table")String table);

    /**查询表是否存在*/
    Integer existTable(@Param("tabName")String tabName);

    /**创建表*/
    Integer createTab(@Param("tabName")String tabName);

    /**删除指定表*/
    Integer dropTable(@Param("tabName")String tabName);

    /**查询行数*/
    Integer findCount(@Param("tabName")String tabName);



}
