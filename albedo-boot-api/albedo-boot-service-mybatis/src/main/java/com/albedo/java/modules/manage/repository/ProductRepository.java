/**
 * Copyright &copy; 2018 <a href="https://github.com/somewhereMrli/albedo-boot">albedo-boot</a> All rights reserved.
 */
package com.albedo.java.modules.manage.repository;


import com.albedo.java.common.persistence.repository.BaseRepository;


import com.albedo.java.modules.manage.domain.Product;
import com.albedo.java.util.domain.PageModel;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 产品管理Repository 产品管理
 * @author xin
 * @version 2019-03-27
 */
public interface ProductRepository extends BaseRepository<Product, String> {

    /** 通过appid查询app秘钥*/
    Product selectSecret(Product product);

    List<Product> selectProductPage(Page<Product> page, @Param("loginId") String loginId);

    String findFatherAppId(@Param("productappid")String productAppid);

    Product findInfoByAppId(@Param("productAppid")String productAppid);

    Integer updateByAppid(Product product);

    String findproductId(@Param("deviceId")String deviceId);

    Product findUrl(@Param("productId")String productId);

    Integer updateAppid(Product product);
}
