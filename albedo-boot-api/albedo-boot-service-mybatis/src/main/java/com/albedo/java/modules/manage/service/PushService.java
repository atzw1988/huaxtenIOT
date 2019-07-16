package com.albedo.java.modules.manage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * =======================
 *
 * @author scx
 * @date 2019/5/7 15:53
 * <p>
 * =======================
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class PushService {
    @Autowired
    DeviceServcie deviceServcie;

    @Autowired
    CurrencyService currencyService;

    /**
     * 存入数据并推送数据
     *
     * @param data
     */
    @Async("taskExecutor")
    public void receiveAndSave(String data) {
        deviceServcie.send(data);
        currencyService.saveInfo(data);
        //deviceServcie.Test1(data);

        //currencyService.Test(data);
    }

}
