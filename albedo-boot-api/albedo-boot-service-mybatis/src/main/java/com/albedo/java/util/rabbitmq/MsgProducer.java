package com.albedo.java.util.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * =======================
 *
 * @author scx
 * @date 2019/4/26 14:40
 * <p>
 *     生产者  消息发送端
 * =======================
 */
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback {

    protected static Logger logger = LoggerFactory.getLogger(MsgProducer.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String data) {
        System.out.println("Sender : " + data);
        this.rabbitTemplate.convertAndSend("dxqueue", data);
    }


    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("CallBackConfirm UUID: " + correlationData.getId());

        if(ack) {
            System.out.println("CallBackConfirm 消息消费成功！");
        }else {
            System.out.println("CallBackConfirm 消息消费失败！");
        }

        if(cause!=null) {
            System.out.println("CallBackConfirm Cause: " + cause);
        }
    }


}
