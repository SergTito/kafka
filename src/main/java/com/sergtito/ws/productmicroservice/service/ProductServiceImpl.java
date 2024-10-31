package com.sergtito.ws.productmicroservice.service;

import com.sergtito.ws.productmicroservice.service.dto.CreateProductDTO;
import com.sergtito.ws.productmicroservice.service.event.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService{

    private KafkaTemplate<String,ProductCreatedEvent> kafkaTemplate;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductDTO dto) {
        //TODO save to DB

        String productID = UUID.randomUUID().toString();
        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(
                productID,dto.getTitle(),dto.getPrice(),dto.getQuantity()
        );

        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
                kafkaTemplate.send("product-created-events-topic", productID, productCreatedEvent);

        future.whenComplete((result,exception)->{
           if (exception != null){
            LOGGER.error("failed to send message: {}",exception.getMessage());
           }else {
            LOGGER.info("message send successfully: {}", result.getRecordMetadata());
           }
        });

        LOGGER.info("return:{}", productID);
        return productID;
    }
}
