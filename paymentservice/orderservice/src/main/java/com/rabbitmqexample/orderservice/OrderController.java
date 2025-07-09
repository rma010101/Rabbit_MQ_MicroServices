package com.rabbitmqexample.orderservice;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String ORDER_QUEUE = "order.queue";

    @PostMapping
    public String createOrder(@RequestBody OrderRequest orderRequest) {
        // Generate a unique order ID
        String orderId = UUID.randomUUID().toString();
        
        // Create order object
        Order order = new Order(orderId, orderRequest.getItem(), orderRequest.getAmount());
        
        // Send order to RabbitMQ for payment processing
        rabbitTemplate.convertAndSend(ORDER_QUEUE, order);
        
        System.out.println("Order created and sent to payment service: " + order);
        
        return "Order created successfully with ID: " + orderId;
    }

    @GetMapping("/health")
    public String health() {
        return "Order Service is running!";
    }
}

// Simple request class for order creation
class OrderRequest {
    private String item;
    private double amount;

    public OrderRequest() {}

    public OrderRequest(String item, double amount) {
        this.item = item;
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
