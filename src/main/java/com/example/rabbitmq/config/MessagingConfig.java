package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {

    public static final String QUEUE_REGISTER = "register";
    public static final String QUEUE_DELETE = "delete";
    public static final String EXCHANGE = "topic";

    @Bean
    public Queue queueRegister() {
        return new Queue(QUEUE_REGISTER);
    }

    @Bean
    public Queue queueDelete() {
        return new Queue(QUEUE_DELETE);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding bindingRegister(Queue queueRegister, TopicExchange exchange) {
        return BindingBuilder.bind(queueRegister).to(exchange).with("product.register");
    }

    @Bean
    public Binding bindingDelete(Queue queueDelete, TopicExchange exchange) {
        return BindingBuilder.bind(queueDelete).to(exchange).with("product.delete");
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
}
