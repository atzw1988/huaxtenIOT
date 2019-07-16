package com.albedo.java.modules.manage.service;

import com.albedo.java.modules.manage.domain.Currency;
import com.albedo.java.modules.manage.domain.Device;
import com.albedo.java.modules.manage.repository.CurrencyRepository;
import com.albedo.java.util.DateTool;
import com.albedo.java.util.UUID32;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/5 16:22
 * <p> 地磁设备数据存储service
 * =======================
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class CurrencyService extends ServiceImpl<CurrencyRepository, Currency> {

    protected static Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    protected static final String PREFIX = "info_";

    @Autowired
    DeviceServcie deviceServcie;


    /**
     * 查询表是否存在
     *
     * @param tabName
     * @return
     */
    public boolean existTable(String tabName) {
        int i = this.baseMapper.existTable(tabName);
        if (i > 0)
            return true;
        else
            return false;

    }


    /**
     * 创建表
     *
     * @param tabName
     * @return
     */
    public synchronized boolean createTab(String tabName) {
        if (tabName == null || tabName.equals(""))
            return false;
        tabName = PREFIX + tabName + "_";
        try {
            for (int i = 0; i < 100; i++) {
                String table = "";
                if (i < 10) {
                    table = tabName + "0" + i;
                } else {
                    table = tabName + i;
                }
                this.baseMapper.createTab(table);
            }
            return true;
        } catch (Exception e) {
            logger.error("create table error:", e);
            return false;
        }

    }

    /**
     * 删除表
     *
     * @param tabName
     * @return
     */
    public boolean dropTable(String tabName) {
        int i = this.baseMapper.dropTable(tabName);
        if (i > 0)
            return true;
        else
            return false;
    }



    public List<Currency> queryTable(String tabName) {
        return this.baseMapper.queryTable(tabName);
    }


    public void insertData(Currency currency) {
        try {
            System.out.println(Thread.currentThread().getName() + currency.toString() + ":现在执行插入操作");
            int n = this.baseMapper.saveData(currency, currency.getTabName());
        } catch (Exception e) {
            logger.info("推送数据插入异常：", e);
            logger.error("推送数据插入异常：", e);
        }
    }

    @Async("taskExecutor")
    public void saveInfo(String data) {
        JSONObject json = new JSONObject(data);
        try {
            Device device = deviceServcie.findProductId(json.getString("deviceId"));
            String imei = device.getDeviceImei().toString();//1234
            Currency currency = new Currency();
            currency.setInfAddtime(DateTool.string2Timezone("yyyyMMdd'T'hhmmss'Z'", json.getString("Time"), "UTC"));
            currency.setInfData(json.getString("Datas"));
            currency.setInfDeviceid(json.getString("deviceId"));
            currency.setInfId(UUID32.getUUID());
            currency.setTabName(PREFIX + device.getProductId() + "_" + imei.substring(imei.length() - 2, imei.length()));
            insertData(currency);
        } catch (Exception e) {
            logger.error("CurrencyService function saveInfo  error,data:" + data, e);
        }

    }



    @Async(value = "taskExecutor")
    public void Test(String i){
        try {
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName()+"--"+i +"Test时间："+ new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }catch (Exception e){

        }
    }


}
