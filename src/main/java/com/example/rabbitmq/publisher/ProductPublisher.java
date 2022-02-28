package com.example.rabbitmq.publisher;

import com.example.rabbitmq.config.MessagingConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostMapping
    ResponseEntity<String> registerProduct(String name){
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE, "product.register", name);

        return ResponseEntity.ok("Product is Registered successfully!");
    }

    @DeleteMapping
    ResponseEntity<String> deleteProduct(String name){
        rabbitTemplate.convertAndSend(MessagingConfig.EXCHANGE, "product.delete", name);

        return ResponseEntity.ok("Product is deleted successfully!");
    }
}
