package com.rabbitmqexample.paymentservice;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    //Method to listen for incoming orders messages from RabbitMQ
    @RabbitListener(queues = "order.queue", concurrency = "1")
    public void handleOrder(Order order) {
        System.out.println("Payment Service received order: " + order.getOrderId() + ", for item: " + order.getItem() + 
        ", with amount: $" + order.getAmount());
        
        // Process payment logic here
        processPayment(order);
    }
    
    private void processPayment(Order order) {
        // Simulate payment processing
        try {
            Thread.sleep(2000); // Simulate processing time
            System.out.println("Payment processed successfully for order: " + order.getOrderId() + 
                             " with amount: $" + order.getAmount());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Payment processing interrupted for order: " + order.getOrderId());
        }
    }
}
