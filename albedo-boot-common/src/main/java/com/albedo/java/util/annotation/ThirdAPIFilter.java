package com.albedo.java.util.annotation;

import java.lang.annotation.*;

/**
 * @author scx
 * @version 1.00
 * @time 2019/4/1 16:45
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ThirdAPIFilter {
    String value() default "";
}
