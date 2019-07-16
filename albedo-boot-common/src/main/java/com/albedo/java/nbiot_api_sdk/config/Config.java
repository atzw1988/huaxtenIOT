package com.albedo.java.nbiot_api_sdk.config;


import com.albedo.java.nbiot_api_sdk.exception.NBStatus;
import com.albedo.java.nbiot_api_sdk.exception.OnenetNBException;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by zhuocongbin
 * date 2018/3/15
 * Loading global properties
 */
public class Config {
    public static String domainName;
    static {
        Properties properties = new Properties();
        try {
            properties.load(Config.class.getClassLoader().getResourceAsStream("albedo.properties"));
            domainName = (String)properties.get("domainName");
        } catch (IOException e) {
            throw new OnenetNBException(NBStatus.LOAD_CONFIG_ERROR);
        }
    }
    public static String getDomainName() {
        return domainName;
    }
}
