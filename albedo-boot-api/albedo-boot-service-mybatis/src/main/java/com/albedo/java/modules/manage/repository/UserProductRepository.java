package com.albedo.java.modules.manage.repository;

import com.albedo.java.modules.manage.domain.UserProduct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/8 13:58
 * <p>
 * =======================
 */

@Repository
public interface UserProductRepository extends BaseMapper<UserProduct> {

    String findUrl(UserProduct userProduct);

    Integer saveInfo(UserProduct userProduct);

}
