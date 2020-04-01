package com.kakuiwong.messagecenterthanos.config.rabbitmq;

import com.kakuiwong.messagecenterthanos.listen.MessageOneListen;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author gaoyang
 * @email 785175323@qq.com
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue queueOne() {
        return new Queue(MessageOneListen.QUEUE);
    }

    @Bean
    TopicExchange topicExchangeOne() {
        return new TopicExchange(MessageOneListen.EXCHANGE);
    }

    @Bean
    public Binding bindingExchangeWithOne() {
        return BindingBuilder.bind(queueOne()).to(topicExchangeOne()).with(MessageOneListen.QUEUE);
    }

}
