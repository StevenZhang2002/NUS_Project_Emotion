package com.example.demo.Config;

import com.example.demo.Impl.TransactionServiceImpl;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    private static final String EXCHANGE_NAME = "points.exchange";
    private static final String POINTS_QUEUE_NAME = "points.queue";
    private static final String POINTS_ROUTING_KEY = "points.routingkey";
    private static final String RECORD_QUEUE_NAME = "record.queue";
    private static final String RECORD_ROUTING_KEY = "record.routingkey";

    @Bean
    public Queue pointsQueue() {
        return new Queue(POINTS_QUEUE_NAME, true); // 持久化队列
    }

    @Bean
    public Queue recordQueue() {
        return new Queue(RECORD_QUEUE_NAME, true); // 持久化队列
    }

    @Bean
    public Exchange pointsExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding pointsBinding(Queue pointsQueue, Exchange pointsExchange) {
        return BindingBuilder.bind(pointsQueue).to(pointsExchange).with(POINTS_ROUTING_KEY).noargs();
    }

    @Bean
    public Binding recordBinding(Queue recordQueue, Exchange pointsExchange) {
        return BindingBuilder.bind(recordQueue).to(pointsExchange).with(RECORD_ROUTING_KEY).noargs();
    }

    // 定义 Jackson 的消息转换器
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // 配置 RabbitTemplate 使用 Jackson2JsonMessageConverter
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

//    // 配置监听器适配器使用 Jackson2JsonMessageConverter
//    @Bean
//    public MessageListenerAdapter messageListenerAdapter(TransactionServiceImpl transactionService) {
//        return new MessageListenerAdapter(transactionService, jackson2JsonMessageConverter());
//    }
}
