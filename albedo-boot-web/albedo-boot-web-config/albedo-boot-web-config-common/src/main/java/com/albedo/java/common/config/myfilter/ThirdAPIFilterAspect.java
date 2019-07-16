package com.albedo.java.common.config.myfilter;

import com.albedo.java.modules.manage.domain.Product;
import com.albedo.java.modules.manage.service.ProductService;
import com.albedo.java.util.SignUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/1 17:30
 * 开放接口权限验证aop
 */

@Aspect
@Component
public class ThirdAPIFilterAspect {
    private static Logger logger = LoggerFactory.getLogger(ThirdAPIFilterAspect.class);

    @Autowired
    ProductService service;

    @Pointcut("@annotation(com.albedo.java.util.annotation.ThirdAPIFilter)")
    public void annotationPointcut() {


    }

    @Before("annotationPointcut()")
    public void beforePointcut(JoinPoint joinPoint) throws Throwable  {
        //获取到请求的属性
        try {
        ServletRequestAttributes attributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取到请求对象
        HttpServletRequest request = attributes.getRequest();
        Map<String,String[]> params=request.getParameterMap();
        Map<String, String> map = SignUtil.toVerifyMap(params,false);
        if(map!=null && map.size()!=0 && map.get("appid")!=null){
            Product product=new Product();
            product.setProductAppid(map.get("appid"));
            String secret=service.selectSecret(product);
            SignUtil.verify(map,secret);//数据签名验证
        }else {
            logger.error("ThirdAPIFilterAspect AOP:","sign is not found");
            throw new  RuntimeException("请求出错：sign is not found");
        }

        }catch (Exception e){
            logger.error("ThirdAPIFilterAspect AOP:",e);
            throw new RuntimeException("请求出错："+e.getMessage());
        }
    }


}
