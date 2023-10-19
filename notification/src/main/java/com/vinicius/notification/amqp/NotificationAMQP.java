package com.vinicius.notification.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationAMQP {

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> startAdmin(RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rebbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public org.springframework.amqp.core.Queue queueNotification() {
        return QueueBuilder
                .nonDurable("product.notification.v1")
                .deadLetterExchange("product.dlx")
                .build();
    }

    @Bean
    public Queue queueDlqNotification(){
        return QueueBuilder
                .nonDurable("product.notification.v1.dlq")
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return ExchangeBuilder
                .fanoutExchange("product.ex")
                .build();
    }

    @Bean
    public FanoutExchange deadLetterExchange(){
        return ExchangeBuilder
                .fanoutExchange("product.dlx")
                .build();
    }

    @Bean
    public Binding bindProduct(){
        return BindingBuilder
                .bind(queueNotification())
                .to(fanoutExchange());
    }

    @Bean
    public Binding bindDlxProduct(){
        return BindingBuilder
                .bind(queueDlqNotification())
                .to(deadLetterExchange());
    }
}