package com.rabbit_mq_example.orderservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




@Configuration
public class RabbitMQConfig {

    //giving the name of the RabbitMQ queue
    public static final String ORDER_QUEUE = "order.queue";

    //creating Bean for RabbitMQ queue
    //This queue will be used to send messages from the order service to the payment service    
    @Bean
    public Queue orderQueue() {
        return new Queue(ORDER_QUEUE, true); // durable queue
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
}
    // Message converter for JSON serialization
    //@Bean
    //public MessageConverter jsonMessageConverter() {
    //    return new Jackson2JsonMessageConverter();
    //}   

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
  
}
}
