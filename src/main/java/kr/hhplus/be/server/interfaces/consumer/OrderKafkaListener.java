package kr.hhplus.be.server.interfaces.consumer;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderKafkaListener {// @KafkaListner  바깥(카프카) 에서 메세지를 컨슈밍하고 처리하는 친구 OrderKafkaListner 라고 하던 OrderKafkaConsumer라 해도 됨

    @KafkaListener(topics = "order-topic", groupId = "order-group")
    public void consumeOrderMessage(JsonNode orderJson) {
        System.out.println("주문 ID: " + orderJson.get("orderId").asText());
        System.out.println("상품명: " + orderJson.get("productName").asText());
    }
}
