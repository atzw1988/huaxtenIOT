package com.albedo.java.modules.manage.service;

import com.albedo.java.modules.manage.domain.UserProduct;
import com.albedo.java.modules.manage.repository.UserProductRepository;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/8 14:02
 * <p>  用户产品配置 service
 * =======================
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class UserProductService extends ServiceImpl<UserProductRepository, UserProduct> {

    Logger logger= LoggerFactory.getLogger(UserProductService.class);

    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public String findUrl(UserProduct userProduct) {
        return this.baseMapper.findUrl(userProduct);
    }


    /**
     * 保存信息
     * @param userProduct
     */
    public void saveInfo (UserProduct userProduct){
        try {
            this.baseMapper.saveInfo(userProduct);
        }catch (Exception e){
            logger.error("UserProductService saveInfo error: ",e);
            throw new RuntimeException(e);
        }
    }

}
