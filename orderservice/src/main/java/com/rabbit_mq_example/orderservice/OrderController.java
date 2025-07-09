package com.rabbit_mq_example.orderservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/orders")      
public class OrderController {
    
    //call the RabbitTemplate for handling the message sent from the controller
    //This will be used to send messages to the payment service
    private final RabbitTemplate rabbitTemplateObj;
    
    public OrderController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplateObj = rabbitTemplate;
    }   
    //Post method which will send the order details on the server to the payment service 

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        //send the order object to the payment service via RabbitMQ
        rabbitTemplateObj.convertAndSend(RabbitMQConfig.ORDER_QUEUE, order);
        return "Order placed successfully! " + order.getItem() + " with amount $ " + order.getAmount();
    }
    
    //Post method for handling multiple orders at once
    @PostMapping("/batch")
    public String placeBatchOrders(@RequestBody List<Order> orders) {
        StringBuilder response = new StringBuilder();
        int totalOrders = orders.size();
        double totalAmount = 0.0;
        
        response.append("Batch order processing started for ").append(totalOrders).append(" orders:\n");
        
        for (Order order : orders) {
            //send each order object to the payment service via RabbitMQ
            rabbitTemplateObj.convertAndSend(RabbitMQConfig.ORDER_QUEUE, order);
            response.append("- Order ").append(order.getOrderId())
                    .append(" (").append(order.getItem()).append(") placed successfully! Amount: $")
                    .append(order.getAmount()).append("\n");
            totalAmount += order.getAmount();
        }
        
        response.append("Total orders processed: ").append(totalOrders)
                .append(", Total amount: $").append(String.format("%.2f", totalAmount));
        
        return response.toString();
    }
}

