package com.vinicius.notification.amqp;

import com.vinicius.notification.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    @RabbitListener(queues = "product.notification.v1")
    public void Product(ProductResponse productResponse){
        String message = """
                Id: %s
                Nome: %s
                Categoria: %s
                Valor: %s
                Cor: %s
                Tamanho: %s
                Descrição: %s
                """.formatted(
                productResponse.id(),
                productResponse.name(),
                productResponse.category(),
                productResponse.price(),
                productResponse.color(),
                productResponse.size(),
                productResponse.description());
        logger.info("Produto criado " + message);
    }
}
