package com.albedo.java.util.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * =======================
 *
 * @author scx
 * @date 2019/4/26 11:12
 * <p>
 *     队列配置
 * =======================
 */

@Configuration
public class RabbitConfig {



    @Bean
    public Queue DXQueue() {
        return new Queue("dxqueue");
    }

    @Bean
    public Queue neoQueue() {
        return new Queue("neo");
    }

    @Bean
    public Queue objectQueue() {
        return new Queue("object");
    }


    @Bean("customContainerFactory")
    public SimpleRabbitListenerContainerFactory containerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConcurrentConsumers(5);  //设置线程数
        factory.setMaxConcurrentConsumers(10); //最大线程数
        configurer.configure(factory, connectionFactory);
        return factory;
    }


}
