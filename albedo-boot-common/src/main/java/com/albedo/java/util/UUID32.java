package com.albedo.java.util;

import java.util.UUID;

/**
 * =======================
 *
 * @author scx
 * @date 2019/4/17 16:08
 * <p>
 * =======================
 * 32位UUID工具类
 */
public class UUID32 {

    /**
     * 获取32位的UUID
     * @return
     */
    public static String getUUID(){
       return UUID.randomUUID().toString().replaceAll("-","");
    }
}
