package com.albedo.java.util.rabbitmq;

import com.albedo.java.modules.manage.service.CurrencyService;
import com.albedo.java.modules.manage.service.DeviceServcie;
import com.albedo.java.modules.manage.service.ProductService;
import com.albedo.java.modules.manage.service.PushService;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * =======================
 *
 * @author scx
 * @date 2019/4/28 14:09
 * <p>
 * =======================
 */

@Component
@RabbitListener(queues = "dxqueue",containerFactory = "customContainerFactory")
public class MsgReceiverTwo {

    @Autowired
    PushService service;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RabbitHandler
    public void process(String data, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws Exception{
        try {
//            System.out.println("MsgReceiverTwo  : " + hello);
            service.receiveAndSave(data);
            channel.basicAck(tag,false);
        }catch (Exception e){
            channel.basicReject(tag,true);//重新加入队列
        }
    }


}
