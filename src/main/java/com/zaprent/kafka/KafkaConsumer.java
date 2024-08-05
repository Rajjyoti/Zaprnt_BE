package com.zaprent.kafka;

import com.zaprent.service.product.IProductService;
import com.zaprnt.beans.dtos.request.product.ProductUpdateRequest;
import com.zaprnt.beans.enums.ProductStatus;
import com.zaprnt.beans.kafka.BulkProductUpdateEvent;
import com.zaprnt.beans.kafka.ProductUpdateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.zaprnt.beans.kafka.Constants.PRODUCT_UPDATE_TOPIC;
import static java.util.Objects.isNull;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    private IProductService productService;

    @KafkaListener(topics = PRODUCT_UPDATE_TOPIC, groupId = "my-group")
    public void consumeBulkProductUpdateEvent(BulkProductUpdateEvent message) {
        log.info("[consumeProductUpdateEvent]: {}", message);
        if (isNull(message) || message.getProductDetails().isEmpty()) {
            return;
        }
        try {
            for (ProductUpdateEvent event: message.getProductDetails()) {
                productService.updateProduct(new ProductUpdateRequest().setProductId(event.getProductId()).setQuantity(event.getQuantity()).setStatus(event.getQuantity() == 0 ? ProductStatus.OUT_OF_STOCK : null));
            }
        } catch (Exception e) {
            log.error("error processing kafka event: {}", message);
        }
    }
}

